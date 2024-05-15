import { Component,OnInit, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from '../../services/shared.service';


@Component({
  selector: 'app-livreur-travail',
  standalone: true,
  imports: [CommonModule,HttpClientModule],
  templateUrl: './livreur-travail.component.html',
  styleUrl: './livreur-travail.component.scss'
})
export class LivreurTravailComponent implements OnInit{
  csvData:any[]=[];
  livreur:Livreur[]=[];
  nom: string = localStorage.getItem('livreurNom') || '';;
  trigramme : string = localStorage.getItem('livreurTrigramme') || '';

  constructor(private http:HttpClient,private route: ActivatedRoute,private sharedService: SharedService) {}
  
  ngOnInit(): void {
    this.getCsvData();
  }
  //readonly isLoggedIn1=computed<number>(()=>this.isLoggedIn(this.livreur));
  getCsvData(){
<<<<<<< HEAD
    this.http.get('http://localhost:8080/employes',{responseType:'text'})
=======
    this.http.get('./assets/Export_Employés.csv',{responseType:'text'})
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
    .subscribe(data=>{
      //this.csvData=this.parseCsvData(data);
      this.livreur=this.parseCsvData(data);
      this.sharedService.setLivreurs(this.livreur);
      let liv:Livreur|undefined;
    liv=this.livreur.find(liv=>liv.nom.toUpperCase()===this.nom.toUpperCase())
    if(liv){
      this.sharedService.setData(true);
      console.log(this.sharedService.getData())
      this.sharedService.setEmploye(liv)
    }
    },
  );
  }

  parseCsvData(csvData:string){
    const result:Livreur[]= [];
    const jsonArray = JSON.parse(csvData);
    for (let item of jsonArray) {
      const livreur: Livreur = Object.assign({}, item);
      console.log(livreur);
      result.push(livreur);
    }
    return result;
  }

<<<<<<< HEAD
  isLoggedIn(livreur:Livreur[]){
=======
  isLoggedIn(livreur:Livreur[]) : void{
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
    const filteredLivreur = livreur.filter(item => item.nom === this.nom);
    const filteredTrigramme = livreur.filter(item =>item.trigramme === this.trigramme);
    if (filteredLivreur.length >= 0 && filteredTrigramme.length >= 0) {
        this.sharedService.setData(true);
<<<<<<< HEAD
        return filteredLivreur.length;
    } else {
        this.sharedService.setData(false);
        return filteredLivreur.length;
=======
    } else {
        this.sharedService.setData(false);
>>>>>>> 4d93b5eaad341077ba9d9163750d2e71240d4191
    }

  }

  getData(){
    return this.sharedService.getData()
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

interface ParsedData {
  [key: string]: string;
}