// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./layouts/user-layout/user-layout.module').then(
        (m) => m.UserLayoutModule
      ),
  },
  {
    path: 'admin',
    loadChildren: () =>
      import('./layouts/admin-layout/admin-layout.module').then(
        (m) => m.AdminLayoutModule
      ),
  },
  {
    path: '**',
    redirectTo: '',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
