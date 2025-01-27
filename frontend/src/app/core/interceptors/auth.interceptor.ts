import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class authInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    const urlsToNotUse: string[] = ['/auth/login', '/auth/register', 'generativelanguage.googleapis.com'];
    const shouldSkip = urlsToNotUse.some(u => req.url.includes(u));

    if (shouldSkip) {
      return next.handle(req);
    }

    if (token) {
      if (this.authService.isTokenExpired(token)) {
        this.authService.removeToken();
        this.router.navigate(['/login']);
        return throwError(() => new Error('Token expired'));
      }
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.authService.removeToken();
          this.router.navigate(['/login']);
        }
        return throwError(() => error);
      })
    );
  }
}
