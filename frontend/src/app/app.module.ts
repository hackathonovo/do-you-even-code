import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { AppComponent }        from './app.component';
import {HttpModule, JsonpModule} from '@angular/http';

import { AppRoutingModule }     from './app-routing.module';
import {PointModule} from './points/point.module';
import {HeaderModule} from "./header/header.module";
import {MapModule} from "./maps/map.module";
import {PointService} from "./services/point.service";
import {PolygonService} from "./services/polygon.service";
import {AuthenticationService} from "./services/authentication.service";
import {ActionService} from "./services/action.service";
import {UserService} from "./services/user.service";
import {TimeTableService} from "./services/timetable.service";
import {LogService} from "./services/log.service";
import {UserModule} from "./users/user.module";
import {ActionModule} from "./actions/action.module";
import {TimeTableModule} from "./timetables/timetable.module";
import {LogModule} from "./logs/log.module";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    AppRoutingModule,
    PointModule,
    HeaderModule,
    MapModule,
    UserModule,
    ActionModule,
    TimeTableModule,
    LogModule
  ],
  declarations: [
    AppComponent
  ],
  providers: [
    PointService,
    PolygonService,
    AuthenticationService,
    ActionService,
    UserService,
    TimeTableService,
    LogService
  ],
  bootstrap: [ AppComponent ]
})

export class AppModule {
}


