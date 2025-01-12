import { Component } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.scss'
})
export class SideBarComponent {
    constructor(private confirmationService: ConfirmationService,private authService :AuthService ) {}

    confirm1(event: Event) {
      console.log(event);

      this.authService.logout();
      this.confirmationService.confirm({
          target: event.target as EventTarget,
          message: 'Are you sure that you want to proceed?',
          header: 'Confirmation',
          icon: 'pi pi-exclamation-triangle',
          acceptIcon:"none",
          rejectIcon:"none",
          rejectButtonStyleClass:"p-button-text",
          accept: () => {
         },
          reject: () => {
          }
      });
  }
}
