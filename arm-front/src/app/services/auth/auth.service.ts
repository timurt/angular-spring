import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import {TokenStorage} from '../../core/token-storage';
import { Router } from '@angular/router';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authenticated = false;

  constructor(private http: HttpClient,
    private token: TokenStorage,
    private router: Router) {
        if (this.token.getToken()) {
            this.authenticated = true;
        }
    }

    roleMatch(allowedRoles): boolean {
        return allowedRoles.indexOf(this.token.getRole()) > -1;
    }

    logout(): void {
        this.token.signOut();
        this.authenticated = false;
        this.router.navigateByUrl('/login');
    }

    checkExpirationTime() {
        if (this.authenticated) {
            const curTime = Date.now();
            const diff = (this.token.getExpirationTime() - curTime) / 1000;
            if (diff <= 60) {
               this.refreshToken();
            }
        }
    }

    refreshToken(): void {
        const authUrl = 'http://arm:1234/api/auth/refresh-token';
        this.http.post(authUrl, null, httpOptions).pipe(
            tap(data => {
                console.log('Token updated');
                this.token.saveToken(data['accessToken']);
                this.token.saveExpirationTime(data['expirationTime']);
                this.authenticated = true;
            })
        ).subscribe(
            data => {}
        );
    }

    authenticate(credentials): Observable<any> {
        const authUrl = 'http://arm:1234/api/auth/login';
        return this.http.post(authUrl, credentials, httpOptions).pipe(
            tap(data => {
                this.token.saveData(data);
                this.authenticated = true;
            }),
            catchError(this.handleError<any>('authentication', {}))
        );
    }

    register(form): Observable<any> {
        const authUrl = 'http://arm:1234/api/auth/register';
        return this.http.post(authUrl, form, httpOptions).pipe(
            catchError(this.handleError<any>('registration', {}))
        );
    }

    private handleError<T> (operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
          console.error(error);

          result['error'] = true;
          if (operation === 'authentication') {
            if (error.status === 401) {
                result['message'] = 'Wrong login or password';
            }
            if (error.status === 400) {
                result['message'] = 'Fill all fields';
            }
          } else if (operation === 'registration') {
            if (error.status === 400) {
                if (error.error.message === 'User already exists') {
                    result['message'] = 'User already exists';
                } else {
                    result['message'] = 'Fill all fields';
                }
            }
          }
          return of(result as T);
        };
      }
}
