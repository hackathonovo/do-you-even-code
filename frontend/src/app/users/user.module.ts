import { NgModule }       from '@angular/core';
import { UserDetailComponent } from './edit/user-detail-component';
import { UserComponent }     from './users.component';

import {UserCreateComponent} from './create/user-create.component';
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
    UserDetailComponent,
    UserCreateComponent,
    UserComponent,
  ],
})

export class UserModule {
}


