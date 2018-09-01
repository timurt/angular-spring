import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GeocodingService {
  private apiKey = 'AIzaSyBquTtFRMTpshXkwWwvp2ZqEHJtGAMr9Ug';
  private geocodingUrl = 'https://maps.googleapis.com/maps/api/geocode/json';

  constructor(private http: HttpClient) { }

  getCoordinates(address: string): Observable<any> {
    const params = new HttpParams()
      .set('address', address)
      .set('key', this.apiKey);
    return this.http.get<any>(this.geocodingUrl, { params: params })
    .pipe(
      tap(restaurants => console.log('fetched geocoding')),
      catchError(this.handleError('getRestaurants', []))
    );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
