import { Component } from '@angular/core';
import { isHeaderVisible } from './session';

@Component({
  moduleId: module.id,
  selector: 'my-app',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
})
export class AppComponent {
  public header;

  constructor() {
    this.header = isHeaderVisible;
  }
}
