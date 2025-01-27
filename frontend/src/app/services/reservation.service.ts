import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(private http:HttpClient) { }


  
  getAllReservations(page: number, size: number):Observable<any> {
    const url= environment.AllBooksReservationsApi
   
    const params= new HttpParams()
          .set('page', page.toString())
          .set('size', size.toString());

    return this.http.get(url, {params});      
  }
  

  changeResrvationStatus(reservationId:Number):Observable<any>{
    const body={"returned":true}
    return this.http.patch<any>(environment.ChangeReservationStatusApi+reservationId,body);
  }


  getUserReservations(page: number, size: number):Observable<any>{
    const url= environment.UserReservationsApi
    const params= new HttpParams()
          .set('page', page.toString())
          .set('size', size.toString());

    return this.http.get(url, {params});
  }

}
