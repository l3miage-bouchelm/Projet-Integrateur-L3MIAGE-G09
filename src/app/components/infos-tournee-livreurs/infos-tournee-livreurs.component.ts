import { Component, OnInit } from '@angular/core';
import { SharedService } from '../../services/shared.service';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

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


    console.log(this.location)
  }


  constructor(private sharedService:SharedService,private http:HttpClient,private router: Router){}

  tournees:Tournee[]=[];
  tousTournee:Tournee[]=[];
  location:number[][]=[]
  m:Matrice={k:0,matrix:[],start:0}
  trajet:Trajets|undefined

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

  setLocation(index: number): Promise<void> {
    return new Promise((resolve) => {
      this.location = [];
      this.tournees[index].livraison.forEach(l => {
        this.location.push([l.client.position.longitude,l.client.position.latitude]);
      });
      this.location.push([this.tournees[index].entrepot.position.longitude,this.tournees[index].entrepot.position.latitude]);
      resolve();
    });
  }

  getnum(index:number){
    let i=this.tournees[index].liveurs.find(l=>l.trigramme==this.sharedService.getEmploye().trigramme)
    if(i){
      console.log(this.tournees[index].liveurs.indexOf(i))
      return this.tournees[index].liveurs.indexOf(i)
    }
    return -1
  }

  async voirinfos(index: number) {
    try {
      await this.setLocation(index);
      await this.matrice(this.tournees[index]);
      this.router.navigate(['/leaflet'], { queryParams: { location: JSON.stringify(this.location), trajets: JSON.stringify(this.trajet) } });
    } catch (error) {
      console.error('Error in voirinfos:', error);
    }
  }

  matrice(tournee: Tournee): Promise<void> {
    return new Promise((resolve, reject) => {
      let request = new XMLHttpRequest();
      let matrix;

      request.open('POST', "https://api.openrouteservice.org/v2/matrix/driving-car");

      request.setRequestHeader('Accept', 'application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8');
      request.setRequestHeader('Content-Type', 'application/json');
      request.setRequestHeader('Authorization', '5b3ce3597851110001cf6248366ddf83a2ee4d23ba9481ec85060854');

      request.onreadystatechange = () => {
        if (request.readyState === 4) {
          if (request.status === 200) {
            const jsonObject = JSON.parse(request.responseText);
            const distances = jsonObject.distances;
            matrix = jsonObject.distances;
            handleMatrix(matrix);
          } else {
            reject(new Error(`Request failed with status ${request.status}`));
          }
        }
      };

      const body = this.tostring();
      request.send(body);

      const handleMatrix = (matrix: number[][]) => {
        if (matrix !== undefined) {
          for (let i = 0; i < matrix.length; i++) {
            for (let j = i; j < matrix[0].length; j++) {
              matrix[j][i] = matrix[i][j];
            }
          }
          if (tournee) {
            this.m.k = tournee.liveurs.length;
          }
          this.m.matrix = matrix;
          this.m.start = matrix.length - 1;
          this.http.post<Trajets>('http://130.190.78.146:4201/planner/planif', this.m)
            .subscribe(
              (response) => {
                this.trajet = response;
                resolve();
              },
              (error) => {
                reject(error);
              }
            );
        } else {
          reject(new Error('Matrix is undefined'));
        }
      };
    });
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
  adresse: Adresse,
  position: Position,
}

interface Commande {
  reference: string;
  etat: string;
  dateDeCreation: string;
  note: string;
  commantaire: string;
  client: Client;
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