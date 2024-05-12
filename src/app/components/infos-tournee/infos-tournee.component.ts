import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from '../../services/shared.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-infos-tournee',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './infos-tournee.component.html',
  styleUrl: './infos-tournee.component.scss'
})
export class InfosTourneeComponent  implements OnInit{
  
  constructor(private route: ActivatedRoute,private sharedService:SharedService,private router: Router) { }

  tournee:Tournee|undefined
  location:number[][]|undefined
  commandePrevu:Commandes[]|undefined
  tourneeTrans:number=-1;
  livraisonTrans:number=-1;
  tousTournee:Tournee[]|undefined
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const tourneeString = params['tournee'];
      const locationString=params['location'];
      this.tournee = JSON.parse(tourneeString); // 将字符串转换为对象
      this.location=JSON.parse(locationString);
      //console.log('Received tournee:', this.location);
    });
    this.commandePrevu=this.sharedService.getCommandePrevu()
    this.tousTournee=this.sharedService.getTournee();
  }

  print(){
    console.log('Received tournee:', this.location);
    console.log(this.sharedService.getTournee())
  }

  voirCarte(){
    this.router.navigate(['/leaflet'], { queryParams: {location:JSON.stringify(this.location)} });
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