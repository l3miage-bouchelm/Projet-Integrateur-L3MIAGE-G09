import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from '../../services/shared.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-infos-tournee',
  standalone: true,
  imports: [FormsModule,CommonModule,HttpClientModule],
  templateUrl: './infos-tournee.component.html',
  styleUrl: './infos-tournee.component.scss'
})
export class InfosTourneeComponent  implements OnInit{
  constructor(private route: ActivatedRoute,private sharedService:SharedService,private router: Router,private http:HttpClient) { }

  tournee:Tournee|undefined
  location:number[][]|undefined
  commandePrevu:Commandes[]|undefined
  tourneeTrans:number=-1;
  livraisonTrans:number=-1;
  tousTournee:Tournee[]|undefined
  m:Matrice={k:0,matrix:[],start:0}
  trajets:Trajets|undefined;
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const tourneeString = params['tournee'];
      const locationString=params['location'];
      const trajetsString=params['trajets'];
      this.tournee = JSON.parse(tourneeString); // 将字符串转换为对象
      this.location=JSON.parse(locationString);
      this.trajets=JSON.parse(trajetsString);
      console.log('Received :', trajetsString);
    });
    this.commandePrevu=this.sharedService.getCommandePrevu();
    this.tousTournee=this.sharedService.getTournee();
  }

  print(){
    console.log('Received tournee:', this.location);
    console.log(this.sharedService.getTournee())
  }

  voirCarte(){
    console.log('Received :', this.trajets);
    this.router.navigate(['/leaflet'], { queryParams: {location:JSON.stringify(this.location),trajets:JSON.stringify(this.trajets)} });
  }

  supprimerLivraison(livraison:Livraison){
    if(this.tournee){
      if(this.tournee.livraison.indexOf(livraison)!==-1){
        const index=this.tournee.livraison.indexOf(livraison);
        let liv:Livraison=this.tournee.livraison[index];
        this.tournee.livraison.splice(index,1);
        this.location?.splice(liv.id-1,1);
        let l=this.tournee;
        ///this.sharedService.removeTournee(this.tournee)
        for(let i=0;i<liv.commandes.length;i++){
          for(let j=0;j<this.tournee.commandes.length;j++){
            if(liv.commandes[i].reference===this.tournee.commandes[j].reference){
              this.tournee.commandes.splice(j,1);
            }
          }
          if(this.commandePrevu){
            for(let k=0;k<this.commandePrevu?.length;k++){
              if(this.commandePrevu[k].commande.reference===liv.commandes[i].reference){
                this.commandePrevu[k].tourneeID=-1;
                this.commandePrevu[k].disabled=false;
              }
            }
          }
        }
        console.log(this.tournee)
        this.sharedService.changeTournee(l,this.tournee)
      }
    }
    this.matrice();
  }

  modifierLivraison(tourneeTrans:number,livraisonTrans:number){
    if(this.tournee&&this.tousTournee){
      let liv=this.tournee.livraison.find(l=>l.id==livraisonTrans);
      let tou=this.tousTournee.find(d=>d.id==tourneeTrans);
      console.log(liv,tou)
      if(liv&&tou){
        this.supprimerLivraison(liv);
        this.ajouterLivraison(tou,liv);
        //console.log('nmsl')
      }
    }
  }

  ajouterLivraison(tourneeTrans:Tournee,livraisonTrans:Livraison){
    let tou=tourneeTrans;
    let liv=tourneeTrans.livraison.find(l=>l.client==livraisonTrans.client)
    if(liv){
      let i=tourneeTrans.livraison.indexOf(liv);
      livraisonTrans.commandes.forEach(c=>{
        tourneeTrans.livraison[i].commandes.push(c)
      })
    }else{
      tou.livraison.push(livraisonTrans);
    }
    for(let i=0;i<livraisonTrans.commandes.length;i++){
        if(this.commandePrevu){
          for(let k=0;k<this.commandePrevu?.length;k++){
            if(this.commandePrevu[k].commande.reference===livraisonTrans.commandes[i].reference){
              this.commandePrevu[k].tourneeID=tou.id;
              this.commandePrevu[k].disabled=true;
            }
          }
        }
    }

    livraisonTrans.commandes.forEach(l=>{
      tou.commandes.push(l)
    })
    this.sharedService.changeTournee(tourneeTrans,tou)
  }

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

  matrice(){
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
          if(this.tournee){
            this.m.k=this.tournee?.liveurs.length;
          }
          this.m.matrix=matrix;
          this.m.start=matrix.length;
          console.log('matrix 已被赋值:', this.m);
          this.http.post<Trajets>('http://localhost:4201/planner/planif', this.m)
              .subscribe(
                  (response) => {
                      console.log('Post request successful:', response);
                      this.trajets=response;
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