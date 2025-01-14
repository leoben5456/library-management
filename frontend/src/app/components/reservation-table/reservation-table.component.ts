import { Component } from '@angular/core';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { Book, BookService } from '../../services/book.service';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { BookDialogComponent } from '../book-dialog/book-dialog.component';
import { ReservationService } from '../../services/reservation.service';

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
  Reservations: any[] = [];
  page: number = 0;
  size: number = 6;
  totalRecords: number = 0;

  constructor(private bookService: BookService,private reservationService:ReservationService) {}

  ngOnInit() {
    
    this.loadReservations();
    this.loading = false;
  }


  onGlobalFilter(event: Event, table: Table) {
    const value = (event.target as HTMLInputElement).value;
    table.filterGlobal(value, 'contains');
  }


  loadReservations() {
    this.reservationService.getAllReservations(this.page,this.size).subscribe((reservations) => {
      this.Reservations = reservations.content;
      this.totalRecords=reservations.totalElements;
      console.log(reservations.content);
    });
  }

  onPageChange(event: TableLazyLoadEvent) {
     this.page = event.first ? event.first / (event.rows || 1) : 0;
     this.size = event.rows || 5;
   
     this.loadReservations();
   }


}
