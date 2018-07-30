import { Injectable } from '@angular/core';
import { Apartment } from '../../models/apartment';
import { APARTMENTS } from '../../models/mock-apartments';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ApartmentService {
  private apartmentsUrl = 'http://localhost:8080/apartments'; 
  
  getApartments(page : number, size : number, filters) : Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    for (let key in filters) {
      let value = filters[key];
      params = params.set(key, value.toString());
    }  
    return this.http.get<any>(this.apartmentsUrl, { params: params }) 
    .pipe(
      tap(apartments => console.log('fetched apartments')),
      catchError(this.handleError('getApartments', []))
    );
  }

  getApartment(id : number) : Observable<Apartment> {
    const url = `${this.apartmentsUrl}/${id}`;
    return this.http.get<Apartment>(url).pipe(
      tap(_ => console.log(`fetched apartment id=${id}`)),
      catchError(this.handleError<Apartment>(`getApartment id=${id}`))
    );
  }

  updateApartment (apartment: Apartment): Observable<any> {
    return this.http.post(this.apartmentsUrl, apartment, httpOptions).pipe(
      tap(_ => console.log(`updated apartment id=${apartment.id}`)),
      catchError(this.handleError<any>('updateApartment'))
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

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
   
      console.error(error); // log to console instead
   
      return of(result as T);
    };
  }

  constructor(private http: HttpClient) { }
}
