import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserLayoutComponent } from './user-layout.component';
import { MainPageComponent } from '../../components/main-page/main-page.component';
import { AuthGuard } from '../../core/guards/auth.guard';
import { RoleGuard } from '../../core/guards/role.guard';
import { DiscoverComponent } from '../../components/discover/discover.component';

const routes: Routes = [
  {
    path: '',
    component: UserLayoutComponent,
    canActivate: [AuthGuard],
    
    
    children: [
      {
        path: '',
        component: MainPageComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Etudiant'] },

      },

      {
        path:'Discover',
        component: DiscoverComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Etudiant'] },
      }


    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserLayoutRoutingModule {}