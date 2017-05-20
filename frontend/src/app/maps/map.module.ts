import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MapComponent } from './map.component';
import {AgmCoreModule} from "@agm/core";
import {KeyChain} from '../../keys';

@NgModule({
  declarations: [
    MapComponent
  ],
  imports: [
    BrowserModule,
    AgmCoreModule.forRoot({
      apiKey: KeyChain.google
    })
  ],
  providers: [],
})
export class MapModule { }
