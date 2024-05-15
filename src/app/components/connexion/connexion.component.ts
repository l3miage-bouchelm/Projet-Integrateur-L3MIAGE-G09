import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ConnexionService } from '../../services/connexion.service';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
<<<<<<< HEAD
=======
import { SharedService } from '../../services/shared.service';
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191

@Component({
  selector: 'app-connexion',
  standalone: true,
  templateUrl: './connexion.component.html',
  styleUrl: './connexion.component.scss',
<<<<<<< HEAD
  providers:[ConnexionService, CommonModule],
=======
  providers:[ConnexionService, CommonModule, SharedService],
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports :[RouterModule,RouterOutlet, FormsModule]
})
export class ConnexionComponent {
<<<<<<< HEAD
    constructor(private cox: ConnexionService, private router : Router){
=======
    constructor(private cox: ConnexionService, private router : Router, private sharedService : SharedService){
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
    }
    nom: string = '';
    trigramme : string ='';
  
    login() {
    localStorage.setItem('livreurNom', this.nom);
    localStorage.setItem('livreurTrigramme', this.trigramme);
    this.router.navigate(['/livreur-travail']);
<<<<<<< HEAD
=======

>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
    }

 
    async logGoogle():Promise<void>{
        this.cox.loginGoogle();
    }
    async logOut():Promise<void>{
        this.cox.logout();
    }
  }