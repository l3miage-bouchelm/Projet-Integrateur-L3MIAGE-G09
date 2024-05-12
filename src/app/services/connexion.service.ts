import { Injectable } from '@angular/core';
import { Auth, User, authState, signOut, signInWithPopup, GoogleAuthProvider } from '@angular/fire/auth';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConnexionService {

    constructor(private fbAuth: Auth) { } 
    readonly obsUser: Observable<User | null> = authState(this.fbAuth) 
    //readonly userSignal= Signal<boolean>();
    //readonly userSignal = this.obsUser.pipe(Map(this.user => !!user))
    async logout(): Promise<void> { 
     return signOut(this.fbAuth); 
      
        } 
    async loginGoogle(): Promise<void> { 
      
          return signInWithPopup(this.fbAuth, new GoogleAuthProvider()).then( 
            (result) => {
              const credential = GoogleAuthProvider.credentialFromResult(result);
             let token: string | undefined;
  
             if (credential) {
              token = credential.accessToken;
              } else { 
                 console.error("La credential est null.");
  }
              const user = result.user;
            }).catch((error) => {
              const errorCode = error.code;
              const errorMessage = error.message;
              const email = error.customData.email;
              const credential = GoogleAuthProvider.credentialFromError(error);
            }
      
        ) 
      
        } 
  }
  


