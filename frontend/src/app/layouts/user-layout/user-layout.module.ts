import { NgModule } from '@angular/core';
import { UserLayoutComponent } from './user-layout.component';
import { UserLayoutRoutingModule } from './user-layout-routing.module';
import { SideBarComponent } from '../../components/side-bar/side-bar.component';
import { SharedModule } from '../../modules/shared/shared.module';
import { MainPageComponent } from '../../components/main-page/main-page.component';
import { TruncatePipe } from '../../core/pipes/truncate.pipe';
import { RatingModule } from 'primeng/rating';
import { DiscoverComponent } from '../../components/discover/discover.component';
import { PaginatorModule } from 'primeng/paginator';


@NgModule({
  declarations: [
    UserLayoutComponent,
    SideBarComponent,
    MainPageComponent,
    TruncatePipe,
    DiscoverComponent,
    
    
    

  ],
  imports: [
    UserLayoutRoutingModule,
    SharedModule,
    RatingModule,
    PaginatorModule 
    
  ]
})
export class UserLayoutModule { }
