import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MapComponent } from './map.component';
import {AgmCoreModule} from "@agm/core";
import {KeyChain} from '../../keys';
import {MapViewComponent} from "./view/map-view.component";
import {HeaderModule} from "app/header/header.module";

@NgModule({
  declarations: [
    MapComponent,
    MapViewComponent,
  ],
  imports: [
    BrowserModule,
    AgmCoreModule.forRoot({
      apiKey: KeyChain.google
    }),
    HeaderModule
  ],
  providers: [],
})
export class MapModule { }
