import { Component, signal } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-creation-compte',
  standalone: true,
  imports: [HttpClientModule, FormsModule],
  templateUrl: './creation-compte.component.html',
  styleUrl: './creation-compte.component.scss'
})
export class CreationCompteComponent {
    constructor(private http : HttpClient){}
    readonly livreur = signal<Livreur>({
      trigramme: '',
      prenom: '',
      nom: '',
      photo: '',
      telephone: '',
      emploi: '',
      entrepot: '',
      tournees: ''
    }) ;

    selectedEmploi: string = '';

    empOptions = ['Emploi', 'Planificateur', 'Livreur'];

    onEmploiChange() {
      this.livreur().emploi = this.selectedEmploi;
    }

    creerCompte(){
      const  livreurData: Livreur = {
        trigramme : this.livreur().trigramme,
        nom: this.livreur().nom,
        prenom: this.livreur().prenom,
        photo : this.livreur().photo,
        telephone : this.livreur().telephone,
        emploi : 'Livreur',
        entrepot : 'Grenis',
        tournees:''
      };
      this.http.post<any>('http://localhost:8080/employes', livreurData).subscribe(
        (response) => {
          console.log('Données envoyées avec succès:', response);
        },
        (error) => {
          console.error('Erreur lors de l\'envoi des données:', error);
        }
      );
  
    }
  }


interface Livreur {
  trigramme: string;
  prenom: string;
  nom: string;
  photo: string;
  telephone: string;
  emploi: string;
  entrepot: string;
  tournees: string;
}