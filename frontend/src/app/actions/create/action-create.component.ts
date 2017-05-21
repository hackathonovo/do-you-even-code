import { Component, OnInit } from '@angular/core';
import { Location }                 from '@angular/common';
import '../../rxjs-operators';
import { ActionService } from '../../services/action.service';
import {Action} from "../../models/action";
import {AuthenticationService} from "../../services/authentication.service";
import {user} from "app/session";

@Component({
  moduleId: module.id,
  selector: 'my-action-create',
  templateUrl: 'action-create.component.html',
  styleUrls: ['action-create.component.css']
})
export class ActionCreateComponent implements OnInit {
  private model: Action;

  constructor(
    private actionService: ActionService,
    private location: Location,
    private authService: AuthenticationService) {
    this.model = new Action();
  }

  ngOnInit(): void {
    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.actionService.headers.append('Authorization', user.token);
        });
    }

    this.model = new Action();
  }

  save(): void {
    this.actionService.create(this.model)
      .then(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
