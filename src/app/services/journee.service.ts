import { HttpClient } from '@angular/common/http';
import { Injectable, QueryList, ViewChildren, WritableSignal, computed, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JourneeService {
  commandes:Commande[]=[];
  livreurs:Livreur[]=[];
  camions:Camion[]=[];
  entrepots:Entrepot[]=[];
  commandeOuvert:Commande[]=[];
  commandePrevu:Commandes[]=[];
  tournees:Tournee[]=[];
  entrepotChoisi:string='';
  commandeSelectionnee:Commande | null = null; 
  


  constructor( private http: HttpClient) { }

 

  

  

    
  
  getCsvData(){
    this.http.get('./assets/Export_Commandes.csv',{responseType:'text'})
    .subscribe(data=>{
      this.commandes=this.lireCommande(data);
    },
  );
    this.commandeOuvert=this.commandes.filter(commande => commande.etat === "ouverte");
      }

  lireCommande(csvData:string){
    const lines=csvData.split('\n');
    const result:Commande[]= [];

    const headers=lines[0].split(',');
    for(let i=1;i<lines.length;i++){
      const obj: ParsedData = {};
      const currentLine=lines[i].split(',');
      for(let j=0;j<headers.length;j++){
        obj[headers[j]]=currentLine[j];
      }
      const commande:Commande={
        reference:obj[headers[0]],
        etat:obj[headers[1]],
        dateDeCreation:obj[headers[2]],
        note:obj[headers[3]],
        commentaire:obj[headers[4]],
        client:obj[headers[5]],
        ligne:obj[headers[6]],
      }
      result.push(commande);
    }
    return result;
  }

  lireLivreur(csvData:string){
    const lines=csvData.split('\n');
    const result:Livreur[]= [];

    const headers=lines[0].split(',');
    for(let i=1;i<lines.length;i++){
      const obj: ParsedData = {};
      const currentLine=lines[i].split(',');
      for(let j=0;j<headers.length;j++){
        obj[headers[j]]=currentLine[j];
      }
      const livreur:Livreur={
        trigramme:obj[headers[0]],
        prenom:obj[headers[1]],
        nom:obj[headers[2]],
        photo:obj[headers[3]],
        telephone:obj[headers[4]],
        emploi:obj[headers[5]],
        entrepot:obj[headers[6]],
        tournees:obj[headers[7]],
      }
      if(livreur.emploi=='livreur'){
        result.push(livreur);
      }     
    }
    return result;
  }

  lireCamion(csvData:string){
    const lines=csvData.split('\n');
    const result:Camion[]= [];

    const headers=lines[0].split(',');
    for(let i=1;i<lines.length;i++){
      const obj: ParsedData = {};
      const currentLine=lines[i].split(',');
      for(let j=0;j<headers.length;j++){
        obj[headers[j]]=currentLine[j];
      }
      const camion:Camion={
        immatriculation:obj[headers[0]],
        latitude:obj[headers[1]],
        longitude:obj[headers[2]],
        entrepot:obj[headers[3]],
      }
      result.push(camion);    
    }
    return result;
  }

  lireEntrepot(csvData:string){
    const lines=csvData.split('\n');
    const result:Entrepot[]= [];

    const headers=lines[0].split(',');
    for(let i=1;i<lines.length;i++){
      const obj: ParsedData = {};
      const currentLine=lines[i].split(',');
      for(let j=0;j<headers.length;j++){
        obj[headers[j]]=currentLine[j];
      }
      const entrepot:Entrepot={
        nom:obj[headers[0]],
        lettre:obj[headers[1]],
        photo:obj[headers[2]],
        adresse:obj[headers[3]],
        codePostal:obj[headers[4]],
        ville:obj[headers[5]],
        latitude:obj[headers[6]],
        longitude:obj[headers[7]],
        camions:obj[headers[8]],
        employés:obj[headers[9]],
      }
      result.push(entrepot);   
    }
    return result;
  }

  lireInfos(){
    this.http.get('./assets/Export_Employés.csv',{responseType:'text'})
    .subscribe(data=>{
      this.livreurs=this.lireLivreur(data);
    },
  );
  this.http.get('./assets/Export_Camions.csv',{responseType:'text'})
    .subscribe(data=>{
      this.camions=this.lireCamion(data);
    },
  );
  this.http.get('./assets/Export_Entrepôts.csv',{responseType:'text'})
    .subscribe(data=>{
      this.entrepots=this.lireEntrepot(data);
    },
  );
  }

  
  
  selectionnerCommande(event: Event) {
    const selectedIndex = (event.target as HTMLSelectElement).selectedIndex;
    const selectedOption = (event.target as HTMLSelectElement).options[selectedIndex];
    const numeroCommande = selectedOption.value;
  
    if (numeroCommande) {
      const commandeTrouvee = this.commandes.find(commande => commande.reference === numeroCommande);
      this.commandeSelectionnee = commandeTrouvee ? commandeTrouvee : null;
    }
  }
  
  
  
  addTournee() {
    this.tournees.push({id:this.tournees.length+1,commandes:[]});
  }

  removeTournee(index: number) {
    this.tournees.splice(index, 1);
  }

  toggleSelectionCommande(item: Commandes,itemSelected :Commande[],i:number) {
    if (itemSelected.indexOf(item.commande)===-1) {
      itemSelected.push(item.commande);
      item.disabled=!item.disabled;
      item.tourneeID=i;
    } else {
      const index = itemSelected.indexOf(item.commande);
      if (index !== -1) {
        itemSelected.splice(index, 1);
        item.disabled=!item.disabled;
        item.tourneeID=0;
      }
    }
  }

}




interface Commande {
  reference: string;
  etat: string;
  dateDeCreation: string;
  note: string;
  commentaire: string;
  client: string;
  ligne: string;
}

interface ParsedData {
  [key: string]: string;
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

interface Tournee {
  id:number;
  commandes:Commande[];
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

interface Commandes{
  commande:Commande;
  disabled:boolean;
  tourneeID:number;
}

