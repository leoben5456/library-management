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
  loading: boolean = false;

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

   // AI Auto-Fill Function
   fillBookDetailsWithAI() {
    const bookTitle = this.bookForm.get('titre')?.value;
    if (!bookTitle) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Warning',
        detail: 'Please enter a book title first.'
      });
      return;
    }
  
    this.loading = true;
    this.bookService.fillFieldWithAi(bookTitle).subscribe({
      next: (response) => {
        this.loading = false;
        console.log('Gemini API response:', response);
  
        if (response?.candidates?.length) {
          // 1. Get the text returned by the model.
          let content = response.candidates[0].content.parts[0].text || '';
          
          // 2. Remove the ```json and ``` markers if they exist.
          //    This helps ensure valid JSON string before parsing.
          content = content.replace(/```json|```/g, '').trim();
          
          try {
            // 3. Parse the JSON.
            const parsedData = JSON.parse(content);
  
            // 4. Update your form fields using parsedData.
            this.bookForm.patchValue({
              titre: parsedData.book_name || '',
              auteur: parsedData.author_name || '',
              description: parsedData.description || '',
              langue: parsedData.language || '',
              category: parsedData.category || '',
              quantite: parsedData.quantity || 0,
              rating: parsedData.rating || 0
            });
  
            this.messageService.add({
              severity: 'success',
              summary: 'Success',
              detail: 'Book details retrieved successfully!'
            });
          } catch (parseError) {
            console.error('Failed to parse JSON:', parseError);
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Could not parse AI response JSON.'
            });
          }
  
        } else {
          this.messageService.add({
            severity: 'warn',
            summary: 'Warning',
            detail: 'No candidates returned from Gemini.'
          });
        }
      },
      error: (err) => {
        this.loading = false;
        console.error('Gemini API error:', err);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Failed to retrieve book details.'
        });
      }
    });
  }
  

  

}
