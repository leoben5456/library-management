import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { BookDialogComponent } from '../book-dialog/book-dialog.component';
import { Book, BookService } from '../../services/book.service';
import { Table } from 'primeng/table';

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

  constructor(private bookService: BookService, public dialog: MatDialog) {}

  ngOnInit() {
    this.bookService.getBooks().then((books) => {
      this.books = books;
      this.loading = false;

      // If there are date fields or other transformations, handle them here
    });

    // Define categories if needed
    this.categories = [
      { label: 'Category 1', value: 'Category 1' },
      { label: 'Category 2', value: 'Category 2' },
      { label: 'Category 3', value: 'Category 3' },
      // Add more categories as needed
    ];

    // Define statuses based on Book.inventoryStatus
    this.statuses = [
      { label: 'In Stock', value: 'INSTOCK' },
      { label: 'Low Stock', value: 'LOWSTOCK' },
      { label: 'Out of Stock', value: 'OUTOFSTOCK' }
    ];
  }

  clear(table: Table) {
    table.clear();
  }

 

  show() {
    const dialogRef = this.dialog.open(BookDialogComponent, {
      height: '550px',
      width: '450px',
      // Pass data if necessary
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Handle the result, e.g., refresh the table
        this.ngOnInit();
      }
    });
  }

  deleteSelected() {
    // Implement deletion logic
  }
  onGlobalFilter(event: Event, table: Table) {
    const value = (event.target as HTMLInputElement).value;
    table.filterGlobal(value, 'contains');
  }
  
  
}