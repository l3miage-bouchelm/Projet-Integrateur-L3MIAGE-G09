import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { getAuth, provideAuth } from '@angular/fire/auth';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(), importProvidersFrom(provideFirebaseApp(() => initializeApp({"projectId":"projetintegrateur9","appId":"1:92324758253:web:5c21ba427087c105cc42e4","storageBucket":"projetintegrateur9.appspot.com","apiKey":"AIzaSyD-cMegyM3cA_cUPGE8RBP9L0mfhOOyEgs","authDomain":"projetintegrateur9.firebaseapp.com","messagingSenderId":"92324758253","measurementId":"G-YW0Q9NHHWT"}))), importProvidersFrom(provideAuth(() => getAuth())),
  ],
};
