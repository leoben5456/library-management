import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { Injectable } from '@angular/core';


@Injectable()
export class authInterceptor implements HttpInterceptor {
  

  constructor(private authService: AuthService, private router: Router) {}

  

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    const urlsToNotUse: Array<string> = ['/auth/login', '/auth/register'];

    if (urlsToNotUse.includes(req.url)) {
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