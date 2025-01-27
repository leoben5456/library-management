import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';

interface Message {
  text: string;
  sender: string;
}

interface BotResponse {
  sql_query: string;
  results: number[][];

}
@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.scss'],
  animations: [
    trigger('popupAnimation', [
      state('closed', style({
        opacity: 0,
        transform: 'scale(0.8) translateY(50%)'
      })),
      state('open', style({
        opacity: 1,
        transform: 'scale(1) translateY(0)'
      })),
      transition('closed => open', [
        animate('500ms ease-out', style({
          opacity: 1,
          transform: 'scale(1.05) translateY(0)'
        })),
        animate('100ms ease-in')
      ]),
      transition('open => closed', [
        animate('500ms ease-in')
      ])
    ])
  ]

})
export class ChatbotComponent implements OnInit {
  isPopupOpen = false;
  messages: Message[] = [];
  userInput: string = '';

  constructor() { }

  ngOnInit() {
    this.messages.push({
      text: 'How can I help you today?',
      sender: 'Bot',

    });






  }
  openPopup() {
    this.isPopupOpen = !this.isPopupOpen;
  }

  

  }







