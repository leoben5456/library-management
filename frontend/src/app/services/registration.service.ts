import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { User } from '../components/signin/signin.component';
import { environment } from '../../environments/environment.development';
import { catchError, Observable, throwError } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http:HttpClient) { }

  

  register(user: User, photo?: File): Observable<any> {
    const formData: FormData = new FormData();
    const apiUrl = environment.userRegistrationApi;
    formData.append('user', JSON.stringify(user));
    if (photo) {
      formData.append('file', photo, photo.name);
    }

    return this.http.post<any>(apiUrl, formData).pipe(
      catchError(this.handleError)
    );
  }
  


  private handleError(error: HttpErrorResponse) {
    // Handle different error scenarios
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      // Client-side or network error
      errorMessage = `Network error: ${error.error.message}`;
    } else if (error.error && error.error.error) {
      // Backend returned an error message
      errorMessage = error.error.error;
    }
    return throwError(() => new Error(errorMessage));
  }


 

}
