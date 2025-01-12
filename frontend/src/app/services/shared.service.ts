import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  constructor() { }
  confirmDialogSubject = new Subject<any>();

  confirmDialog(options: any) {
    this.confirmDialogSubject.next(options);
  }
}
