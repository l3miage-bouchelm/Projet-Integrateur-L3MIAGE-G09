import { CommonModule} from '@angular/common';
import { ChangeDetectionStrategy, Component, OnDestroy, OnInit, Signal, computed, signal } from '@angular/core';

import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import L, {
  LatLng, Layer, TileLayer, tileLayer, polygon,
  Map as LeafletMap,
  Polygon as LeafletPolygon,
  Marker as LeafletMarker,
  Polyline,
} from 'leaflet';

import { Subscription } from 'rxjs';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatListModule } from '@angular/material/list';
import { GeoapiService, PositionToLatLng } from '../../services/geoapi.service';
import { getObsResize } from './utils/rxjs';
import { HttpClientModule } from '@angular/common/http';
import { getMarker } from './utils/marker';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-carto',
  standalone: true,
  providers: [ GeoapiService ],
  imports: [
    CommonModule, LeafletModule,
    MatGridListModule, MatListModule,
    HttpClientModule,
  ],
  templateUrl: './carto.component.html',
  styleUrl: './carto.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CartoComponent implements OnDestroy {
  readonly center = signal<LatLng>(new LatLng(45.166672, 5.71667));
  readonly zoom = signal<number>(12);
  private readonly tileLayer = signal<TileLayer>(tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' }))
  private readonly leafletMap = signal<LeafletMap | undefined>(undefined);
  private readonly communes = signal<(LeafletPolygon | LeafletMarker | Polyline)[]>([])

  readonly layers: Signal<Layer[]> = computed(() => [
    this.tileLayer(),
    ...this.communes(),
  ])

  private subResize?: Subscription;

  registerLeafletMap(m: LeafletMap, divMap: HTMLDivElement): void {
    this.leafletMap.set(m);
    this.subResize = getObsResize(divMap).subscribe(() => {
      m.invalidateSize({ animate: false })
      m.setView(this.center(), this.zoom());
    });
  }

   greenIcon = new L.Icon({
    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
  });

  blueIcon = new L.Icon({
    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
  });

  toLocation(l:number[][]){
    let lo:Location[]=[];
    l.forEach(loc=>{
      lo.push({longitude:loc[0],latitude:loc[1]})
    });
    return lo;
  }

  //location:number[][]=[[5.67959,45.112876],[5.672983,45.187277],[5.804502,45.233075],[5.687907,45.182207],[5.7369725,45.14852]];
  locations:Location[]=[{longitude:5.67959,latitude:45.112876},{longitude:5.672983,latitude:45.187277},{longitude:5.804502,latitude:45.233075},{longitude:5.687907,latitude:45.182207},{longitude:5.7369725,latitude:45.14852},{longitude:5.674091,latitude:45.213553},{longitude:5.630015,latitude:45.165433}];
  trajet:Trajets={trajets:[
    [
      0,
      5,
      4,
      6
    ],
    [
      0,
      2,
      3
    ],
    [
      0,
      1
    ]
  ],longTrajets:[
    35645.37,
    42178.82000000001,
    34414.46
  ]}
  colors = ['red', 'blue', 'green', 'yellow'];
  location:number[][]|undefined
  trajets:Trajets|undefined
  loc:Location[][]=[]
  num=0;
  constructor(private geoAPI: GeoapiService,private route: ActivatedRoute) {
    /*this.route.queryParams.subscribe(params => {
      const locationString=params['location'];
      this.location=JSON.parse(locationString);
      const trajetsString=params['trajets']
      this.trajets=JSON.parse(trajetsString);
      if(this.location){
        this.locations=this.toLocation(this.location)
      }
      console.log('Received trajets:', this.trajets);
    });*/
    let prevLongitude: number | undefined;
    let prevLatitude: number | undefined;
    const numLocations = this.locations.length;
    let index=0;
    if(this.trajet&&this.locations){
      for(let i=0;i<this.trajet.trajets.length;i++){
        let l:Location[]=[]
        this.trajet.trajets[i].forEach(t=>{
          l.push(this.locations[t])
        });
        this.loc.push(l);
      }
    }
    console.log(this.trajet.trajets)
    console.log("nmsl",this.loc);
    this.loc.forEach(l=>l.forEach
    ( async ({ longitude, latitude }) => {
      /*const [fp, fm] = await this.geoAPI.getCommune(postalCode);
      const p = polygon(
        fp.geometry.coordinates.map(L => L.map(PositionToLatLng)),
        {color: "black", fillColor: color, fillOpacity: 0.5}
      );*/
      
      const m = getMarker( PositionToLatLng([longitude,latitude]) );
      if (index === 0) {
        m.setIcon(this.greenIcon)
      }else{
        m.setIcon(this.blueIcon)
      }
      
      
      // 创建线条
      if (prevLongitude !== undefined && prevLatitude !== undefined) {
        const lineCoordinates = [
        L.latLng(prevLatitude, prevLongitude), // 起点坐标
        L.latLng(latitude, longitude) // 终点坐标
      ];
      const polyline = new Polyline(lineCoordinates, { color: this.colors[this.num%this.colors.length] });
      this.communes.update(LC => [...LC, m, polyline]);
      }else{
        this.communes.update(LC => [...LC, m]);
      }
      if(index!=l.length-1){
        prevLongitude = longitude;
        prevLatitude = latitude;
        index++;
      }else{
        prevLatitude=undefined;
        prevLongitude=undefined;
        index=0;
        this.num++;
      }
      
      
      
    }))
    geoAPI.getCommune('38000').then(console.log);
    
  }

  ngOnDestroy(): void {
    this.subResize?.unsubscribe();
  }
}

interface Location{
  longitude:number,
  latitude:number
}

interface Trajets{
  trajets:number[][],
  longTrajets:number[]
}



//prend les commande 
//fqit les equipes de  livraison
// tournees


/*
constructor(private geoAPI: GeoapiService) {
  [
    { postalCode: "38000", color: "blue" },
    { postalCode: "38130", color: "green" },
    { postalCode: "38170", color: "red" },
    { postalCode: "38600", color: "yellow" },
  ].forEach( async ({ postalCode, color }) => {
    const [fp, fm] = await this.geoAPI.getCommune(postalCode);
    const p = polygon(
      fp.geometry.coordinates.map(L => L.map(PositionToLatLng)),
      {color: "black", fillColor: color, fillOpacity: 0.5}
    );
    const m = getMarker( PositionToLatLng(fm.geometry.coordinates) );
    m.setIcon(this.greenIcon)
    
    // 创建线条
    const lineCoordinates = [
      L.latLng(45.166672, 5.71667), // 起点坐标
      L.latLng(fm.geometry.coordinates[1], fm.geometry.coordinates[0]) // 终点坐标
    ];
    const polyline = new Polyline(lineCoordinates, { color: 'red' });
    this.communes.update(LC => [...LC, m, polyline]);
    
  })
  geoAPI.getCommune('38000').then(console.log);
  
}

ngOnDestroy(): void {
  this.subResize?.unsubscribe();
}*/