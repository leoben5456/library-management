import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
    checked: boolean = false;
    LoginForm!: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.LoginForm = this.fb.group({

      email: ['', [Validators.required, Validators.email]],

      password: ['',[Validators.required, Validators.minLength(6)]]

    });
  }


  
  onLogin(){
   console.log(this.LoginForm.value)
 }

}
