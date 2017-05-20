import { NgModule }       from '@angular/core';
import { PointDetailComponent } from './edit/point-detail-component';
import { PointComponent }     from './points.component';

import {PointCreateComponent} from './create/point-create.component';
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";

@NgModule({
  imports: [
    FormsModule,
    BrowserModule,
    RouterModule
  ],
  declarations: [
    PointDetailComponent,
    PointCreateComponent,
    PointComponent,
  ],
})

export class PointModule {
}


