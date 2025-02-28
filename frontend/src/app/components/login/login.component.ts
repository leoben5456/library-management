  import { Component } from '@angular/core';
  import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
  import { AuthService } from '../../services/auth.service';
  import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';

  @Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
  })
  export class LoginComponent {
      checked: boolean = false;
      LoginForm!: FormGroup;
      isLoading:boolean=false;
      errorMessage: string = '';

    constructor(private fb: FormBuilder,private authservice:AuthService,private router:Router) {

    
    }

    ngOnInit(): void {
      this.LoginForm = this.fb.group({

        email: ['', [Validators.required, Validators.email]],

        password: ['',[Validators.required, Validators.minLength(5)]]

      });
    }


    
    onLogin() {
      console.log(this.LoginForm.value); // Log form values for debugging
      this.isLoading = true; // Show loading spinner
    
      this.authservice.authentificate(this.LoginForm.value).subscribe({
        next: (data: { refreshToken: string; accessToken: string }) => {
          // Check if tokens are present
          if (data.refreshToken && data.accessToken) {
            // Store tokens
            this.authservice.storeToken(data.accessToken, data.refreshToken);
    
            // Determine user role
            const userRole = this.authservice.userRole(data.accessToken);
    
            // Navigate based on role
            switch (userRole) {
              case 'admin':
                this.router.navigate(['/admin/Dashboard']);
                break;
              case 'Etudiant':
              case 'Prof':
                this.router.navigate(['']);
                break;
              default:
                console.error('Unknown role:', userRole);
                // Handle unknown role (e.g., show an error message)
                this.errorMessage='Unknown user role. Please contact support.';
            }
          } else {
            // Handle missing tokens
            this.errorMessage='Login failed: No tokens received.';
           
          }
        },
        error: (error) => {
          // Handle login errors using the authService's handleError method
            this.errorMessage=error.message
            console.log(this.errorMessage);
            this.isLoading=false;
        },
        complete: () => {
          // Reset loading state
          this.isLoading = false;
        },
      });
    }
    
    

  }



  

  /*

  {
      "email":"tahaelhajhouj2@gmail.com",
      "password":"securePassword123"
  }

  */