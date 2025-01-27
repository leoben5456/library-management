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

    constructor(private fb: FormBuilder,private authservice:AuthService,private router:Router) {

    
    }

    ngOnInit(): void {
      this.LoginForm = this.fb.group({

        email: ['', [Validators.required, Validators.email]],

        password: ['',[Validators.required, Validators.minLength(5)]]

      });
    }


    
    onLogin(){
    console.log(this.LoginForm.value)
      this.isLoading=true;
      this.authservice.authentificate(this.LoginForm.value).subscribe(
        data => {
          
          if(data.refreshToken!=null && data.accessToken!= null){
            const userRole=this.authservice.userRole(data.accessToken);
            if(userRole=="admin"){
              this.authservice.storeToken(data.accessToken,data.refreshToken);
              this.router.navigate(['/admin/Dashboard']);
            }
            else if(userRole=="Etudiant" || userRole=="Prof"){
              this.authservice.storeToken(data.accessToken,data.refreshToken);
              this.router.navigate(['']);
            }
            
          }

          this.isLoading=false;
        });
  }

  }



  

  /*

  {
      "email":"tahaelhajhouj2@gmail.com",
      "password":"securePassword123"
  }

  */