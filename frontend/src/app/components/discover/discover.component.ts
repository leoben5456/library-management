import { Component, OnInit } from '@angular/core';
import { PaginatorState } from 'primeng/paginator';
import { BookService } from '../../services/book.service';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { BookDetailsComponent } from '../book-details/book-details.component';
import { WishlistService } from '../../services/wishlist.service';

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
 
  ref: DynamicDialogRef | undefined;

  constructor(private bookService: BookService,private dialogService:DialogService) { }

  ngOnInit(): void {
    this.loadBooks();
  }

  onPageChange(event: PaginatorState) {
    this.page = event.page || 0; 
    this.size = event.rows || 6;
    this.loadBooks(); 
  }


 


  loadBooks():void{
    this.bookService.getAllBooks(this.page,this.size).subscribe((data:any)=>{
      this.Books=data.content;
      this.totalRecords = data.totalElements;
      console.log(this.Books);
    })
  }


  getBookCover(book:any):string{
    const  imgPrefix:string = 'http://localhost:8080/livre-service/uploads/book-cover/';
     
    return imgPrefix + book.coverPath;

  }
  

  showBookDetails(book: any) {
    this.ref = this.dialogService.open(BookDetailsComponent, {
      header: book.titre,
      width: '30%',
      height: '75vh',
      data:book
      
    });
  }
   

  ngOnDestroy() {
    if (this.ref) {
      this.ref.close();
    }
  }


  

}
