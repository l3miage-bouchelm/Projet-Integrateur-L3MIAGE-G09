import { Component, OnInit } from '@angular/core';
import { SharedService } from '../../services/shared.service';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-infos-tournee-livreurs',
  standalone: true,
  imports: [CommonModule,HttpClientModule],
  templateUrl: './infos-tournee-livreurs.component.html',
  styleUrl: './infos-tournee-livreurs.component.scss'
})
export class InfosTourneeLivreursComponent implements OnInit{



  ngOnInit(): void {
    this.tousTournee=this.sharedService.getTournee();
    this.tousTournee.forEach(t=>{
      let liv=t.liveurs.find(tt=>tt.trigramme==this.sharedService.getEmploye().trigramme)
      if(liv){
        this.tournees.push(t);
      }
    })
    console.log(this.tournees.length)
    this.tournees.forEach(t=>{
      t.livraison.forEach(l=>{
<<<<<<< HEAD
        this.location.push([l.client.position.longitude,l.client.position.latitude])
      })
      this.location.push([t.entrepot.position.longitude,t.entrepot.position.latitude])
=======
        this.location.push([parseFloat(l.client.longitude),parseFloat(l.client.latitude)])
      })
      this.location.push([parseFloat(t.entrepot.longitude),parseFloat(t.entrepot.latitude)])
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191

    })
    
    console.log(this.location)
  }


  constructor(private sharedService:SharedService,private http:HttpClient){}

  tournees:Tournee[]=[];
  tousTournee:Tournee[]=[];
  location:number[][]=[]
  m:Matrice={k:0,matrix:[],start:0}
  trajet:Trajets[]=[]

  tostring(){
    let body='{"locations":[';
    if(this.location){
      for(let i=0;i<this.location.length;i++){
        body+='['+this.location[i][0]+','+this.location[i][1]+']'
        if(i!=this.location.length-1){
          body+=','
        }
      }
      body+='],"metrics":["distance"]}';
    }
    console.log(body)
    return body;
  }

  matrice(tournee:Tournee){
    let request = new XMLHttpRequest();
    let matrix;


    request.open('POST', "https://api.openrouteservice.org/v2/matrix/driving-car");

    request.setRequestHeader('Accept', 'application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8');
    request.setRequestHeader('Content-Type', 'application/json');
    request.setRequestHeader('Authorization', '5b3ce3597851110001cf6248366ddf83a2ee4d23ba9481ec85060854');

    request.onreadystatechange = function () {
      if (this.readyState === 4) {
        console.log('Status:', this.status);
        console.log('Headers:', this.getAllResponseHeaders());
        console.log('Body:', this.responseText);
        const jsonObject = JSON.parse(request.responseText);
        const distances = jsonObject.distances;
        matrix = jsonObject.distances;
        handleMatrix(matrix);
      }
    };

    const body = this.tostring();
    request.send(body);
    const handleMatrix = (matrix:number[][]) => {
      if (matrix !== undefined) {
        for(let i=0;i<matrix.length;i++){
          for(let j=i;j<matrix[0].length;j++){
            matrix[j][i]=matrix[i][j];
          }
        }
          //console.log('matrix 已被赋值:', matrix);
          if(tournee){
            this.m.k=tournee.liveurs.length;
          }
          this.m.matrix=matrix;
          this.m.start=matrix.length;
          console.log('matrix 已被赋值:', this.m);
          this.http.post<Trajets>('http://localhost:4201/planner/planif', this.m)
              .subscribe(
                  (response) => {
                      console.log('Post request successful:', response);
                      this.trajet.push(response);
                  },
                  (error) => {
                      console.error('Error in post request:', error);
                  }
              );
      } else {
          console.log('matrix 尚未被赋值');
      }
  };
  }
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
<<<<<<< HEAD
  adresse: Adresse,
  position: Position,
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
interface Commandes{
  commande:Commande;
  disabled:boolean;
  tourneeID:number;
}

interface Matrice{
  k:number;
  matrix:number[][];
  start:number;
}

interface Trajets{
  trajets:number[][],
  longTrajets:number[]
}