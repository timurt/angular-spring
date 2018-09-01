import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Restaurant } from '../../models/restaurant';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {
  private restaurantsUrl = 'http://arm:1234/api/restaurants';

  constructor(private http: HttpClient) { }

  getRestaurants(page: number, size: number, filters, sortBy: string): Observable<any> {
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
    return this.http.get<any>(this.restaurantsUrl, { params: params })
    .pipe(
      tap(restaurants => console.log('fetched restaurants')),
      catchError(this.handleError('getRestaurants', []))
    );
  }

  getRestaurant(id: number): Observable<Restaurant> {
    const url = `${this.restaurantsUrl}/${id}`;
    return this.http.get<Restaurant>(url).pipe(
      tap(_ => console.log(`fetched restaurant id=${id}`)),
      catchError(this.handleError<Restaurant>(`getRestaurant id=${id}`))
    );
  }

  updateRestaurant (restaurant: Restaurant): Observable<any> {
    return this.http.post(this.restaurantsUrl, restaurant, httpOptions).pipe(
      tap(_ => console.log(`updated restaurant id=${restaurant.id}`)),
      catchError(this.handleError<any>('updateRestaurant', {}))
    );
  }

  deleteRestaurant (restaurant: Restaurant | number): Observable<any> {
    const id = typeof restaurant === 'number' ? restaurant : restaurant.id;
    const url = `${this.restaurantsUrl}/${id}`;
    return this.http.delete(url, httpOptions).pipe(
      tap(_ => console.log(`deleted restaurant id=${id}`)),
      catchError(this.handleError<Restaurant>('deleteRestaurant'))
    );
  }

  rentRestaurant (restaurant: Restaurant | number): Observable<any> {
    const id = typeof restaurant === 'number' ? restaurant : restaurant.id;
    const url = `${this.restaurantsUrl}/rent/${id}`;
    return this.http.post(url, httpOptions).pipe(
      tap(_ => console.log(`set restaurant rented id=${id}`)),
      catchError(this.handleError<Restaurant>('rentRestaurant'))
    );
  }

  freeRestaurant (restaurant: Restaurant | number): Observable<any> {
    const id = typeof restaurant === 'number' ? restaurant : restaurant.id;
    const url = `${this.restaurantsUrl}/free/${id}`;
    return this.http.post(url, httpOptions).pipe(
      tap(_ => console.log(`set restaurant free id=${id}`)),
      catchError(this.handleError<Restaurant>('freeRestaurant'))
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
