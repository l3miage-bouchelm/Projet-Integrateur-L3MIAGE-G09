import { Component, OnInit } from '@angular/core';
import { SharedService } from '../../services/shared.service';

@Component({
  selector: 'app-infos-tournee-livreurs',
  standalone: true,
  imports: [],
  templateUrl: './infos-tournee-livreurs.component.html',
  styleUrl: './infos-tournee-livreurs.component.scss'
})
export class InfosTourneeLivreursComponent implements OnInit{



  ngOnInit(): void {
    this.tournees=this.sharedService.getTournee();
  }


  constructor(private sharedService:SharedService){}

  tournees:Tournee[]=[]
}


interface Tournee{
  id:number,
  journee:string,
  date:Date,
  entrepot:string,
  camion:string,
  liveurs:Livreur[],
  livraison:Livraison[],
  commandes:Commande[]
}

interface Livraison{
  id:number,
  client:Client,
  commandes:Commande[]
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
  name:string;
  lettre:string;
  photo:string;
  adresse:string;
  codePostal:string;
  ville:string;
  latitude:string;
  longitude:string;
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

interface Matrice{
  k:number;
  matrix:number[][];
  start:number;
}

interface Client{
  email:string,
  prenom:string,
  nom:string,
  adresse:string,
  codePostal:string,
  ville:string,
  latitude:string,
  longitude:string,
  commandes:string
}

interface Commandes{
  commande:Commande;
  disabled:boolean;
  tourneeID:number;
}