import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommandeService {
  commande:Commande[]=[];
  constructor(private http:HttpClient) { }
  getCsvData(){
    this.http.get('./assets/Export_Employés.csv',{responseType:'text'})
    .subscribe(data=>{
      this.commande=this.parseCsvData(data);
    },
  );
  }

  parseCsvData(csvData:string){
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

  getCommande(){
    this.getCsvData();
    return this.commande;
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