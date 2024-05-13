import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ConnexionService } from '../../services/connexion.service';
import { Router, RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-connexion',
  standalone: true,
  templateUrl: './connexion.component.html',
  styleUrl: './connexion.component.scss',
  providers:[ConnexionService, CommonModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports :[RouterModule,RouterOutlet]
})
export class ConnexionComponent {
    constructor(private cox: ConnexionService){
    }
   

 
    async logGoogle():Promise<void>{
        this.cox.loginGoogle();
    }
    async logOut():Promise<void>{
        this.cox.logout();
    }
  }