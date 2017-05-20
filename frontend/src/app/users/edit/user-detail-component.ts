import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';
import '../../rxjs-operators';
import { UserService } from '../../services/user.service';
import {User} from "../../models/user";
import {AuthenticationService} from "../../services/authentication.service";
import {user} from "../../session";

@Component({
  moduleId: module.id,
  selector: 'my-user-detail',
  templateUrl: 'user-detail.component.html',
  styleUrls: ['user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  @Input()
  model: User;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private location: Location,
    private authService: AuthenticationService) {}

  ngOnInit(): void {
    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.userService.headers.append('Authorization', user.token);
          this.getEntity();
        });
    } else {
      this.getEntity();
    }
  }

  private getEntity(): void {
    this.route.params
      .switchMap((params: Params) => this.userService.get(+params['id']))
      .subscribe(user => this.model = user as User);
  }

  save(): void {
    this.userService.update(this.model)
      .then(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
