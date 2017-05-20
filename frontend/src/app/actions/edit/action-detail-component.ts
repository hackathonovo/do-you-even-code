import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';
import '../../rxjs-operators';
import { ActionService } from '../../services/action.service';
import {Action} from "../../models/action";
import {AuthenticationService} from "../../services/authentication.service";
import {user} from "../../session";

@Component({
  moduleId: module.id,
  selector: 'my-action-detail',
  templateUrl: 'action-detail.component.html',
  styleUrls: ['action-detail.component.css']
})
export class ActionDetailComponent implements OnInit {
  @Input()
  model: Action;

  constructor(
    private actionService: ActionService,
    private route: ActivatedRoute,
    private location: Location,
    private authService: AuthenticationService) {}

  ngOnInit(): void {
    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.actionService.headers.append('Authorization', user.token);
          this.getEntity();
        });
    } else {
      this.getEntity();
    }
  }

  private getEntity(): void {
    this.route.params
      .switchMap((params: Params) => this.actionService.get(+params['id']))
      .subscribe(action => this.model = action as Action);
  }

  save(): void {
    this.actionService.update(this.model)
      .then(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
