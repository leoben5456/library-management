import { Component } from '@angular/core';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { Book, BookService } from '../../services/book.service';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { BookDialogComponent } from '../book-dialog/book-dialog.component';
import { ReservationService } from '../../services/reservation.service';
import { ConfirmationService, MessageService } from 'primeng/api';

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

  constructor(private bookService: BookService,private reservationService:ReservationService,private confirmationService: ConfirmationService, private messageService: MessageService) {}

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


   

   confirm1(event: Event,reservationId:Number) {
    this.confirmationService.confirm({
        target: event.target as EventTarget,
        message: 'Are you sure that you want to proceed?',
        header: 'Confirmation',
        icon: 'pi pi-exclamation-triangle',
        acceptIcon:"none",
        rejectIcon:"none",
        rejectButtonStyleClass:"p-button-text",
        accept: () => {
            this.updateReservationStatus(reservationId);
            this.loadReservations();
            this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'You have accepted' });
        },
        reject: () => {
            this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected', life: 3000 });
        }
    });
}

   updateReservationStatus(reservationId:Number){
    this.reservationService.changeResrvationStatus(reservationId).subscribe((response)=>

      console.log(response)
      
    )
   }
 
}
