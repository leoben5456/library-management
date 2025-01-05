import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopBarComponent } from '../../components/top-bar/top-bar.component';



@NgModule({
  declarations: [TopBarComponent],
  exports: [
    TopBarComponent,
    CommonModule
  ],

  
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
