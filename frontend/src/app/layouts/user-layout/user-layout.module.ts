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
import { TagModule } from 'primeng/tag';
import { BookDetailsComponent } from '../../components/book-details/book-details.component';
import { UserReservationsComponent } from '../../components/user-reservations/user-reservations.component';
import { UserWishlistComponent } from '../../components/user-wishlist/user-wishlist.component';
import { CalendarModule } from 'primeng/calendar';
import { SkeletonModule } from 'primeng/skeleton';
@NgModule({
  declarations: [
    UserLayoutComponent,
    SideBarComponent,
    MainPageComponent,
    TruncatePipe,
    DiscoverComponent,
    BookDetailsComponent,
    UserReservationsComponent,
    UserWishlistComponent,

    
    
    
    

  ],
  imports: [
    UserLayoutRoutingModule,
    SharedModule,
    RatingModule,
    PaginatorModule,
    TagModule,
    CalendarModule,
    SkeletonModule,
    
  ]
})
export class UserLayoutModule { }
