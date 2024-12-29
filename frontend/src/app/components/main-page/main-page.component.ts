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
      image: 'https://prodimage.images-bn.com/pimages/9781804090114_p0_v2_s1200x630.jpg'
    },
    {
      title: 'How Innovation Works',
      author: 'Matt Ridley',
      image: 'https://prodimage.images-bn.com/pimages/9781804090114_p0_v2_s1200x630.jpg'
    },
    {
      title: 'Company of One',
      author: 'Paul Jarvis',
      image: 'https://prodimage.images-bn.com/pimages/9781804090114_p0_v2_s1200x630.jpg'
    },
    {
      title: 'Stupore e Tremori',
      author: 'Amélie Nothomb',
      image: 'https://prodimage.images-bn.com/pimages/9781804090114_p0_v2_s1200x630.jpg'
    }
  ];

}
