import { Component, OnInit } from '@angular/core';
import { PaginatorState } from 'primeng/paginator';
import { BookService } from '../../services/book.service';

interface PageEvent {
  first: number;
  rows: number;
  page: number;
  pageCount: number;
}

@Component({
  selector: 'app-discover',
  templateUrl: './discover.component.html',
  styleUrl: './discover.component.scss'
})
export class DiscoverComponent implements OnInit {
  

  page: number = 0;

  size: number = 9;

  Books:any[] = [];

  totalRecords: number = 0;


  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.loadBooks();
  }

  onPageChange(event: PaginatorState) {
    this.page = event.page || 0; 
    this.size = event.rows || 9;
    this.loadBooks(); 
  }


  books=[
    {
      title: 'The Alchemist ',
      stars: 4.5,
      author: 'Paulo Coelho',
      cover: 'https://marketplace.canva.com/EAFaQMYuZbo/1/0/1003w/canva-brown-rusty-mystery-novel-book-cover-hG1QhA7BiBU.jpg'
    },
    {
      title: 'The Alchemist',
      stars: 4.5,
      author: 'Paulo Coelho',
      cover: 'https://marketplace.canva.com/EAFaQMYuZbo/1/0/1003w/canva-brown-rusty-mystery-novel-book-cover-hG1QhA7BiBU.jpg'
    },
    {
      title: 'The Alchemist',
      stars: 4.5,
      author: 'Paulo Coelho',
      cover: 'https://marketplace.canva.com/EAFaQMYuZbo/1/0/1003w/canva-brown-rusty-mystery-novel-book-cover-hG1QhA7BiBU.jpg'
    },
    {
      title: 'The Alchemist',
      stars: 4.5,
      author: 'Paulo Coelho',
      cover: 'https://marketplace.canva.com/EAFaQMYuZbo/1/0/1003w/canva-brown-rusty-mystery-novel-book-cover-hG1QhA7BiBU.jpg'
    },
    {
      title: 'The Alchemist',
      stars: 4.5,
      author: 'Paulo Coelho',
      cover: 'https://marketplace.canva.com/EAFaQMYuZbo/1/0/1003w/canva-brown-rusty-mystery-novel-book-cover-hG1QhA7BiBU.jpg'
    },
    {
      title: 'The Alchemist',
      stars: 4.5,
      author: 'Paulo Coelho',
      cover: 'https://marketplace.canva.com/EAFaQMYuZbo/1/0/1003w/canva-brown-rusty-mystery-novel-book-cover-hG1QhA7BiBU.jpg'
    },
    {
      title: 'The Alchemist',
      stars: 4.5,
      author: 'Paulo Coelho',
      cover: 'https://marketplace.canva.com/EAFaQMYuZbo/1/0/1003w/canva-brown-rusty-mystery-novel-book-cover-hG1QhA7BiBU.jpg'
    },
    {
      title: 'The Alchemist',
      stars: 4.5,
      author: 'Paulo Coelho',
      cover: 'https://marketplace.canva.com/EAFaQMYuZbo/1/0/1003w/canva-brown-rusty-mystery-novel-book-cover-hG1QhA7BiBU.jpg'
    },
    {
      title: 'The Alchemist',
      stars: 4.5,
      author: 'Paulo Coelho',
      cover: 'https://marketplace.canva.com/EAFaQMYuZbo/1/0/1003w/canva-brown-rusty-mystery-novel-book-cover-hG1QhA7BiBU.jpg'
    },
    
  ]


  loadBooks():void{
    this.bookService.getAllBooks(this.page,this.size).subscribe((data:any)=>{
      this.Books=data.content;
      this.totalRecords = data.totalElements;
      console.log(this.Books);
    })
  }


  getBookCover(book:any):string{
    const  imgPrefix:string = 'http://localhost:8080/livre-service/uploads/book-cover/';
     console.log(imgPrefix + book.coverPath);
    return imgPrefix + book.coverPath;

  }

}
