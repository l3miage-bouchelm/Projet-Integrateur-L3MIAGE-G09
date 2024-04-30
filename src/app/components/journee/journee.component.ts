import { CdkDropList, CdkDrag, CdkDragDrop } from '@angular/cdk/drag-drop';
import { CommonModule } from '@angular/common';
import { HttpClient,HttpClientModule } from '@angular/common/http';
import { AfterViewInit, ChangeDetectionStrategy, Component, QueryList, ViewChildren, WritableSignal, computed, signal } from '@angular/core';
import { MatListModule } from '@angular/material/list';
import { CommandeService } from '../../services/commande.service';

interface ListDD_Data<T> {
  readonly sig: WritableSignal<readonly T[]>;
  readonly value: T;
}

@Component({
  selector: 'app-lists',
  standalone: true,
  imports: [
    CommonModule, MatListModule,
    CdkDropList, CdkDrag,HttpClientModule
  ],
  templateUrl: './journee.component.html',
  styleUrl: './journee.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class JourneeComponent implements AfterViewInit {
  commande:Commande[]=[];
  livreur:Livreur[]=[];
  commandeOuvert:Commande[]=[];
  commandePrevu:Commande[]=[];
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
      this.commande=this.parseCsvData(data);
    },
  );
    this.commandeOuvert=this.commande.filter(commande => commande.etat === "ouverte");
    this.sigCO.set(this.commandeOuvert.map(commande => commande.reference));
    this.sigCP.set([]);
    this.commandePrevu=[];
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
        commantaire:obj[headers[4]],
        client:obj[headers[5]],
        ligne:obj[headers[6]],
      }
      result.push(commande);
    }
    return result;
  }

  changeEtat(){
    for(let i=0;i<this.sigCP().length;i++){
      for(let j=0;j<this.commandeOuvert.length;j++){
        if(this.sigCP()[i]==this.commandeOuvert[j].reference){
          this.commandeOuvert[j].etat='prévu';
          this.commandePrevu.push(this.commandeOuvert[j]);
          this.commandeOuvert.splice(j,1);
        }
      }
    }
    for(let i=0;i<this.sigCO().length;i++){
      for(let j=0;j<this.commandePrevu.length;j++){
        if(this.sigCO()[i]==this.commandePrevu[j].reference){
          this.commandePrevu[j].etat='ouvert';
          this.commandeOuvert.push(this.commandePrevu[j]);
          this.commandePrevu.splice(j,1);
        }
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