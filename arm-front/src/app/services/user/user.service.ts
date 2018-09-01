import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { User } from '../../models/user';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersUrl = 'http://arm:1234/api/users';

  constructor(private http: HttpClient) {}

  getUsers(page: number, size: number, search: string, roleType: string): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('search', search.toString())
      .set('roleType', roleType.toString());

    return this.http.get<any>(this.usersUrl, { params: params }).pipe(
      tap(users => console.log('fetched users')),
      catchError(this.handleError('getUsers', []))
    );
  }

  getUser(id: number): Observable<User> {
    const url = `${this.usersUrl}/${id}`;
    return this.http.get<User>(url).pipe(
      tap(_ => console.log(`fetched user id=${id}`)),
      catchError(this.handleError<User>(`getUser id=${id}`))
    );
  }

  getOwners(): Observable<User[]> {
    const url = `${this.usersUrl}/owners`;
    return this.http.get<User[]>(url).pipe(
      tap(_ => console.log(`fetched owners`)),
      catchError(this.handleError<User[]>(`getOwners`))
    );
  }

  updateUser(user: User): Observable<any> {
    let url = `${this.usersUrl}`;
    if (user.id) {
      url = `${this.usersUrl}/${user.id}`;
    }
    return this.http.post(url, user, httpOptions).pipe(
      tap(_ => console.log(`updated user id=${user.id}`)),
      catchError(this.handleError<any>('updateUser', {}))
    );
  }

  changePassword(data: any): Observable<any> {
    const url = `${this.usersUrl}/change-password`;

    return this.http.post(url, data, httpOptions).pipe(
      tap(_ => console.log(`changed password`)),
      catchError(this.handleError<any>('changePassword', {}))
    );
  }

  deleteUser(user: User | number): Observable<any> {
    const id = typeof user === 'number' ? user : user.id;
    const url = `${this.usersUrl}/${id}`;
    return this.http.delete(url, httpOptions).pipe(
      tap(_ => console.log(`deleted user id=${id}`)),
      catchError(this.handleError<User>('deleteUser'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      result['error'] = true;

      if (error.status === 400) {
        result['message'] = 'Fill all fields';
      }

      if (operation === 'updateUser') {
        if (error.status === 400) {
          if (error.error.message === 'User already exists') {
            result['message'] = 'User already exists';
          }
        }
      }

      return of(result as T);
    };
  }
}
