import { NgModule }       from '@angular/core';
import { TimeTableDetailComponent } from './edit/timetable-detail-component';
import { TimeTableComponent }     from './timetables.component';

import {TimeTableCreateComponent} from './create/timetable-create.component';
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";
import {HeaderModule} from "app/header/header.module";

@NgModule({
  imports: [
    FormsModule,
    BrowserModule,
    RouterModule,
    HeaderModule
  ],
  declarations: [
    TimeTableDetailComponent,
    TimeTableCreateComponent,
    TimeTableComponent,
  ],
})

export class TimeTableModule {
}


