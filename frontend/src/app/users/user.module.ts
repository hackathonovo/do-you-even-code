import { NgModule }       from '@angular/core';
import { UserDetailComponent } from './edit/user-detail-component';
import { UserComponent }     from './users.component';

import {UserCreateComponent} from './create/user-create.component';
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
    UserDetailComponent,
    UserCreateComponent,
    UserComponent,
  ],
})

export class UserModule {
}


