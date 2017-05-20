import { NgModule }       from '@angular/core';
import { LogDetailComponent } from './edit/log-detail-component';
import { LogComponent }     from './logs.component';

import {LogCreateComponent} from './create/log-create.component';
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";
import {HeaderModule} from "../header/header.module";

@NgModule({
  imports: [
    FormsModule,
    BrowserModule,
    RouterModule,
    HeaderModule
  ],
  declarations: [
    LogDetailComponent,
    LogCreateComponent,
    LogComponent,
  ],
})

export class LogModule {
}


