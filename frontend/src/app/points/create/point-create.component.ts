import { Component, OnInit } from '@angular/core';
import { Location }                 from '@angular/common';
import '../../rxjs-operators';
import { PointService } from '../../services/point.service';
import {Point} from "../../models/point";
import {AuthenticationService} from "../../services/authentication.service";
import {user} from "app/session";

@Component({
  moduleId: module.id,
  selector: 'my-point-create',
  templateUrl: 'point-create.component.html',
  styleUrls: ['point-create.component.css']
})
export class PointCreateComponent implements OnInit {
  private model: Point;

  constructor(
    private pointService: PointService,
    private location: Location,
    private authService: AuthenticationService) {
    this.model = new Point();
  }

  ngOnInit(): void {
    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.pointService.headers.append('Authorization', user.token);
        });
    }

    this.model = new Point();
  }

  save(): void {
    this.pointService.create(this.model)
      .then(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
