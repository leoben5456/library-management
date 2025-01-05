import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { catchError, Observable, throwError } from 'rxjs';
import { jwtDecode } from "jwt-decode";
import { Router } from '@angular/router';




interface User{
  email:string;
  password:string;
}



@Injectable({
  providedIn: 'root'
})




export class AuthService {


  
  constructor(private http:HttpClient,private router:Router) {
    
   }


  

   authentificate(user: User): Observable<any> {
    const url = environment.authApi;

    return this.http.post<any>(url, user).pipe(
      // Catch and handle errors
      catchError(this.handleError)
    );
  }

 
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred!';

    if (error.error instanceof ErrorEvent) {
      // Client-side or network error
      errorMessage = `Client-side error: ${error.error.message}`;
    } else {
      // Backend error
      switch (error.status) {
        case 400:
          errorMessage = 'Bad Request: Please check the entered data.';
          break;
        case 401:
          errorMessage = 'Unauthorized: Invalid credentials.';
          break;
        case 404:
          errorMessage = 'Not Found: The server endpoint is incorrect.';
          break;
        case 500:
          errorMessage = 'Internal Server Error: Please try again later.';
          break;
        default:
          errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
    }

  

    // Return a user-friendly error message
    return throwError(() => new Error(errorMessage));
  }
  

  userRole(token: string): string {
    const decodedToken = jwtDecode<{ role: string }>(token);
    return decodedToken.role;
  }
  
  storeToken(token: string,refresh_token:string): void {
    localStorage.setItem('token', token);
    localStorage.setItem('refreshToken', refresh_token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  removeToken(): void {
    localStorage.removeItem('token');
  }

  isTokenExpired(token: string): boolean {
    try {
      const decodedToken = jwtDecode<{ exp: number }>(token);
      const currentTime = Date.now() / 1000;
      return decodedToken.exp < currentTime; 
    } catch (error) {
      console.error('Error decoding token:', error);
      return true; 
    }
  }



  isAuthenticated(): boolean {
    const token = this.getToken();
    if (token) {
      return !this.isTokenExpired(token); 
    }
    return false; 
  }
    
  logout(): void {
    this.removeToken();
    this.router.navigate(['/login']); 
  }

}







