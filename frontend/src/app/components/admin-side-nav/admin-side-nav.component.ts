import { Component } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-admin-side-nav',
  templateUrl: './admin-side-nav.component.html',
  styleUrl: './admin-side-nav.component.scss'
})
export class AdminSideNavComponent {


  constructor(private confirmationService: ConfirmationService,private authService:AuthService){}

  confirm(event: Event) {
    this.confirmationService.confirm({
        target: event.target as EventTarget,
        message: 'Are you sure that you want to logout?',
        header: 'Logout',
        icon: 'pi pi-sign-out',
        acceptIcon:"none",
        rejectIcon:"none",
        rejectButtonStyleClass:"p-button-text",
        accept: () => {
          this.authService.logout();
        },
        reject: () => {

        }
  });
}


  

}
