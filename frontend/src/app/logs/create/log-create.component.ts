import { Component, OnInit } from '@angular/core';
import { Location }                 from '@angular/common';
import '../../rxjs-operators';
import { LogService } from '../../services/log.service';
import {Log} from "../../models/log";
import {AuthenticationService} from "../../services/authentication.service";
import {user} from "app/session";

@Component({
  moduleId: module.id,
  selector: 'my-log-create',
  templateUrl: 'log-create.component.html',
  styleUrls: ['log-create.component.css']
})
export class LogCreateComponent implements OnInit {
  private model: Log;

  constructor(
    private logService: LogService,
    private location: Location,
    private authService: AuthenticationService) {
    this.model = new Log();
  }

  ngOnInit(): void {
    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.logService.headers.append('Authorization', user.token);
        });
    }

    this.model = new Log();
  }

  save(): void {
    this.logService.create(this.model)
      .then(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
