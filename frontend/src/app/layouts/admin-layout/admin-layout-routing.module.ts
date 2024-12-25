// admin-layout-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminLayoutComponent } from './admin-layout.component';
import { DashboardComponent } from '../../components/dashboard/dashboard.component';
import { BooksTableComponent } from '../../components/books-table/books-table.component';

const routes: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: '',
        component: DashboardComponent, 
      },
      {
        path:'Manage/Books',
        component:BooksTableComponent
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminLayoutRoutingModule {}
