import { Component, OnInit } from '@angular/core';
import '../rxjs-operators';
import {Router} from "@angular/router";
declare let $: any;

@Component({
  moduleId: module.id,
  selector: 'my-header',
  templateUrl: 'header.component.html',
  styleUrls: ['header.component.css'],
})

export class HeaderComponent implements OnInit {
  isDropDownVisible = false;

  constructor(
    private router: Router
  ) {}

  ngOnInit(): void {

  }

  onDropDownClick(): void {
    this.isDropDownVisible = !this.isDropDownVisible;
  }

  gotoDetail(id: number): void {
    this.router.navigate(['/points/detail/', id]);
  }

}
