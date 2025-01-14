import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookService } from '../../services/book.service';
import { MessageService } from 'primeng/api';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-book-dialog',
  templateUrl: './book-dialog.component.html',
  styleUrls: ['./book-dialog.component.scss'],
  providers: [DialogService]
})
export class BookDialogComponent implements OnInit {
  bookForm!: FormGroup;
  languages: any[] = [];
  selectedLanguage: string = 'English';
  selectedFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService,
    public dialogService: DialogService,
    private ref: DynamicDialogRef,
    private bookService: BookService
  ) {}

  ngOnInit(): void {
    this.bookForm = this.fb.group({
      titre: ['', ],
      auteur: ['',],
      genre: [''],
      datePublication: ['',],
      langue: ['English',],
      quantite: [0, ],
      category: ['',],
      description: [''],
      rating: [0, ],
    
    });
  }

  onFileSelect(event: any) {
    const file = event?.files?.[0];
    if (file) {
      this.selectedFile = file;
      this.bookForm.patchValue({
        cover: file
      });
    }
  }

  close(): void {
    this.ref.close();
  }

  save(): void {
    if (this.bookForm.valid) {
      const formData = this.bookForm.value;
      console.log(formData);

      this.bookService.CreateNewBook(formData, this.selectedFile!).subscribe({
        next: (response) => {
          console.log(response);
          this.messageService.add({ 
            severity: 'success', 
            summary: 'Success', 
            detail: 'Book saved successfully!' 
          });
          this.close();
        },
        error: (err) => {
          console.error(err);
          this.messageService.add({ 
            severity: 'error', 
            summary: 'Error', 
            detail: 'Failed to save book.' 
          });
        }
      });
    } else {
      this.bookForm.markAllAsTouched();
    }
  }
}
