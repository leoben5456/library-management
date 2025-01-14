import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminLayoutComponent } from './admin-layout.component';
import { AdminLayoutRoutingModule } from './admin-layout-routing.module';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { DashboardComponent } from '../../components/dashboard/dashboard.component';
import { SharedModule } from '../../modules/shared/shared.module';
import { BooksTableComponent } from '../../components/books-table/books-table.component';
import {MatDialogModule} from '@angular/material/dialog';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import { HttpClientModule } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { BookDialogComponent } from '../../components/book-dialog/book-dialog.component';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { NgApexchartsModule } from 'ng-apexcharts';
import { PieChartComponent } from '../../components/pie-chart/pie-chart.component';
import { RadarChartComponent } from '../../components/radar-chart/radar-chart.component';
import { BarChartComponent } from '../../components/bar-chart/bar-chart.component';
import { DialogService } from 'primeng/dynamicdialog';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { DialogModule } from '@angular/cdk/dialog';
import { FileUploadModule } from 'primeng/fileupload';
import { ReservationTableComponent } from '../../components/reservation-table/reservation-table.component';
import { AdminSideNavComponent } from '../../components/admin-side-nav/admin-side-nav.component';
import { InputNumberModule } from 'primeng/inputnumber';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AdminLayoutComponent,
    DashboardComponent,
    BooksTableComponent,
    BookDialogComponent,
    PieChartComponent,
    RadarChartComponent,
    BarChartComponent,
    ReservationTableComponent,
    AdminSideNavComponent,
  ],
  imports: [
    CommonModule,
    AdminLayoutRoutingModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    SharedModule,
    MatDialogModule,
    TableModule,
    TagModule,
    MultiSelectModule,
    DropdownModule,
    HttpClientModule,
    ButtonModule,
    ToastModule,
    NgApexchartsModule,
    DialogModule,
    DynamicDialogModule,
    FileUploadModule,
    InputNumberModule,
    ReactiveFormsModule


  ],
  
})
export class AdminLayoutModule { }
