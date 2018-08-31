import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Apartment } from '../../models/apartment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ApartmentService {
  private apartmentsUrl = 'http://arm:1234/api/apartments';

  constructor(private http: HttpClient) { }

  getApartments(page: number, size: number, filters, sortBy: string): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy.toString());
    for (const key in filters) {
      if (filters.hasOwnProperty(key)) {
        const value = filters[key];
        if (value != null) {
          params = params.set(key, value.toString());
        }
      }
    }
    return this.http.get<any>(this.apartmentsUrl, { params: params })
    .pipe(
      tap(apartments => console.log('fetched apartments')),
      catchError(this.handleError('getApartments', []))
    );
  }

  getApartment(id: number): Observable<Apartment> {
    const url = `${this.apartmentsUrl}/${id}`;
    return this.http.get<Apartment>(url).pipe(
      tap(_ => console.log(`fetched apartment id=${id}`)),
      catchError(this.handleError<Apartment>(`getApartment id=${id}`))
    );
  }

  updateApartment (apartment: Apartment): Observable<any> {
    return this.http.post(this.apartmentsUrl, apartment, httpOptions).pipe(
      tap(_ => console.log(`updated apartment id=${apartment.id}`)),
      catchError(this.handleError<any>('updateApartment', {}))
    );
  }

  deleteApartment (apartment: Apartment | number): Observable<any> {
    const id = typeof apartment === 'number' ? apartment : apartment.id;
    const url = `${this.apartmentsUrl}/${id}`;
    return this.http.delete(url, httpOptions).pipe(
      tap(_ => console.log(`deleted apartment id=${id}`)),
      catchError(this.handleError<Apartment>('deleteApartment'))
    );
  }

  rentApartment (apartment: Apartment | number): Observable<any> {
    const id = typeof apartment === 'number' ? apartment : apartment.id;
    const url = `${this.apartmentsUrl}/rent/${id}`;
    return this.http.post(url, httpOptions).pipe(
      tap(_ => console.log(`set apartment rented id=${id}`)),
      catchError(this.handleError<Apartment>('rentApartment'))
    );
  }

  freeApartment (apartment: Apartment | number): Observable<any> {
    const id = typeof apartment === 'number' ? apartment : apartment.id;
    const url = `${this.apartmentsUrl}/free/${id}`;
    return this.http.post(url, httpOptions).pipe(
      tap(_ => console.log(`set apartment free id=${id}`)),
      catchError(this.handleError<Apartment>('freeApartment'))
    );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      result['error'] = true;

      if (error.status === 400) {
        result['message'] = 'Fill all fields';
      }
      return of(result as T);
    };
  }
}
