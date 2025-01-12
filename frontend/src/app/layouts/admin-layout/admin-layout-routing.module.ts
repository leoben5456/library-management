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
    canActivate: [AuthGuard],
    
    
    children: [
      {
        path: 'Dashboard',
        component: DashboardComponent,
        canActivate: [RoleGuard],
        data: { roles: ['admin'] },
        
        
        
      },
      {
        path: 'Manage/Books',
        component: BooksTableComponent,
        canActivate: [RoleGuard],
        data: { roles: ['admin'] },
        
        
      },
      {
        path: 'Reservations',
        component: ReservationTableComponent,
        canActivate: [RoleGuard],
        data: { roles: ['admin'] },
        
       
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminLayoutRoutingModule {}