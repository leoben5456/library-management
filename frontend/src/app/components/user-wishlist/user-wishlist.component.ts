import { Component, OnInit } from '@angular/core';
import { WishlistService } from '../../services/wishlist.service';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { PaginatorState } from 'primeng/paginator';
import { BookDetailsComponent } from '../book-details/book-details.component';

@Component({
  selector: 'app-user-wishlist',
  templateUrl: './user-wishlist.component.html',
  styleUrl: './user-wishlist.component.scss'
})
export class UserWishlistComponent implements OnInit {

  Books:any[] = [];

  page: number = 0;
  
  size: number = 9;
  
  
  totalRecords: number = 0;
   
  ref: DynamicDialogRef | undefined;


  constructor(private wishlistService:WishlistService,private dialogService:DialogService) { }
  ngOnInit(): void {
    this.loadBooksFromUserWishlist();
  }


  onPageChange(event: PaginatorState) {
    this.page = event.page || 0; 
    this.size = event.rows || 6;
    this.loadBooksFromUserWishlist();
  }




  loadBooksFromUserWishlist(){
    this.wishlistService.getBooksFromUserWishlist().subscribe((data:any)=>{
      this.Books = data;
      console.log(this.Books);
    })
  }

  getBookCover(book:any):string{
      const  imgPrefix:string = 'http://localhost:8080/livre-service/uploads/book-cover/';
       
      return imgPrefix + book.coverPath;
  
    }
    
  
    showBookDetails(book: any) {
      this.ref = this.dialogService.open(BookDetailsComponent, {
        header: book.titre,
        width: '30%',
        height: '75vh',
        data:book
        
      });
    }
     
  
    ngOnDestroy() {
      if (this.ref) {
        this.ref.close();
      }
    }

}
