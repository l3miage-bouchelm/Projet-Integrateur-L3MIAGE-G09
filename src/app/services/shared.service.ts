import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private isLoggedIn: boolean = false;
  private employe:Livreur={trigramme:'',prenom:'',nom:'',photo:'',telephone:'',emploi:'',entrepot:'',tournees:''}
  private tournee:Tournee[]=[];
  private commandeOuvert:Commande[]=[];
  private commandePrevu:Commandes[]=[];

  
  setData(data: boolean) {
    this.isLoggedIn = data;
  }

  getData() {
    return this.isLoggedIn;
  }

  setEmploye(data:Livreur){
    this.employe=data;
  }

  getEmploye() {
    return this.employe;
  }

  addTournee(data:Tournee){
    let index = this.tournee.findIndex(t => t.id === data.id);
    if (index !== -1) {
      this.tournee[index]=data;
    }else{
      this.tournee.push(data);
    }
  }

  removeTournee(data:Tournee){
    let index = this.tournee.findIndex(t => t.id === data.id);
    if (index !== -1) {
      this.tournee.splice(index, 1);
    } 
  }

  changeTournee(oldData:Tournee,newData:Tournee){
    let index = this.tournee.findIndex(t => t.id === oldData.id);
    if (index !== -1) {
      this.tournee[index]=newData;
    }else{
      this.addTournee(newData)
    }
  }

  getTournee(){
    return this.tournee;
  }

  getCommandeOuvert(){
    return this.commandeOuvert
  }

  getCommandePrevu(){
    return this.commandePrevu
  }

  setCommandeOuvert(c:Commande[]){
    this.commandeOuvert=c
  }

  setCommandePrevu(c:Commandes[]){
    this.commandePrevu=c
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
  client:string,
  commandes:Commande[]
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
  tourneeID:number;
}