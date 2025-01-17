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
@NgModule({
  declarations: [TopBarComponent],
  exports: [
    TopBarComponent,
    CommonModule,
    IconFieldModule,
    InputIconModule,
    InputTextModule,
    FormsModule,
    SkeletonModule,
    ConfirmDialogModule,
    FileUploadModule,
    InputMaskModule,
    ToastModule
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
    ToastModule
  ],
  providers: [MessageService,DialogService,MessageService,ConfirmationService],
})
export class SharedModule { }
