import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LivreurTravailComponent } from '../livreur-travail/livreur-travail.component';

@Component({
  selector: 'app-livreur-login',
  standalone: true,
  imports: [FormsModule,LivreurTravailComponent],
  templateUrl: './livreur-login.component.html',
  styleUrl: './livreur-login.component.scss'
})
export class LivreurLoginComponent {
  constructor(private router: Router) {}
  nom: string = '';

  login() {
  localStorage.setItem('livreurNom', this.nom);
  this.router.navigate(['/livreur-travail']);
  }
}
