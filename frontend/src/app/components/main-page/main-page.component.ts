import { Component, OnInit } from '@angular/core';
import { BookService } from '../../services/book.service';
import { NotificationService } from '../../services/notification.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss'
})
export class MainPageComponent implements OnInit {

   
  isLoading:boolean = false;

  BooksByGengre:any[] = [];

  RecommendedBooks:any[] = [];
 
  

constructor(private bookService:BookService,private notificationService:NotificationService) { }
  
  ngOnInit(): void {
    
    this.loadBooksBygenre('All');

    this.notificationService.connect();

    
  
    
  }


  
  

  value:number =4.5;
  books = [
    {
      id: 1,
      title: 'The Psychology of Money',
      author: 'Morgan Housel',
      description: 'Explores the way we think about money and wealth.Explores the way we think about money and wealth.Explores the way we think about money and wealth. Explores the way we think about money and wealth.',
      stars: 5,
      image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJorThOoxXmdX6dF-vJ_YH2ZlPx6jqtgGrhg&s'
    },
    {
      id: 2,
      title: 'How Innovation Works',
      author: 'Matt Ridley',
      description: 'Explores the way we think about money and wealth.Explores the way we think about money and wealth.Explores the way we think about money and wealth. Explores the way we think about money and wealth.',
      stars: 2,
      image: 'https://images.squarespace-cdn.com/content/v1/6129e1d90fd5785f5023e7dd/1646777757537-KJH07HRMXHO4FU15BJFL/MATT+RIDLEY.jpg'
    },
    {
      id: 3,
      title: 'Company of One',
      author: 'Paul Jarvis',
      description: 'Explores the way we think about money and wealth.Explores the way we think about money and wealth.Explores the way we think about money and wealth. Explores the way we think about money and wealth.',
      stars: 3,
      image: 'https://cdn.prod.website-files.com/64fb9e01844ff7d2dd1fa177/64fb9e01844ff7d2dd1fa6c9_company-of-one.jpg'
    },
    {
      id: 4,
      title: 'Stupore e Tremori',
      author: 'Amélie Nothomb',
      description: 'Explores the way we think about money and wealth.Explores the way we think about money and wealth.Explores the way we think about money and wealth. Explores the way we think about money and wealth.',
      stars: 3,
      image: 'https://cdn.prod.website-files.com/65441765dc6dcc1bb6d1a57f/654507e52f8520489d144d44_paolo-chiabrando-9dXSoi6VXEA-unsplash.webp'
    },
    {
      id: 5,
      title: 'Stupore e Tremori',
      author: 'Amélie Nothomb',
      description: 'Explores the way we think about money and wealth.Explores the way we think about money and wealth.Explores the way we think about money and wealth. Explores the way we think about money and wealth.',
      stars: 4,
      image: 'https://static.wixstatic.com/media/c78e34_2bf63156c3be4f668cf1cd55854945dc~mv2.jpg/v1/fill/w_528,h_546,al_c,lg_1,q_80,enc_auto/c78e34_2bf63156c3be4f668cf1cd55854945dc~mv2.jpg'
    }
  ];

  selectedCategory:string = 'All';
  categories = [
    { name: 'All'},
    { name: 'Fantasy' },
    { name: 'Fiction'},
    { name: 'Adventure'},
    { name: 'Romance'},
    { name: 'Technology'},
    { name: 'Historical'},
    { name: 'Novel'}
  ];

  

  selectCategory(category:string){
    this.selectedCategory = category;
    this.loadBooksBygenre(category);
  }

  selectedBookId:number=1;

  selectBook(id:number){
    this.selectedBookId = id;
  }


 loadBooksBygenre(genre:string){
    this.isLoading = true;
    this.bookService.getBooksByGenre(genre).subscribe(
      (response:any)=>{
        console.log(response);
        this.BooksByGengre = response;
        this.isLoading = false;
      },
      (error)=>{
        console.log(error);
        this.isLoading = true;
      }
    )

  }






 getBookCover(book:any):string{
  const  imgPrefix:string = 'http://localhost:8080/livre-service/uploads/book-cover/';
   
  return imgPrefix + book.coverPath;

}


}
