import { Component, OnInit } from '@angular/core';
import { BookDialogComponent } from '../book-dialog/book-dialog.component';
import { Book, BookService } from '../../services/book.service';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { PaginatorState } from 'primeng/paginator';

interface PageEvent {
  first: number;
  rows: number;
  page: number;
  pageCount: number;
}

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
  page: number = 0;

  size: number = 5;

  Books:any[] = [];
  totalRecords: number = 0;

  constructor(private bookService: BookService,public dialogService: DialogService,private confirmationService: ConfirmationService, private messageService: MessageService) {}

  ngOnInit() {
    
    this.loadBooks();
    this.loading = false;
    
  
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

  deleteSelected(event: Event,id:Number) {  
      this.confirmationService.confirm({
          target: event.target as EventTarget,
          message: 'Do you want to delete this Book?',
          header: 'Delete Confirmation',
          icon: 'pi pi-info-circle',
          acceptButtonStyleClass:"p-button-danger p-button-text",
          rejectButtonStyleClass:"p-button-text p-button-text",
          acceptIcon:"none",
          rejectIcon:"none",

          accept: () => {
              this.DeleteBook(id);
              this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'Book deleted' });
              this.Books = this.Books.filter(book => book.id !== id); 
              this.totalRecords--;
          },
          reject: () => {
          }
      });
  }
  
  onGlobalFilter(event: Event, table: Table) {
    const value = (event.target as HTMLInputElement).value;
    table.filterGlobal(value, 'contains');
  }
  
  loadBooks():void{
    this.bookService.getAllBooks(this.page,this.size).subscribe((data:any)=>{
      this.Books=data.content;
      this.totalRecords = data.totalElements;
      console.log(this.Books);
    })
  }

  onPageChange(event: TableLazyLoadEvent) {
    this.page = event.first ? event.first / (event.rows || 1) : 0;
    this.size = event.rows || 5;
  
    this.loadBooks();
  }



  DeleteBook(id:Number){
    this.bookService.DeleteBook(id).subscribe((data:any)=>{})
    
  }

  

  
}
  



  
