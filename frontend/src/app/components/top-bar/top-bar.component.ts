import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.scss'
})
export class TopBarComponent {
   
   notificationsCount = 0;
   items: MenuItem[] | undefined;
   newNotifications:any[]=[]


   constructor(private notificationService: NotificationService) {}

   ngOnInit() {

         this.notificationService.notificationCount$.subscribe((count) => {
              this.notificationsCount =count;
         });

        

         this.loadUserUnreadNotification()
         this.items = [
           {
               label: 'Notifications',
               
               items: []
           }
       ];
   }
  



  loadUserUnreadNotification(): void {
    this.notificationService.loadUnreadNotifications().subscribe(
      (notifications) => {
        this.newNotifications = notifications;
        this.notificationService.setNotificationCount(notifications.length);
        this.updateNotificationMenu();
       
      },
      (error) => {
        console.error('Error loading notifications:', error);
      }
    );

  }
  private updateNotificationMenu(): void {
    // Create menu items from notifications
    const notificationItems = this.newNotifications.map(notification => {
      return {
        label: notification.message || notification.content,
        icon: notification.icon || 'pi pi-bell',
        
      };
    });
    
    // Update the menu structure
    this.items = [
      {
        label: 'Notifications',
        items: notificationItems.length > 0 
          ? notificationItems 
          : [{ label: 'No new notifications', disabled: true }]
      }
    ];
  }

   markAsread(){
    this.notificationService.markNotificationAsRead().subscribe((res)=>{
      console.log(res)
      this.notificationService.setNotificationCount(0); 
    })
  }

  toggleNotificationMenu(event:any,menu: any){
      
    menu.toggle(event);
    

  }
  
  onMenuShow(): void {
    this.loadUserUnreadNotification();
    if (this.notificationsCount > 0) {
      this.markAsread();
    }
  }
}
