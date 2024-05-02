import { CommonModule } from '@angular/common';
import { Component,Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tournee',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './tournee.component.html',
  styleUrl: './tournee.component.scss'
})
export class TourneeComponent {
  @Input()
  livreurs:Livreur[]=[]
  @Input()
  camions:Camion[]=[]
  @Input()
  entrepots:Entrepot[]=[]
  @Input()
  commandes:Commande[]=[]

  constructor() { }
  entrepotChoisi:string='default';
  camionsChoisi:Camion[]=[];
  livreursChoisi:Livreur[]=[];



  toggleSelection(item: any,itemSelected :any[]) {
    if (itemSelected.indexOf(item)===-1) {
      itemSelected.push(item);
    } else {
      const index = itemSelected.indexOf(item);
      if (index !== -1) {
        itemSelected.splice(index, 1);
      }
    }
  }

  toggleSelectionCommande(item: Commandes,itemSelected :Commande[]) {
    if (itemSelected.indexOf(item.commande)===-1) {
      itemSelected.push(item.commande);
      item.disabled=!item.disabled;
    } else {
      const index = itemSelected.indexOf(item.commande);
      if (index !== -1) {
        itemSelected.splice(index, 1);
        item.disabled=!item.disabled;
      }
    }
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

interface Camion{
  immatriculation:string;
  latitude:string;
  longitude:string;
  entrepot:string;
}

interface Entrepot{
  nom:string;
  lettre:string;
  photo:string;
  adresse:string;
  codePostal:string;
  ville:string;
  latitude:string;
  longitude:string;
  camions:string;
  employés:string;
}

interface Commande {
  reference: string;
  etat: string;
  dateDeCreation: string;
  note: string;
  commantaire: string;
  client: string;
  ligne: string;
}

interface Commandes{
  commande:Commande;
  disabled:boolean;
}