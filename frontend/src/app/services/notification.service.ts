import { Injectable } from '@angular/core';
import { Client, Message, StompHeaders, frameCallbackType } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private client: Client;
  private token: string | null;
  private socketUrl: string;

  constructor() {
    
    this.token = localStorage.getItem('token');

    
    this.socketUrl = 'ws://localhost:8080/notification-service/ws';
    this.client = new Client({
      brokerURL: this.token
        ? `${this.socketUrl}?token=${this.token}`
        : this.socketUrl,
  
      reconnectDelay: 5000,  // Reconnect after 5s if disconnected
  
      heartbeatIncoming: 10000, // Expect server heartbeat every 10s
      heartbeatOutgoing: 10000, // Send heartbeat to server every 10s
  
      onConnect: (frame) => {
          console.log('STOMP connected:', frame);
  
          this.client.subscribe('/user/queue/notification', (message: Message) => {
              console.log('Received message:', message.body);
          });
      },
  
      onStompError: (frame) => {
          console.error('Broker reported error:', frame.headers['message']);
          console.error('Additional details:', frame.body);
      },
  
      debug: (str: string) => {
          console.log(new Date(), str);
      }
  });
}
  

  /** Establishes the STOMP WebSocket connection. */
  public connect(): void {
    // Only activate if not already active
    if (!this.client.active) {
      this.client.activate();
      console.log('Attempting STOMP connection...');
    }
  }

  /** Gracefully disconnect from the STOMP WebSocket. */
  public disconnect(): void {
    if (this.client && this.client.active) {
      this.client.deactivate();
      console.log('STOMP disconnected');
    }
  }
}
