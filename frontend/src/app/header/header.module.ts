import { NgModule }       from '@angular/core';
import {BrowserModule} from "@angular/platform-browser";
import {HeaderComponent} from "./header.component";
import {RouterModule} from "@angular/router";

@NgModule({
  imports: [
    BrowserModule,
    RouterModule
  ],
  declarations: [
    HeaderComponent
  ],
  providers: [
  ],
  exports: [
    HeaderComponent
  ]
})

export class HeaderModule {
}


