import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor,
    HttpResponse,
    HttpErrorResponse
  } from '@angular/common/http';
import { Router } from '@angular/router';

import { Observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import {TokenStorage} from './token-storage';
import { AuthService } from '../services/auth/auth.service';

const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    constructor(private auth: AuthService, private token: TokenStorage, private router: Router) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (req.url.startsWith('https://maps.googleapis.com')) {
          return next.handle(req);
        } else {
          let authReq = req;
          if (this.token.getToken() != null) {
            authReq = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + this .token.getToken())});
          }
        return next.handle(authReq)
        .pipe(tap(
            (event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
              this.auth.checkExpirationTime();
            }
          }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
              if (err.status === 401) {
                this.auth.logout();
              }
              if (err.status === 403) {
                this.router.navigateByUrl('/403');
              }
              if (err.status === 404) {
                this.router.navigateByUrl('/404');
              }
            }
          }));
        }
    }
}
