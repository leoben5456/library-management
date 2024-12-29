import { Component, OnInit } from '@angular/core';
import { BookDialogComponent } from '../book-dialog/book-dialog.component';
import { Book, BookService } from '../../services/book.service';
import { Table } from 'primeng/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-books-table',
  templateUrl: './books-table.component.html',
  styleUrl: './books-table.component.scss'
})
export class BooksTableComponent implements OnInit {
  books!: Book[];
  selectedBooks: Book[] = []; 
  categories!: any[];
  statuses!: any[];
  loading: boolean = true;
  activityValues: number[] = [0, 100];
  ref: DynamicDialogRef | undefined;

  constructor(private bookService: BookService,public dialogService: DialogService,private confirmationService: ConfirmationService, private messageService: MessageService) {}

  ngOnInit() {
    this.bookService.getBooks().then((books) => {
      this.books = books;
      this.loading = false;

    });

    this.categories = [
      { label: 'Category 1', value: 'Category 1' },
      { label: 'Category 2', value: 'Category 2' },
      { label: 'Category 3', value: 'Category 3' },
    ];

    
  }

  clear(table: Table) {
    table.clear();
  }

 

  show() {
    this.ref = this.dialogService.open(BookDialogComponent, { 
      header: 'Book Details',
      width: '32vw',
      height: '85vh',
    }
    );
  }

  deleteSelected(event: Event) {  
      this.confirmationService.confirm({
          target: event.target as EventTarget,
          message: 'Do you want to delete this record?',
          header: 'Delete Confirmation',
          icon: 'pi pi-info-circle',
          acceptButtonStyleClass:"p-button-danger p-button-text",
          rejectButtonStyleClass:"p-button-text p-button-text",
          acceptIcon:"none",
          rejectIcon:"none",

          accept: () => {
              this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'Record deleted' });
          },
          reject: () => {
              this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected' });
          }
      });
  }
  onGlobalFilter(event: Event, table: Table) {
    const value = (event.target as HTMLInputElement).value;
    table.filterGlobal(value, 'contains');
  }
    
}
  



  
