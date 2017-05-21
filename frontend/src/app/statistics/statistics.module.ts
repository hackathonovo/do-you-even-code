import { NgModule }       from '@angular/core';

import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";
import {HeaderModule} from "../header/header.module";
import {StatisticsComponent} from "./statistics.component";
import {ChartsModule} from "ng2-charts";

@NgModule({
  imports: [
    FormsModule,
    BrowserModule,
    RouterModule,
    HeaderModule,
    ChartsModule
  ],
  declarations: [
    StatisticsComponent
  ],
})

export class StatisticsModule {
}
