import { DatePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { WishlistService } from '../../services/wishlist.service';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.scss']
})
export class BookDetailsComponent {
  book: any;
  date2: Date | undefined;
  reservationDate: Date | null | undefined;

  constructor(
    public config: DynamicDialogConfig,
    private ref: DynamicDialogRef,
    private datePipe: DatePipe,
    private messageService: MessageService,
    private wishlistService:WishlistService
  ) {
    this.book = this.config.data;
  }

  ngOnInit(): void {
    const now = new Date();
    const dateString = this.datePipe.transform(now, 'yyyy-MM-dd');
    this.reservationDate = dateString ? new Date(dateString) : null;
  }

  addBookToWishlist(id: number) {
    this.addToWishlist(id);
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Book added to wishlist!' });
    this.closeDialog();
  }


  ReserverBook() {
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

}
