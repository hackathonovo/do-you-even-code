import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PointCreateComponent} from "./points/create/point-create.component";
import {PointDetailComponent} from "./points/edit/point-detail-component";
import {PointComponent} from "./points/points.component";
import {MapComponent} from "./maps/map.component";
import {UserDetailComponent} from "./users/edit/user-detail-component";
import {UserCreateComponent} from "./users/create/user-create.component";
import {UserComponent} from "./users/users.component";
import {ActionComponent} from "./actions/actions.component";
import {ActionCreateComponent} from "./actions/create/action-create.component";
import {ActionDetailComponent} from "./actions/edit/action-detail-component";
import {LogDetailComponent} from "./logs/edit/log-detail-component";
import {LogCreateComponent} from "./logs/create/log-create.component";
import {LogComponent} from "./logs/logs.component";
import {TimeTableDetailComponent} from "./timetables/edit/timetable-detail-component";
import {TimeTableComponent} from "app/timetables/timetables.component";
import {TimeTableCreateComponent} from "./timetables/create/timetable-create.component";

const routes: Routes = [
  { path: '', redirectTo: '/points', pathMatch: 'full' },
  { path: 'points/detail/:id', component: PointDetailComponent },
  { path: 'points/create', component: PointCreateComponent },
  { path: 'points',     component: PointComponent },

  { path: 'map',     component: MapComponent },

  { path: 'users/detail/:id', component: UserDetailComponent },
  { path: 'users/create', component: UserCreateComponent },
  { path: 'users',     component: UserComponent },

  { path: 'actions/detail/:id', component: ActionDetailComponent },
  { path: 'actions/create', component: ActionCreateComponent },
  { path: 'actions',     component: ActionComponent },

  { path: 'logs/detail/:id', component: LogDetailComponent },
  { path: 'logs/create', component: LogCreateComponent },
  { path: 'logs',     component: LogComponent },

  { path: 'timetables/detail/:id', component: TimeTableDetailComponent },
  { path: 'timetables/create', component: TimeTableCreateComponent },
  { path: 'timetables',     component: TimeTableComponent },
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
