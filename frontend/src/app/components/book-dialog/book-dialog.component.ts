import { Component, OnInit } from '@angular/core';
import { Book } from '../../services/book.service';
import { MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-book-dialog',
  templateUrl: './book-dialog.component.html',
  styleUrls: ['./book-dialog.component.scss'],
  providers: [DialogService] // Ensure DialogService is provided
})
export class BookDialogComponent implements OnInit {
  
  value: string = ' ';
  languages: any[] = [];
  selectedLanguage: string = 'English';

  constructor(
    private messageService: MessageService,
    public dialogService: DialogService,
    private ref: DynamicDialogRef
  ) { }

  ngOnInit(): void {
    this.languages = [
      { name: 'English', code: 'en' },
      { name: 'French', code: 'fr' },
      { name: 'Arabic', code: 'ar' }
    ];
  }

  close() {
    this.ref.close(); 
  }

  save() {
    this.messageService.add({ 
      severity: 'success', 
      summary: 'Success', 
      detail: 'Book saved successfully!' 
    });
    this.close(); 
  }
}
