import { CdkDropList, CdkDrag, CdkDragDrop } from '@angular/cdk/drag-drop';
import { CommonModule } from '@angular/common';
import { HttpClient,HttpClientModule } from '@angular/common/http';
import { AfterViewInit, ChangeDetectionStrategy, Component, QueryList, ViewChildren, WritableSignal, computed, signal } from '@angular/core';
import { MatListModule } from '@angular/material/list';
import { CommandeService } from '../../services/commande.service';
import { TourneeComponent } from '../tournee/tournee.component';
import { FormsModule } from '@angular/forms';

interface ListDD_Data<T> {
  readonly sig: WritableSignal<readonly T[]>;
  readonly value: T;
}

@Component({
  selector: 'app-lists',
  standalone: true,
  imports: [
    CommonModule, MatListModule,
    CdkDropList, CdkDrag,HttpClientModule,TourneeComponent,FormsModule
  ],
  templateUrl: './journee.component.html',
  styleUrl: './journee.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class JourneeComponent implements AfterViewInit {
  commandes:Commande[]=[];
  livreurs:Livreur[]=[];
  camions:Camion[]=[];
  entrepots:Entrepot[]=[];
  commandeOuvert:Commande[]=[];
  commandePrevu:Commandes[]=[];
  tournees:Tournee[]=[];
  entrepotChoisi:string='';


  constructor(private http:HttpClient) { }

  readonly sigL1 = signal<readonly string[]>(["A", "B", "C", "D"])
  readonly sigCP = signal<readonly string[]>([])
  readonly sigCO = signal<readonly string[]>([])

  readonly sigLists = computed(() => [this.sigCO, this.sigCP]);

  @ViewChildren("L") matLists!: QueryList<CdkDropList>;
  private readonly _sigDropListRefs = signal<CdkDropList<any>[]>([])
  readonly sigDropListRefs = this._sigDropListRefs.asReadonly();

  ngAfterViewInit(): void {
    this.getCsvData();
    this.lireInfos();
    const update = () => {
      const L = this.matLists.toArray();
      this._sigDropListRefs.set(L)
    };
    this.matLists.changes.subscribe(update);
    update();
  }

  dropIntoList(sigTarget: WritableSignal<readonly string[]>, dropEvent: CdkDragDrop<unknown, unknown, ListDD_Data<string>>) {
    
    // Remove from source
    const { sig: sigSource, value } = dropEvent.item.data;

    if (sigSource === sigTarget && dropEvent.currentIndex === dropEvent.previousIndex) {
      return
    }

    sigSource.update(L => L.filter(e => e !== value))
    
    // Append to target, at the right index
    sigTarget.update(L => {
      const nL = [...L]
      nL.splice(dropEvent.currentIndex, 0, value)
      return nL;
    })

    console.log( sigTarget() )
  }
  getCsvData(){
    this.http.get('./assets/Export_Commandes.csv',{responseType:'text'})
    .subscribe(data=>{
      this.commandes=this.lireCommande(data);
    },
  );
    this.commandeOuvert=this.commandes.filter(commande => commande.etat === "ouverte");
    this.sigCO.set(this.commandeOuvert.map(commande => commande.reference));
    this.sigCP.set([]);
    this.commandePrevu=[];
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
        commantaire:obj[headers[4]],
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

  changeEtat(){
    for(let i=0;i<this.sigCP().length;i++){
      for(let j=0;j<this.commandeOuvert.length;j++){
        if(this.sigCP()[i]==this.commandeOuvert[j].reference){
          this.commandeOuvert[j].etat='prévu';
          this.commandePrevu.push({commande:this.commandeOuvert[j],disabled:false,tourneeID:0});
          this.commandeOuvert.splice(j,1);
        }
      }
    }
    for(let i=0;i<this.sigCO().length;i++){
      for(let j=0;j<this.commandePrevu.length;j++){
        if(this.sigCO()[i]==this.commandePrevu[j].commande.reference){
          this.commandePrevu[j].commande.etat='ouvert';
          this.commandeOuvert.push(this.commandePrevu[j].commande);
          this.commandePrevu.splice(j,1);
        }
      }
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
  commantaire: string;
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