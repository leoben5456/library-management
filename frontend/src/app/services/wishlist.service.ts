import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {

  constructor(private http:HttpClient) { }



  getBooksFromUserWishlist():Observable<any>{
    const url=environment.UserWishlistApi;
    return this.http.get(url);
  }


  addBookToWishlist(bookId: number): Observable<any> {
    const url = `${environment.AddBookToWishlistApi}/${bookId}`;
    return this.http.post(url, {});
  }


}
