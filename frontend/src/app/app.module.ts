import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { SharedModule } from './modules/shared/shared.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { SigninComponent } from './components/signin/signin.component';
import { SelectButtonModule } from 'primeng/selectbutton';
import { FormsModule } from '@angular/forms';
import { authInterceptor } from './core/interceptors/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SigninComponent,
    
    





  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    InputTextModule,
    FloatLabelModule,
    ButtonModule,
    ReactiveFormsModule,
    CardModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    SharedModule,
    BrowserAnimationsModule,
    HttpClientModule,
    SelectButtonModule,
    FormsModule,
    
  ],
  providers: [{provide: HTTP_INTERCEPTORS,useClass: authInterceptor,multi: true,},],
  bootstrap: [AppComponent],
})
export class AppModule {}
