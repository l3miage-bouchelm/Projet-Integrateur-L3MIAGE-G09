import { Component,OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

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
  nom: string = '';

  constructor(private http:HttpClient,private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.getCsvData();
    this.route.queryParams.subscribe(params => {
      this.nom = params['nom'];
    });
  }

  getCsvData(){
    this.http.get('./assets/Export_Employés.csv',{responseType:'text'})
    .subscribe(data=>{
      this.csvData=this.parseCsvData(data);
      this.livreur=this.parseCsvData(data);
    },
  );
  }

  parseCsvData(csvData:string){
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
      result.push(livreur);
    }
    return result;
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