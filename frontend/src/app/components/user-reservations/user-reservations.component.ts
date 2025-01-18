import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../../services/reservation.service';
import { PaginatorState } from 'primeng/paginator';

@Component({
  selector: 'app-user-reservations',
  templateUrl: './user-reservations.component.html',
  styleUrls: ['./user-reservations.component.scss']
})
export class UserReservationsComponent implements OnInit {
  options: any[] = [];
  selectedValue: any;
  customPlaceholder: string = 'Filter';
  reservations: any[] = [];
  filteredReservations: any[] = [];

  page: number = 0;

  size: number = 9;

  Books:any[] = [];

  totalRecords: number = 0;

  constructor(private reservationService: ReservationService) {}

  ngOnInit(): void {
    this.options = [
      { viewValue: 'All', color: '#ffffff' },
      { viewValue: 'Active', color: '#e8fde7' },
      { viewValue: 'Ended', color: '#b5c5e9' },
      { viewValue: 'Overdue', color: '#f8d7da' }
    ];
    this.loadReservations();
  }

  loadReservations() {
    this.reservationService.getAllReservations(0, 10).subscribe((res) => {
      this.reservations = res.content;
      this.totalRecords = res.totalElements;
      this.filteredReservations = this.reservations;
    });
  }

  CheckReservationStatus(ReturnDate: string, isReturned: boolean) {
    const returnDate = new Date(ReturnDate);
    const now = new Date();
    if (returnDate > now && !isReturned) {
      return 'Active';
    } else if (returnDate < now && !isReturned) {
      return 'Overdue';
    } else {
      return 'Ended';
    }
  }

  getSeverity(status: string) {
    if (status === 'Active') {
      return 'success';
    } else if (status === 'Overdue') {
      return 'danger';
    } else {
      return 'info';
    }
  }

  filterReservations() {
    if (!this.selectedValue || this.selectedValue.viewValue === 'All') {
      this.filteredReservations = this.reservations;
    } else {
      this.filteredReservations = this.reservations.filter(
        (r) => this.CheckReservationStatus(r.dateExpiration, r.returned) === this.selectedValue.viewValue
      );
    }
  }


  onPageChange(event: PaginatorState) {
      this.page = event.page || 0; 
      this.size = event.rows || 6;
      this.loadReservations(); 
    }
}
