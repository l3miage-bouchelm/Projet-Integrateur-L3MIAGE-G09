import { Routes } from '@angular/router';
import { CartoComponent } from './components/carto/carto.component';
import { ListsComponent } from './components/lists/lists.component';
import { PresentationComponent } from './components/presentation/presentation.component';
import { LivreurLoginComponent } from './components/livreur-login/livreur-login.component';
//import { LivreurTravailComponent } from '.components/livreur-travail/livreur-travail.component.html';
import { JourneeComponent } from './components/journee/journee.component'; 
import { ConnexionComponent } from './components/connexion/connexion.component';

export const routes: Routes = [
    { path: 'presentation', title: "Présentation", component: PresentationComponent },
    { path: 'leaflet', title: "Cartographie avec les leaflet", component: CartoComponent },
    { path: 'lists', title: "Listes", component: ListsComponent },
    { path: 'livreur-login', title:"Login des livreurs", component: LivreurLoginComponent},
   // { path: 'livreur-travail', title:"Travail des livreurs", component: LivreurTravailComponent},
    { path: 'journee', title:"Journee", component:JourneeComponent},
    { path: 'connexion', title:"Connexion", component: ConnexionComponent},
    { path: '**', redirectTo: '/connexion' }
];