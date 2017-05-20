import { NgModule }       from '@angular/core';
import { TimeTableDetailComponent } from './edit/timetable-detail-component';
import { TimeTableComponent }     from './timetables.component';

import {TimeTableCreateComponent} from './create/timetable-create.component';
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
    TimeTableDetailComponent,
    TimeTableCreateComponent,
    TimeTableComponent,
  ],
})

export class TimeTableModule {
}


