import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopBarComponent } from '../../components/top-bar/top-bar.component';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';
import { SkeletonModule } from 'primeng/skeleton';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { FileUploadModule } from 'primeng/fileupload';
import { InputMaskModule } from 'primeng/inputmask';
import { ToastModule } from 'primeng/toast';
import { BadgeModule } from 'primeng/badge';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { ChatbotComponent } from '../../components/chatbot/chatbot.component';
import { MenuModule } from 'primeng/menu';

@NgModule({
  declarations: [TopBarComponent,ChatbotComponent],
  exports: [
    TopBarComponent,
    CommonModule,
    ChatbotComponent,
    IconFieldModule,
    InputIconModule,
    InputTextModule,
    FormsModule,
    SkeletonModule,
    ConfirmDialogModule,
    FileUploadModule,
    InputMaskModule,
    ToastModule,
    BadgeModule,
    SkeletonModule,
    MatIconModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MenuModule
  ],

  
  imports: [
    CommonModule,
    IconFieldModule,
    InputIconModule,
    InputTextModule,
    FormsModule,
    SkeletonModule,
    ConfirmDialogModule,
    FileUploadModule,
    InputMaskModule,
    ToastModule,
    BadgeModule,
    SkeletonModule,
    MatIconModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MenuModule
  ],
  providers: [MessageService,DialogService,MessageService,ConfirmationService],
})
export class SharedModule { }
