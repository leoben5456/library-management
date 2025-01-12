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

  getBooks(): Promise<Book[]> {
    return Promise.resolve([
      {
        checkbox: false,
        id: '1',
        name: 'Book 1',
        category: 'Fiction',
        author: 'Author 1'
      },
      {
        checkbox: false,
        id: '2',
        name: 'Book 2',
        category: 'Non-Fiction',
        author: 'Author 2'
      },
      {
        checkbox: false,
        id: '3',
        name: 'Book 3',
        category: 'Science',
        author: 'Author 3'
      },
      {
        checkbox: false,
        id: '4',
        name: 'Book 4',
        category: 'History',
        author: 'Author 4'
      },
      {
        checkbox: false,
        id: '5',
        name: 'Book 5',
        category: 'Biography',
        author: 'Author 5'
      },
      {
        checkbox: false,
        id: '6',
        name: 'Book 6',
        category: 'Biography',
        author: 'Author 6'
      }
    ]);
  }

  
   getAllBooks(page:number,size:number):Observable<any>{

     const url=environment.BooksApi

     let params=new HttpParams()
      .set('size',size.toString())
      .set('page',page.toString())
      
      return this.http.get(url, { params });
   }
  

}
