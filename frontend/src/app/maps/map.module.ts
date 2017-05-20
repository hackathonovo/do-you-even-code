import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MapComponent } from './map.component';
import {AgmCoreModule} from "@agm/core";
import {KeyChain} from '../../keys';
import {MapViewComponent} from "./view/map-view.component";
import {HeaderModule} from "app/header/header.module";
import {MapAreasEditComponent} from "app/maps/areas-edit/map-areas-edit.component";
import {MapUsersEditComponent} from "app/maps/users-edit/map-users-edit.component";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    MapComponent,
    MapViewComponent,
    MapUsersEditComponent,
    MapAreasEditComponent
  ],
  imports: [
    BrowserModule,
    AgmCoreModule.forRoot({
      apiKey: KeyChain.google
    }),
    HeaderModule,
    FormsModule
  ],
  providers: [],
})
export class MapModule { }
