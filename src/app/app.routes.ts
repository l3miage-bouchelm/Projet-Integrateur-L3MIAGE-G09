import { Routes } from '@angular/router';
import { CartoComponent } from './components/carto/carto.component';
import { ListsComponent } from './components/lists/lists.component';
import { PresentationComponent } from './components/presentation/presentation.component';
import { LivreurLoginComponent } from './components/livreur-login/livreur-login.component';
import { LivreurTravailComponent } from './components/livreur-travail/livreur-travail.component';
import { JourneeComponent } from './components/journee/journee.component';
import { InfosTourneeComponent } from './components/infos-tournee/infos-tournee.component';


export const routes: Routes = [
    { path: 'presentation', title: "Présentation", component: PresentationComponent },
    { path: 'leaflet', title: "Cartographie avec les leaflet", component: CartoComponent },
    { path: 'lists', title: "Listes", component: ListsComponent },
    { path: 'livreur-login', title:"Login des livreurs", component: LivreurLoginComponent},
    { path: 'livreur-travail', title:"Travail des livreurs", component: LivreurTravailComponent},
    { path: 'journee', title:"Journee", component:JourneeComponent},
    { path: 'infos-tournee', title:"infos d'unes tournee", component:InfosTourneeComponent},
    { path: '**', redirectTo: '/livreur-login' }
];