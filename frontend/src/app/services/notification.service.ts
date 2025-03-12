import { Injectable } from '@angular/core';
import { Client, Message, StompHeaders, frameCallbackType } from '@stomp/stompjs';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class NotificationService  {
  private client: Client;
  private token: string | null;
  private socketUrl: string;
  private notificationCount = new BehaviorSubject<number>(0);
  notificationCount$ = this.notificationCount.asObservable();

  constructor(private http: HttpClient) {
    this.token = localStorage.getItem('token');
    this.socketUrl = environment.notificationApi;
    this.client = new Client({
      brokerURL: this.token ? `${this.socketUrl}?token=${this.token}` : this.socketUrl,
      reconnectDelay: 5000,
      heartbeatIncoming: 10000,
      heartbeatOutgoing: 10000,
      onConnect: (frame) => {
        console.log('STOMP connected:', frame);
        this.client.subscribe('/user/exchange/amq.direct/notification', (message: Message) => {
          this.setNotificationCount(this.notificationCount.value + 1);
          console.log('Received message:', message.body);
        });
      },
      onStompError: (frame) => {
        console.error('Broker reported error:', frame.headers['message']);
        console.error('Additional details:', frame.body);
      },
    });

    // Load initial unread notifications count
    this.initializeNotificationCount();
  }



  
  

  /** Fetch initial unread notification count from backend */
  private initializeNotificationCount(): void {
    this.loadUnreadNotifications().subscribe(
      (response: any) => {
        if (response && response.length !== undefined) {
          this.setNotificationCount(response.length);
          
        }
      },
      (error) => {
        console.error('Failed to load unread notifications:', error);
      }
    );
  }

  /** Connects to STOMP WebSocket */
  public connect(): void {
    if (!this.client.active) {
      this.client.activate();
      console.log('Attempting STOMP connection...');
    }
  }

  /** Disconnects from STOMP WebSocket */
  public disconnect(): void {
    if (this.client && this.client.active) {
      this.client.deactivate();
      console.log('STOMP disconnected');
    }
  }

  /** Updates notification count */
  setNotificationCount(count: number) {
    this.notificationCount.next(count);
  }

  /** Load unread notifications from backend */
  loadUnreadNotifications(): Observable<any> {
    const apiUrl = environment.loadUnreadNotificationsApi;
    return this.http.get(apiUrl);
  }

  markNotificationAsRead():Observable<any>{
    const apiUrl=environment.markAsread
    return this.http.post(apiUrl, {}, { responseType: 'json' });
  }
}