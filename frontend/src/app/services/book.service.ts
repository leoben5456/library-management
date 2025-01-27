import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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


  private apiUrl = `${environment.geminiApiUrl}?key=${environment.geminiApiKey}`;


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


  CheckBookAvailability(id:Number):Observable<any>{
    const url=environment.CheckBookAvailabilityApi+id
    return this.http.get(url)
  }



  ReserveBook(bookName:string,dateReturn:string|null,BookId:number):Observable<any>{
    const url=environment.ReserveBookApi + BookId
    const body={
      "dateExpiration":dateReturn,
      "titreLiver":bookName
        
    }
    return this.http.post(url,body)
  }
  

  getBooksByGenre(genre:string):Observable<any>{
    const url=environment.BooksByGenreApi+genre
    return this.http.get(url)
  }

  
  fillFieldWithAi(bookName: string): Observable<any> {
    const requestBody = {
      contents: [
        {
          parts: [
            {
              text: `You are a knowledgeable library assistant. When provided with a book title "${bookName}", respond only in JSON format with the following fields: {"book_name": "<book name>", "author_name": "<author>", "description": "<detailed and informative book description, at least 150 words>", "language": "<language>", "category": "<category>", "quantity": "<default quantity>", "rating": "<default rating>"}. Ensure the description provides a summary of the book's plot, themes, and significance.`

            }
          ]
        }
      ]
    };
  
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
  
    return this.http.post<any>(this.apiUrl, requestBody, { headers });
  }
  
}


