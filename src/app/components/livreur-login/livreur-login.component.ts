import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-livreur-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './livreur-login.component.html',
  styleUrl: './livreur-login.component.scss'
})
export class LivreurLoginComponent {
  constructor(private router: Router) {}
  nom: string = '';

  login() {
    
    this.router.navigate(['/livreur-travail'],{ queryParams: { nom: this.nom } });
  }
}
