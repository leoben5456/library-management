import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

export interface Book {
  checkbox: boolean;
  id?: string;
  name?: string;
  category?: string;
  author?: string;
}

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http:HttpClient) {}

  

  
   getAllBooks(page:number,size:number):Observable<any>{

     const url=environment.BooksApi

     let params=new HttpParams()
      .set('size',size.toString())
      .set('page',page.toString())
      
      return this.http.get(url, { params });
   }


   CreateNewBook(book: any, photo?: File): Observable<string> {  
    const formData: FormData = new FormData();
    const apiUrl = environment.CreateBookApi;

    formData.append('livre', JSON.stringify(book));
    if (photo) {
      formData.append('file', photo, photo.name);
    }

    return this.http.post(apiUrl, formData, { responseType: 'text' });
  }


  DeleteBook(id:Number):Observable<any>{
    const url=environment.DeleteBookApi+id
    return this.http.delete(url)
  }
  

}
