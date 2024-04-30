import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private isLoggedIn: boolean = false;
  
  setData(data: boolean) {
    this.isLoggedIn = data;
  }

  getData() {
    return this.isLoggedIn;
  }
}
