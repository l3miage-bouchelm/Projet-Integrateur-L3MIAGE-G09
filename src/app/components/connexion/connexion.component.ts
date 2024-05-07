import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ConnexionService } from '../../services/connexion.service';

@Component({
  selector: 'app-connexion',
  standalone: true,
  imports: [
    CommonModule,
  ],
  templateUrl: './connexion.component.html',
  styleUrl: './connexion.component.scss',
  providers:[ConnexionService],
  changeDetection: ChangeDetectionStrategy.OnPush,
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
    