import { Component, Signal, computed, signal } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { Auth, User, authState, signOut, signInWithPopup, GoogleAuthProvider } from '@angular/fire/auth';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';


import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { LivreurLoginComponent } from "./components/livreur-login/livreur-login.component";
import { PresentationComponent } from "./components/presentation/presentation.component";
import { LivreurTravailComponent } from './components/livreur-travail/livreur-travail.component';
import { SharedService } from './services/shared.service';

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
    imports: [
        RouterOutlet, RouterModule,
        MatToolbarModule, MatListModule,
        MatSidenavModule, MatIconModule, MatButtonModule, CommonModule,
        LivreurLoginComponent,
        PresentationComponent
    ]
})
export class AppComponent {
  constructor(private fbAuth: Auth,private sharedService: SharedService ) { } 
  
  readonly obsUser: Observable<User | null> = authState(this.fbAuth) 
  readonly isLoggedIn=computed<boolean>(()=>this.sharedService.getData());
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





