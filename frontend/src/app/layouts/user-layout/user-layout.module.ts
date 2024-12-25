import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserLayoutComponent } from './user-layout.component';
import { UserLayoutRoutingModule } from './user-layout-routing.module';
import { SideBarComponent } from '../../components/side-bar/side-bar.component';
import { SharedModule } from '../../modules/shared/shared.module';


@NgModule({
  declarations: [
    UserLayoutComponent,
    SideBarComponent,

  ],
  imports: [
    CommonModule,
    UserLayoutRoutingModule,
    SharedModule,
  ]
})
export class UserLayoutModule { }
