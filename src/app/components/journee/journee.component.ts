import { CdkDropList, CdkDrag, CdkDragDrop } from '@angular/cdk/drag-drop';
import { CommonModule } from '@angular/common';
import { HttpClient,HttpClientModule } from '@angular/common/http';
import { AfterViewInit, ChangeDetectionStrategy, Component, ElementRef, OnInit, QueryList, ViewChild, ViewChildren, WritableSignal, computed, signal } from '@angular/core';
import { MatListModule } from '@angular/material/list';
import { CommandeService } from '../../services/commande.service';
import { TourneeComponent } from '../tournee/tournee.component';
import { FormsModule } from '@angular/forms';
import { SharedService } from '../../services/shared.service';

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
export class JourneeComponent implements AfterViewInit,OnInit {
  commandes:Commande[]=[];
  livreurs:Livreur[]=[];
  camions:Camion[]=[];
  entrepots:Entrepot[]=[];
  commandeOuvert:Commande[]=[];
  commandePrevu:Commandes[]=[];
  tournees:Tournee[]=[];
  clients:Client[]=[];
  entrepotChoisi:string='';
  tourneeV:boolean=false;



  constructor(private http:HttpClient,private sharedService:SharedService) { }

  readonly sigL1 = signal<readonly string[]>(["A", "B", "C", "D"])
  readonly sigCP = signal<readonly string[]>([])
  readonly sigCO = signal<readonly string[]>([])

  readonly sigLists = computed(() => [this.sigCO, this.sigCP]);
  isViewInit = false;

  @ViewChildren("L") matLists!: QueryList<CdkDropList>;
  private readonly _sigDropListRefs = signal<CdkDropList<any>[]>([])
  readonly sigDropListRefs = this._sigDropListRefs.asReadonly();

  ngAfterViewInit(): void {
    const update = () => {
      const L = this.matLists.toArray();
      this._sigDropListRefs.set(L)
    };
    this.matLists.changes.subscribe(update);
    this.isViewInit = true;
    update();
  }

  ngOnInit(): void {
    this.getCsvData();
    this.lireInfos();
    //this.tournees=this.sharedService.getTournee();
    this.tournees= this.sharedService.getTournee();
    //this.loadTournee();
  }

  loadTournee(){
    this.tournees= this.sharedService.getTournee();
    this.tourneeV=true;
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
    if(this.sharedService.getCommandeOuvert().length>0){
      this.commandeOuvert=this.sharedService.getCommandeOuvert();
      this.commandePrevu=this.sharedService.getCommandePrevu();
    }
    this.sigCO.set(this.commandeOuvert.map(commande => commande.reference));
    this.sigCP.set(this.commandePrevu.map(commande => commande.commande.reference));
    //this.commandePrevu=[];
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
        ligne:obj[headers[5]],
        client:obj[headers[6]],
      }
      result.push(commande);
    }
    return result;
  }

  lireLivreur(csvData:string){
    const result:Livreur[]= [];
    const jsonArray = JSON.parse(csvData);
    for (let item of jsonArray) {
      const livreur: Livreur = Object.assign({}, item);
      console.log(livreur);
      result.push(livreur);
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
        name:obj[headers[0]],
        lettre:obj[headers[1]],
        photo:obj[headers[2]],
        adresse:obj[headers[3]],
        codePostal:obj[headers[4]],
        ville:obj[headers[5]],
        latitude:obj[headers[6]],
        longitude:obj[headers[7]],
      }
      result.push(entrepot);   
    }
    return result;
  }

  lireClient(csvData:string){
    const lines=csvData.split('\n');
    const result:Client[]= [];

    const headers=lines[0].split(',');
    for(let i=1;i<lines.length;i++){
      const obj: ParsedData = {};
      const currentLine=lines[i].split(',');
      for(let j=0;j<headers.length;j++){
        obj[headers[j]]=currentLine[j];
      }
      const client:Client={
        email:obj[headers[0]],
        prenom:obj[headers[1]],
        nom:obj[headers[2]],
        adresse:obj[headers[3]],
        codePostal:obj[headers[4]],
        ville:obj[headers[5]],
        latitude:obj[headers[6]],
        longitude:obj[headers[7]],
        commandes:obj[headers[8]],
      }
      result.push(client);   
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
  this.http.get('./assets/Export_Clients.csv',{responseType:'text'})
    .subscribe(data=>{
      //this.csvData=this.parseCsvData(data);
      this.clients=this.lireClient(data);
      console.log(this.clients)
    },
  );
  }

  changeEtat(){
    for(let i=0;i<this.sigCP().length;i++){
      for(let j=0;j<this.commandeOuvert.length;j++){
        if(this.sigCP()[i]==this.commandeOuvert[j].reference){
          this.commandeOuvert[j].etat='prévu';
          this.commandePrevu.push({commande:this.commandeOuvert[j],disabled:false,tourneeID:-1});
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
    this.sharedService.setCommandeOuvert(this.commandeOuvert);
    this.sharedService.setCommandePrevu(this.commandePrevu)
  }


  addTournee() {
    //this.tournees.push({id:this.tournees.length+1,journee:'',date:new Date(),entrepot:'',camion:'',liveurs:[],livraison:[],commandes:[]});
    this.sharedService.addTournee({id:this.idTournee(),journee:'',date:new Date(),entrepot:'',camion:'',liveurs:[],livraison:[],commandes:[]})
  }

  idTournee(){
    if(this.tournees.length==0){
      return 1
    }else{
      return this.tournees[this.tournees.length-1].id+1  
    }
  }

  removeTournee(index: number) {
    //this.sharedService.removeTournee(this.tournees[index])
    let tou=this.tournees[index]
    this.tournees.splice(index, 1);
    for(let i=0;i<this.commandePrevu.length;i++){
      if(this.commandePrevu[i].tourneeID==tou.id){
        this.commandePrevu[i].tourneeID=-1;
        this.commandePrevu[i].disabled=!this.commandePrevu[i].disabled;
      }
    }
    this.sharedService.setCommandePrevu(this.commandePrevu)
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
        item.tourneeID=-1;
      }
    }
    this.sharedService.setCommandePrevu(this.commandePrevu)
  }

  generateName(index: number): string {
    return `camions_${index}`;
  }

  ischecked(c:number,i:number){
    return i===c
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

interface Commandes{
  commande:Commande;
  disabled:boolean;
  tourneeID:number;
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