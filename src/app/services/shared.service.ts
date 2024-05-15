import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
<<<<<<< HEAD
  private isLoggedIn: boolean = false;
=======
  public isLoggedIn: boolean = false;
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
  private employe:Livreur={trigramme:'',prenom:'',nom:'',photo:'',telephone:'',emploi:'',entrepot:'',tournees:''}
  private tournee:Tournee[]=[];
  private commandeOuvert:Commande[]=[];
  private commandePrevu:Commandes[]=[];
  private locations:Locations[]=[];
  private today:number[]=[]
  private tomorrow:number[]=[]
  private livreurs:Livreur[]=[]

  
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

  getLocations(){
    return this.locations;
  }

  addLocation(l:Locations){
    this.locations.push(l);
  }

  removeLocation(i:number){
    let loc=this.locations.find(lo=>lo.idTournee==i);
    if(loc){
      let index=this.locations.indexOf(loc);
      this.locations.splice(index,1);
    }
  }

  changeLocation(l:Locations,i:number){
    let loc=this.locations.find(lo=>lo.idTournee==i);
    if(loc){
      let index=this.locations.indexOf(loc);
      this.locations[index]=l;
    }else{
      this.addLocation(l);
    }
  }

  getToday(){
    return this.today
  }

  getTomorrow(){
    return this.tomorrow
  }

  setLivreurs(liv:Livreur[]){
    this.livreurs=liv;
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
  entrepot:Entrepot,
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

interface Commande {
  reference: string;
  etat: string;
  dateDeCreation: string;
  note: string;
  commantaire: string;
<<<<<<< HEAD
  client: Client;
=======
  client: string;
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
  ligne: string;
}

interface Commandes{
  commande:Commande;
  disabled:boolean;
  tourneeID:number;
}

interface Locations{
  idTournee:number,
  location:number[][]
}

interface Client{
  email:string,
  prenom:string,
  nom:string,
<<<<<<< HEAD
  //adresse:string,
  //codePostal:string,
  //ville:string,
  adresse: Adresse;
  //latitude:string,
  //longitude:string,
  position: Position;
  commandes:string
}

interface Position{
  latitude:number,
  longitude:number
}
interface Adresse {
  adresse: string;
  codePostal: string;
  ville: string;
}

=======
  adresse:string,
  codePostal:string,
  ville:string,
  latitude:string,
  longitude:string,
  commandes:string
}

>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
interface Entrepot{
  name:string;
  lettre:string;
  photo:string;
<<<<<<< HEAD
  adresse: Adresse,
  position: Position,
=======
  adresse:string;
  codePostal:string;
  ville:string;
  latitude:string;
  longitude:string;
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
}