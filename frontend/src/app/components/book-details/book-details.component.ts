import { DatePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { WishlistService } from '../../services/wishlist.service';
import { Router } from '@angular/router';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.scss']
})
export class BookDetailsComponent {
  book: any;
  date2: Date | undefined;
  reservationDate: Date | null | undefined;
  returnDate: Date | null | undefined;

  reservationDateString: string | null = null;
  returnDateString: string | null = null;

  isVisible = true;
  currentUrl: string = '';

  constructor(
    public config: DynamicDialogConfig,
    private ref: DynamicDialogRef,
    private datePipe: DatePipe,
    private messageService: MessageService,
    private wishlistService:WishlistService,
    private router: Router,
    private bookService:BookService
  ) {
    this.book = this.config.data;
  }

  ngOnInit(): void {
    this.currentUrl = this.router.url;
    if (this.currentUrl === '/User/Whishlist') {
      this.isVisible = false;
    }
    const now = new Date();

    // Initialize reservationDate and returnDate
    this.reservationDate = now;
    

    // Format dates as strings
    this.reservationDateString = this.formatDate(this.reservationDate);
    this.returnDateString = this.formatDate(this.returnDate);

  }

  addBookToWishlist(id: number) {
    this.addToWishlist(id);
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Book added to wishlist!' });
    this.closeDialog();
  }


  ReserveBook(bookname:string,dateReturn:string|null,BookId:number) {
    this.BookReservation(bookname,dateReturn,BookId);
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Book Reserved!' });
    this.closeDialog();
  }

  
  closeDialog() {
    this.ref.close();
  }


  addToWishlist(bookId: number): void {
    this.wishlistService.addBookToWishlist(bookId).subscribe({
      next: (data) => {
        console.log('Book added to wishlist successfully:', data);
      },
      error: (error) => {
        console.error('Error adding book to wishlist:', error);
      }
    });
  }



  // Helper method to format Date to 'yyyy-MM-dd'
  formatDate(date: Date | null | undefined): string | null {
    return date ? this.datePipe.transform(date, 'yyyy-MM-dd') : null;
  }

  
  BookReservation(bookName:string,dateReturn:string|null,BookId:number):void{

    this.bookService.ReserveBook(bookName,dateReturn,BookId).subscribe({
      next: (data) => {
        console.log('Book Reserved successfully:', data);
      },
      error: (error) => {
        console.error('Error reserving book:', error);
      }

    })
    

  } 
  
}
