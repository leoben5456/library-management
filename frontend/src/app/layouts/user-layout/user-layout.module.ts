import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserLayoutComponent } from './user-layout.component';
import { UserLayoutRoutingModule } from './user-layout-routing.module';
import { TopBarComponent } from '../../components/top-bar/top-bar.component';
import { SideBarComponent } from '../../components/side-bar/side-bar.component';


@NgModule({
  declarations: [
    UserLayoutComponent,
    TopBarComponent, 
    SideBarComponent,

  ],
  imports: [
    CommonModule,
    UserLayoutRoutingModule
  ]
})
export class UserLayoutModule { }
