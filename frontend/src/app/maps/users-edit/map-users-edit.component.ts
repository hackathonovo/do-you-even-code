import {Component, OnDestroy} from '@angular/core';
import {PointService} from "../../services/point.service";
import {Polygon} from "../../models/polygon";
import {Point} from "../../models/point";
import {Observable} from "rxjs/Rx";
import {Subscription} from "rxjs/Subscription";
import {Action} from "../../models/action";
import {ActivatedRoute, Params} from "@angular/router";
import {ActionService} from "app/services/action.service";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
declare let $:any;
const REFRESH_INTERVAL = 1000; // in ms

@Component({
  selector: 'app-map',
  templateUrl: './map-users-edit.component.html',
  styleUrls: ['../map.component.css', './map-users-edit.component.css']
})
export class MapUsersEditComponent implements OnDestroy{
  zoom: number = 8;
  areas : Array<Polygon> = [];
  markers: Point[] = [];
  lat: number = 45.48709473229837;
  lng: number = 16.248779296875;
  isToolActive = false;
  customClassName: string = null;
  mapStyle: any = [{"elementType":"geometry","stylers":[{"color":"#ebe3cd"}]},{"elementType":"labels.text.fill","stylers":[{"color":"#523735"}]},{"elementType":"labels.text.stroke","stylers":[{"color":"#f5f1e6"}]},{"featureType":"administrative","elementType":"geometry.stroke","stylers":[{"color":"#c9b2a6"}]},{"featureType":"administrative.land_parcel","stylers":[{"visibility":"off"}]},{"featureType":"administrative.land_parcel","elementType":"geometry.stroke","stylers":[{"color":"#dcd2be"}]},{"featureType":"administrative.land_parcel","elementType":"labels.text.fill","stylers":[{"color":"#ae9e90"}]},{"featureType":"administrative.neighborhood","stylers":[{"visibility":"off"}]},{"featureType":"landscape.natural","elementType":"geometry","stylers":[{"color":"#dfd2ae"}]},{"featureType":"poi","elementType":"geometry","stylers":[{"color":"#dfd2ae"}]},{"featureType":"poi","elementType":"labels.text","stylers":[{"visibility":"off"}]},{"featureType":"poi","elementType":"labels.text.fill","stylers":[{"color":"#93817c"}]},{"featureType":"poi.business","stylers":[{"visibility":"off"}]},{"featureType":"poi.park","elementType":"geometry.fill","stylers":[{"color":"#a5b076"}]},{"featureType":"poi.park","elementType":"labels.text.fill","stylers":[{"color":"#447530"}]},{"featureType":"road","elementType":"geometry","stylers":[{"color":"#f5f1e6"}]},{"featureType":"road","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road","elementType":"labels.icon","stylers":[{"visibility":"off"}]},{"featureType":"road.arterial","stylers":[{"visibility":"off"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"color":"#fdfcf8"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"color":"#f8c967"}]},{"featureType":"road.highway","elementType":"geometry.stroke","stylers":[{"color":"#e9bc62"}]},{"featureType":"road.highway","elementType":"labels","stylers":[{"visibility":"off"}]},{"featureType":"road.highway.controlled_access","elementType":"geometry","stylers":[{"color":"#e98d58"}]},{"featureType":"road.highway.controlled_access","elementType":"geometry.stroke","stylers":[{"color":"#db8555"}]},{"featureType":"road.local","stylers":[{"visibility":"off"}]},{"featureType":"road.local","elementType":"labels.text.fill","stylers":[{"color":"#806b63"}]},{"featureType":"transit","stylers":[{"visibility":"off"}]},{"featureType":"transit.line","elementType":"geometry","stylers":[{"color":"#dfd2ae"}]},{"featureType":"transit.line","elementType":"labels.text.fill","stylers":[{"color":"#8f7d77"}]},{"featureType":"transit.line","elementType":"labels.text.stroke","stylers":[{"color":"#ebe3cd"}]},{"featureType":"transit.station","elementType":"geometry","stylers":[{"color":"#dfd2ae"}]},{"featureType":"water","elementType":"geometry.fill","stylers":[{"color":"#b9d3c2"}]},{"featureType":"water","elementType":"labels.text","stylers":[{"visibility":"off"}]},{"featureType":"water","elementType":"labels.text.fill","stylers":[{"color":"#92998d"}]}];
  errorMessage: string;
  autoRefresh: Subscription;
  model: Action;
  users: User[];

  constructor(private pointService: PointService,
              private actionService: ActionService,
              private userService: UserService,
              private route: ActivatedRoute) {

    this.route.params
      .switchMap((params: Params) => this.actionService.get(+params['aid']))
      .subscribe(user => {
        this.model = user as Action;
        this.getMapData(this.model.id);
        this.setAutoRefresh();

        this.userService.listByAction(this.model.id).subscribe(
          list => {
            if(JSON.stringify(this.markers) != JSON.stringify(list)) {
              this.users = list;
            }
          },
          error =>  this.errorMessage = <any>error
        );
      });
  }

  ngOnDestroy(): void {
    this.autoRefresh.unsubscribe();
  }

  private setAutoRefresh(): void {
    this.autoRefresh = Observable.interval(REFRESH_INTERVAL).subscribe(x => {
      this.getMapData(this.model.id);
      console.debug("refresh");
    });
  }

  private getMapData(actionId: number) {
    //get markers
    this.pointService.listByAction(actionId).subscribe(
      list => {
        if(JSON.stringify(this.markers) != JSON.stringify(list)) {
          this.markers = list;
        }
      },
      error =>  this.errorMessage = <any>error
    );
  }
}
