import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GeocodingService {
  private apiKey = 'AIzaSyBquTtFRMTpshXkwWwvp2ZqEHJtGAMr9Ug';
  private geocodingUrl = 'https://maps.googleapis.com/maps/api/geocode/json'; 

  getCoordinates(address : string) : Observable<any> {
    let params = new HttpParams()
      .set('address', address)
      .set('key', this.apiKey);
    
    return this.http.get<any>(this.geocodingUrl, { params: params }) 
    .pipe(
      tap(apartments => console.log('fetched geocoding')),
      catchError(this.handleError('getApartments', []))
    );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
   
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
   
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  constructor(private http: HttpClient) { }
}
