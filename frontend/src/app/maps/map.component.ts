import {ApplicationRef, Component, OnDestroy} from '@angular/core';
import {PointService} from "../services/point.service";
import {PolygonService} from "../services/polygon.service";
import {Polygon} from "../models/polygon";
import {Point} from "../models/point";
import {user} from "../session";
import {AuthenticationService} from "../services/authentication.service";
import {LatLngLiteral} from "@agm/core";
import {Observable} from "rxjs/Rx";
import {Subscription} from "rxjs/Subscription";
declare let $:any;
const ICON_SIZE = 40;
const REFRESH_INTERVAL = 1000; // in ms

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnDestroy{
  zoom: number = 8;
  areas : Array<Polygon> = [];
  markers: Point[] = [];
  lat: number = 45.48709473229837;
  lng: number = 16.248779296875;
  isToolActive = false;
  activeTool: string = null;
  customTool: string = null;
  customClassName: string = null;
  mapStyle: any = [{"elementType":"geometry","stylers":[{"color":"#ebe3cd"}]},{"elementType":"labels.text.fill","stylers":[{"color":"#523735"}]},{"elementType":"labels.text.stroke","stylers":[{"color":"#f5f1e6"}]},{"featureType":"administrative","elementType":"geometry.stroke","stylers":[{"color":"#c9b2a6"}]},{"featureType":"administrative.land_parcel","stylers":[{"visibility":"off"}]},{"featureType":"administrative.land_parcel","elementType":"geometry.stroke","stylers":[{"color":"#dcd2be"}]},{"featureType":"administrative.land_parcel","elementType":"labels.text.fill","stylers":[{"color":"#ae9e90"}]},{"featureType":"administrative.neighborhood","stylers":[{"visibility":"off"}]},{"featureType":"landscape.natural","elementType":"geometry","stylers":[{"color":"#dfd2ae"}]},{"featureType":"poi","elementType":"geometry","stylers":[{"color":"#dfd2ae"}]},{"featureType":"poi","elementType":"labels.text","stylers":[{"visibility":"off"}]},{"featureType":"poi","elementType":"labels.text.fill","stylers":[{"color":"#93817c"}]},{"featureType":"poi.business","stylers":[{"visibility":"off"}]},{"featureType":"poi.park","elementType":"geometry.fill","stylers":[{"color":"#a5b076"}]},{"featureType":"poi.park","elementType":"labels.text.fill","stylers":[{"color":"#447530"}]},{"featureType":"road","elementType":"geometry","stylers":[{"color":"#f5f1e6"}]},{"featureType":"road","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road","elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"road.arterial","stylers":[{"visibility":"off"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"color":"#fdfcf8"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"color":"#f8c967"}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"color":"#e9bc62"}]},{"featureType":"road.highway","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road.highway.controlled_access","elementType":"geometry","stylers":[{"color":"#e98d58"}]},{"featureType":"road.highway.controlled_access","elementType":"geometry.stroke","stylers":[{"color":"#db8555"}]},{"featureType":"road.local","stylers":[{"visibility":"off"}]},{"featureType":"road.local","elementType":"labels.text.fill","stylers":[{"color":"#806b63"}]},{"featureType":"transit","stylers":[{"visibility":"off"}]},{"featureType":"transit.line","elementType":"geometry","stylers":[{"color":"#dfd2ae"}]},{"featureType":"transit.line","elementType":"labels.text.fill","stylers":[{"color":"#8f7d77"}]},{"featureType":"transit.line","elementType":"labels.text.stroke","stylers":[{"color":"#ebe3cd"}]},{"featureType":"transit.station","elementType":"geometry","stylers":[{"color":"#dfd2ae"}]},{"featureType":"water","elementType":"geometry.fill","stylers":[{"color":"#b9d3c2"}]},{"featureType":"water","elementType":"labels.text","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"labels.text.fill","stylers":[{"color":"#92998d"}]}];
  areaColor: string = "green";
  markerColor: string = "black";
  errorMessage: string;
  tempPoints: Array<LatLngLiteral> = [];
  isFABVisible: boolean = false;
  autoRefresh: Subscription;

  constructor(private application: ApplicationRef,
              private pointService: PointService,
              private polygonService: PolygonService,
              private authService: AuthenticationService) {

    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.polygonService.headers.append('Authorization', user.token);
          this.pointService.headers.append('Authorization', user.token);
          this.getMapData();
          //this.setAutoRefresh();
        });
    } else {
      this.getMapData();
      //this.setAutoRefresh();
    }
  }

  ngOnDestroy(): void {
    //this.autoRefresh.unsubscribe()
  }

  private setAutoRefresh(): void {
    this.autoRefresh = Observable.interval(REFRESH_INTERVAL).subscribe(x => {
      this.getMapData();
    });
  }

  private getMapData() {
    //get markers
    this.pointService.list().subscribe(
      list => {
        if(JSON.stringify(this.markers) != JSON.stringify(list)) {
          this.markers = list;
        }
      },
      error =>  this.errorMessage = <any>error
    );

    //get areas
    this.polygonService.list().subscribe(
      list => {
        if(JSON.stringify(this.areas) != JSON.stringify(list)) {
          this.areas = list;
        }
      },
      error =>  this.errorMessage = <any>error
    );
  }

  clickedMarker(marker: Point) {
    if(this.customTool == "DELETE") {
      let index = this.markers.indexOf(marker);
      this.markers.splice(index, 1);
      this.pointService.remove(marker.id);
    }
  }

  clickedPaths(area: Polygon){
    if(this.customTool == "DELETE") {
      let index = this.areas.indexOf(area);
      this.areas.splice(index, 1);

      if(area.id) {
        this.polygonService.remove(area.id);
      }
    }
  }

  mapClicked(event: any) {
    if(this.isToolActive) {
      switch (this.customTool) {
        case "DELETE":
          break;
        case "AREA":
          this.tempPoints.push({lat: event.coords.lat, lng: event.coords.lng});
          break;
        default:
          let canvas = document.createElement("canvas");
          canvas.width = ICON_SIZE;
          canvas.height = ICON_SIZE;
          let ctx = canvas.getContext("2d");
          ctx.fillStyle = this.markerColor;
          ctx.font = ICON_SIZE + "px FontAwesome";
          ctx.textAlign = "center";
          ctx.textBaseline = "middle";
          ctx.fillText(this.activeTool, ICON_SIZE/2, ICON_SIZE/2);

          let marker = new Point();

          marker.lat = event.coords.lat;
          marker.lng = event.coords.lng;
          marker.label = canvas.toDataURL('image/png');

          this.markers.push(marker);
          this.pointService.create(marker).subscribe(data => this.getMapData());

      }
    }
  }

  markerDragEnd(marker: Point, event: any) {
    let index = this.markers.indexOf(marker);
    this.markers[index].lat = event.coords.lat;
    this.markers[index].lng = event.coords.lng;
    this.pointService.update(this.markers[index]).subscribe(data => this.getMapData());
  }

  onToolPick(unicode, className, custom = null): void {
    this.customTool = custom;
    this.activeTool = unicode;
    this.isToolActive = true;
    this.customClassName = className;
  }

  onColorPick(color): void {
    if(this.customTool == "AREA") {
      this.areaColor = color;
    } else {
      this.markerColor = color;
    }
  }

  createPolygon(): void {
    let polygon = new Polygon();
    polygon.color = this.areaColor;
    polygon.polygon = this.tempPoints;
    this.areas.push(polygon);
    this.tempPoints = [];
    this.polygonService.create(polygon).subscribe(data => this.getMapData());
  }

  onResetTool(): void {
    if(this.isToolActive) {
      this.activeTool = null;
      this.isToolActive = false;
      this.customTool = null;
      this.customClassName = null;
      this.tempPoints = [];
      this.application.tick();
    }
  }

  toggleFAB(toggle): void {
    this.isFABVisible = toggle;
    let selector = '.fixed-action-btn';
    toggle ? $(selector).openFAB() : $(selector).closeFAB();
  }
}
