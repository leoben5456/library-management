import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.scss'
})
export class TopBarComponent {
   
   notificationsCount = 4;
   items: MenuItem[] | undefined;

   ngOnInit() {
       this.items = [
           {
               label: 'Notifications',
               items: [
                   {
                       label: 'Your password have been updated.Your password have been updated',
                       icon: 'pi pi-refresh'
                   },
                   {
                       label: 'Export',
                       icon: 'pi pi-upload'
                   }
               ]
           }
       ];
   }
  constructor() { }

}
