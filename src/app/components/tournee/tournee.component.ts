import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component,Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SharedService } from '../../services/shared.service';

@Component({
  selector: 'app-tournee',
  standalone: true,
  imports: [CommonModule,FormsModule,HttpClientModule],
  templateUrl: './tournee.component.html',
  styleUrl: './tournee.component.scss'
})
export class TourneeComponent implements OnInit{
  @Input()
  livreurs:Livreur[]=[]
  @Input()
  camions:Camion[]=[]
  @Input()
  entrepots:Entrepot[]=[]
  @Input()
  commandes:Commande[]=[]
  @Input()
  clients:Client[]=[]
  @Input()
  id:number=-1;
  @Input() name: string = '';

  constructor(private http:HttpClient,private router: Router,private sharedService:SharedService) { }
  tournee:Tournee={id:-1,journee:'',date:new Date(),entrepot:'',camion:'',liveurs:[],livraison:[],commandes:[]}
  entrepotChoisi:string='';
  camionsChoisi:string='';
  livreursChoisi:Livreur[]=[];
  martix:number[][]=[];
  location:number[][]=[];
  m:Matrice={k:0,matrix:[],start:0}
  ngOnInitCompleted: boolean = false;


  ngOnInit(): void {
    this.loadTournee();
    this.ngOnInitCompleted = true;
  }

  private loadTournee(): void {
    if (this.id !== -1) {
      this.entrepotChoisi=this.sharedService.getEmploye().entrepot;
      this.tournee = this.sharedService.getTournee()[this.id];
      this.camionsChoisi=this.tournee.camion;
      this.livreursChoisi=this.tournee.liveurs;
      this.camions=this.camions.filter(camion=>camion.entrepot===this.entrepotChoisi)
    }
  }

  toggleSelectionLivreur(item: Livreur,itemSelected :Livreur[]) {
    let liv=itemSelected.find(i=>i.nom===item.nom&&i.prenom===item.prenom);
    if(liv){
      const index = itemSelected.indexOf(liv);
      console.log(index)
      if (index !== -1) {
        itemSelected.splice(index, 1);
      }
    }else{
      itemSelected.push(item);
    }
  }

  ischeked(livreur:Livreur){
    let liv=this.livreursChoisi.find(i=>i.nom===livreur.nom&&i.prenom===livreur.prenom);
    if(liv){
      return true
    }else{
      return false
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

  findAdr(commande:Commande){
    const client= this.clients.find(c=>c.email===commande.client);
    //console.log(commande)
    //console.log('['+client?.latitude+','+client?.longitude+']');
    if(client!==undefined){
      const longitude = parseFloat(client.longitude);
      const latitude = parseFloat(client.latitude);
      if(!this.location.some(loc => loc[0] === longitude && loc[1] === latitude)){
        this.location.push([longitude, latitude]);
        return '['+client.longitude+','+client.latitude+']';
      }
       
    }
    return '';
  }

  tostring(){
    let entrepotChoisi:Entrepot|undefined=this.entrepots.find(entrepot => entrepot.name === this.entrepotChoisi)
    let body='{"locations":[';
    for(let i=0;i<this.commandes.length;i++){
      const locationString=this.findAdr(this.commandes[i]);
      if(locationString!==''){
        body+=locationString
        body+=',';
      }
    }
    if(entrepotChoisi!==undefined){
      this.location.push([parseFloat(entrepotChoisi.longitude),parseFloat(entrepotChoisi.latitude)]);
      body=body+'['+entrepotChoisi?.longitude+','+entrepotChoisi?.latitude+']';
      body+='],"metrics":["distance"]}';
      console.log(body);
      console.log(this.location);
      return body;
    }else{
      return '';
    }
    
  }

  matrice(){
    let request = new XMLHttpRequest();
    let matrix;
    this.location=[];


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
          this.m.k=this.livreursChoisi.length;
          this.m.matrix=matrix;
          this.m.start=matrix.length;
          console.log('matrix 已被赋值:', this.m);
          this.http.post('http://localhost:4201/planner/planif', this.m)
              .subscribe(
                  (response) => {
                      console.log('Post request successful:', response);
                  },
                  (error) => {
                      console.error('Error in post request:', error);
                  }
              );
      } else {
          console.log('matrix 尚未被赋值');
      }
  };

    //const m:Matrice={k:3,matrix:[[0.0,140840.89,2434865.5,9801959.0],[139758.16,0.0,2386344.0,9753437.0],[2384907.75,2336977.0,0.0,7328348.0],[9737272.0,9689341.0,7312725.5,0.0]],start:0};
    /*this.http.post('http://localhost:4201/planner/planif',m).subscribe({
      next: (data) => {
        console.log('Post request successful:', data); // 请求成功时的处理逻辑
      },
      error: (error) => {
        console.error('Error in post request:', error); // 请求失败时的处理逻辑
      }
    });
    
    
    for(let i=0;i<matrix.length;i++){
            for(let j=i;j<matrix[0].length;j++){
              matrix[j][i]=matrix[i][j];
            }
          }*/
    

  }

  voirInfos(){
    let livraisons:Livraison[]=[];
    this.commandes.forEach(commande=>{
      const livraison = livraisons.find(l => l.client === commande.client);
      if(livraison){
        livraison.commandes.push(commande);
      }else{
        livraisons.push({
          id: livraisons.length + 1, // 这里可以根据需要生成唯一ID
          client: commande.client,
          commandes: [commande]
        });
      }
    })
    this.tournee={id:this.tournee.id,journee:'',date:this.tournee.date,entrepot:this.entrepotChoisi,camion:this.camionsChoisi,liveurs:this.livreursChoisi,livraison:livraisons,commandes:this.commandes};
    this.sharedService.addTournee(this.tournee);
    this.router.navigate(['/infos-tournee'], { queryParams: {tournee: JSON.stringify(this.tournee),location:JSON.stringify(this.location)} });
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

interface ParsedData {
  [key: string]: string;
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