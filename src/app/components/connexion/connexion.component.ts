import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ConnexionService } from '../../services/connexion.service';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedService } from '../../services/shared.service';

@Component({
  selector: 'app-connexion',
  standalone: true,
  templateUrl: './connexion.component.html',
  styleUrl: './connexion.component.scss',
  providers:[ConnexionService, CommonModule, SharedService],
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports :[RouterModule,RouterOutlet, FormsModule]
})
export class ConnexionComponent {
    constructor(private cox: ConnexionService, private router : Router, private sharedService : SharedService){
    }
    nom: string = '';
    trigramme : string ='';
  
    login() {
    localStorage.setItem('livreurNom', this.nom);
    localStorage.setItem('livreurTrigramme', this.trigramme);
    this.router.navigate(['/livreur-travail']);
    }

 
    async logGoogle():Promise<void>{
        this.cox.loginGoogle();
    }
    async logOut():Promise<void>{
        this.cox.logout();
    }
  }