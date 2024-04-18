import { Component, Signal } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { Auth, User, authState, signOut, signInWithPopup, GoogleAuthProvider } from '@angular/fire/auth';
import { Signal, Observable } from 'rxjs';
import { map } from 'rxjs/operators';


import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet, RouterModule,
    MatToolbarModule, MatListModule,
    MatSidenavModule, MatIconModule, MatButtonModule, CommonModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  constructor(private fbAuth: Auth) { } 
  readonly obsUser: Observable<User | null> = authState(this.fbAuth) 
  //readonly userSignal : new Signal<boolean>();
  //readonly userSignal = this.obsUser.pipe(map(user => !!user))
  async logout(): Promise<void> { 

       return signOut(this.fbAuth); 
    } 
    async loginGoogle(): Promise<void> { 
    
        return signInWithPopup(this.fbAuth, new GoogleAuthProvider()).then( 
    
        console.log, 
    
        console.error 
    
      ) 
    
      } 
}





