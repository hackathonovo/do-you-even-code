import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { Location }                 from '@angular/common';
import '../../rxjs-operators';
import { UserService } from '../../services/user.service';
import {User} from "../../models/user";
import {AuthenticationService} from "../../services/authentication.service";
import {user} from "app/session";

@Component({
  moduleId: module.id,
  selector: 'my-user-create',
  templateUrl: 'user-create.component.html',
  styleUrls: ['user-create.component.css']
})
export class UserCreateComponent implements OnInit {
  //@ViewChild('roleSelect') selectedRole: ElementRef;

  private model: User;

  constructor(
    private userService: UserService,
    private location: Location,
    private authService: AuthenticationService) {
    this.model = new User();
  }

  ngOnInit(): void {
    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.userService.headers.append('Authorization', user.token);
        });
    }

    this.model = new User();
  }

  save(): void {
    //this.model.role = this.selectedRole.nativeElement.options[this.selectedRole.nativeElement.selectedIndex].value;

    if(!this.model.role) {
      return;
    }

    try {
      this.userService.create(this.model)
        .then(() => this.goBack());
    } catch(e) {
      return;
    }
  }

  goBack(): void {
    this.location.back();
  }
}
