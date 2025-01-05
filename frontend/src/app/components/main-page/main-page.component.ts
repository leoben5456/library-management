import { Component } from '@angular/core';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss'
})
export class MainPageComponent {
  books = [
    {
      title: 'The Psychology of Money',
      author: 'Morgan Housel',
      image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJorThOoxXmdX6dF-vJ_YH2ZlPx6jqtgGrhg&s'
    },
    {
      title: 'How Innovation Works',
      author: 'Matt Ridley',
      image: 'https://images.squarespace-cdn.com/content/v1/6129e1d90fd5785f5023e7dd/1646777757537-KJH07HRMXHO4FU15BJFL/MATT+RIDLEY.jpg'
    },
    {
      title: 'Company of One',
      author: 'Paul Jarvis',
      image: 'https://cdn.prod.website-files.com/64fb9e01844ff7d2dd1fa177/64fb9e01844ff7d2dd1fa6c9_company-of-one.jpg'
    },
    {
      title: 'Stupore e Tremori',
      author: 'Amélie Nothomb',
      image: 'https://cdn.prod.website-files.com/65441765dc6dcc1bb6d1a57f/654507e52f8520489d144d44_paolo-chiabrando-9dXSoi6VXEA-unsplash.webp'
    },
    {
      title: 'Stupore e Tremori',
      author: 'Amélie Nothomb',
      image: 'https://static.wixstatic.com/media/c78e34_2bf63156c3be4f668cf1cd55854945dc~mv2.jpg/v1/fill/w_528,h_546,al_c,lg_1,q_80,enc_auto/c78e34_2bf63156c3be4f668cf1cd55854945dc~mv2.jpg'
    }
  ];



  categories = ["All","Business", "Fiction", "Non-Fiction", "Self-Help", "Technology", "History","Biography"];

}
