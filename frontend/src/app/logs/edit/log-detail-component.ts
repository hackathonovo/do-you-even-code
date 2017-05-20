import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';
import '../../rxjs-operators';
import { LogService } from '../../services/log.service';
import {Log} from "../../models/log";
import {AuthenticationService} from "../../services/authentication.service";
import {user} from "../../session";

@Component({
  moduleId: module.id,
  selector: 'my-log-detail',
  templateUrl: 'log-detail.component.html',
  styleUrls: ['log-detail.component.css']
})
export class LogDetailComponent implements OnInit {
  @Input()
  model: Log;

  constructor(
    private logService: LogService,
    private route: ActivatedRoute,
    private location: Location,
    private authService: AuthenticationService) {}

  ngOnInit(): void {
    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.logService.headers.append('Authorization', user.token);
          this.getEntity();
        });
    } else {
      this.getEntity();
    }
  }

  private getEntity(): void {
    this.route.params
      .switchMap((params: Params) => this.logService.get(+params['id']))
      .subscribe(log => this.model = log as Log);
  }

  save(): void {
    this.logService.update(this.model)
      .then(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
