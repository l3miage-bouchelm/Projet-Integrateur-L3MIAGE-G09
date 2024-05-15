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
    this.http.get('./assets/Export_Employés.csv',{responseType:'text'})
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

  isLoggedIn(livreur:Livreur[]) : void{
    const filteredLivreur = livreur.filter(item => item.nom === this.nom);
    const filteredTrigramme = livreur.filter(item =>item.trigramme === this.trigramme);
    if (filteredLivreur.length >= 0 && filteredTrigramme.length >= 0) {
        this.sharedService.setData(true);
    } else {
        this.sharedService.setData(false);
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