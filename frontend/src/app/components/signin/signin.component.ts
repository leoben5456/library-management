import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { RegistrationService } from '../../services/registration.service';

export interface User{
  email: string,
  password: string,
  name: string,
  role: string,
  telephone: string,

}

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent {
  SignupForm!: FormGroup;
  stateOptions: any[] = [
    { label: 'Teacher', value: 'Prof' },
    { label: 'Student', value: 'Etudiant' }
  ];
  selectedFile!: File;
  fileError: string = '';

  constructor(
    private fb: FormBuilder,
    private registrationService: RegistrationService,
    
  ) {}

  ngOnInit(): void {
    this.SignupForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      name: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      phone: ['', [Validators.required, Validators.pattern(/^\d{3}-\d{6}$/)]],
      role: ['', Validators.required]
    });

    // Initialize the role field with a default value
    this.SignupForm.get('role')?.setValue(this.stateOptions[0].value);
  }

  onFileSelect(event: any) {
    const files: File[] = event.currentFiles;
    if (files && files.length > 0) {
      const file = files[0];
      // Validate file type
      
      // Validate file size (already limited by p-fileUpload's maxFileSize)
      this.selectedFile = file;
      this.fileError = '';
    }
  }

  onSignup() {
    if (this.SignupForm.invalid) {
      this.SignupForm.markAllAsTouched();
      return;
    }

    const user: User = {
      email: this.SignupForm.value.email,
      name: this.SignupForm.value.name,
      password: this.SignupForm.value.password,
      telephone: this.SignupForm.value.phone,
      role: this.SignupForm.value.role
    };

    this.registrationService.register(user, this.selectedFile).subscribe({
      next: (response) => {
        this.SignupForm.reset();
        
      },
      error: (error) => {
      }
    });
  }
}