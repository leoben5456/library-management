import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.scss'
})
export class SigninComponent {
   checked: boolean = false;
   SignupForm!: FormGroup;
   stateOptions: any[] = [
    { label: 'Student', value: 'Etudiant' },
    { label: 'Teacher', value: 'Prof' }
   ];
   value: string = 'Etudiant';
    constructor(private fb: FormBuilder,private authservice:AuthService) {
  
     
    }
  
    ngOnInit(): void {
      this.SignupForm = this.fb.group({
  
        email: ['', [Validators.required, Validators.email]],


  
        password: ['',[Validators.required, Validators.minLength(6)]]
  
      });
    }
  
  
    
    onSignup(){
     console.log(this.SignupForm.value)
  
      
   }

}
