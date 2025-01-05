import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminLayoutComponent } from './admin-layout.component';
import { DashboardComponent } from '../../components/dashboard/dashboard.component';
import { BooksTableComponent } from '../../components/books-table/books-table.component';
import { ReservationTableComponent } from '../../components/reservation-table/reservation-table.component';
import { AuthGuard } from '../../core/guards/auth.guard';
import { RoleGuard } from '../../core/guards/role.guard';


const routes: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    
    
    children: [
      {
        path: 'Dashboard',
        component: DashboardComponent,
        
        
      },
      {
        path: 'Manage/Books',
        component: BooksTableComponent,
        
        
      },
      {
        path: 'Reservations',
        component: ReservationTableComponent,
        
       
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminLayoutRoutingModule {}