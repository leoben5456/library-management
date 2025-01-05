import { Component } from '@angular/core';
import { Table } from 'primeng/table';
import { Book, BookService } from '../../services/book.service';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { BookDialogComponent } from '../book-dialog/book-dialog.component';

@Component({
  selector: 'app-reservation-table',
  templateUrl: './reservation-table.component.html',
  styleUrl: './reservation-table.component.scss'
})
export class ReservationTableComponent {
books!: Book[];
  selectedBooks: Book[] = [];
  categories!: any[];
  statuses!: any[];
  loading: boolean = true;
  activityValues: number[] = [0, 100];
  ref: DynamicDialogRef | undefined;

  constructor(private bookService: BookService) {}

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








  onGlobalFilter(event: Event, table: Table) {
    const value = (event.target as HTMLInputElement).value;
    table.filterGlobal(value, 'contains');
  }

}
