import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { UserService } from '../services/user.service';
import '../rxjs-operators';
import {user} from "../session";
import {AuthenticationService} from "../services/authentication.service";

@Component({
  moduleId: module.id,
  selector: 'my-users',
  templateUrl: 'users.component.html',
  styleUrls: ['users.component.css'],
  providers: [UserService]
})

export class UserComponent implements OnInit {
  list: User[];
  errorMessage: any;

  constructor(
    private userService: UserService,
    private authService: AuthenticationService) {

    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.userService.headers.append('Authorization', user.token);
          this.getList();
        });
    } else {
      this.getList();
    }
  }

  ngOnInit(): void {

  }

  private getList(): void {
    this.userService.list().subscribe(
      list => this.list = list,
      error =>  this.errorMessage = <any>error
    );
  }

  remove(id: number): void {
    if(confirm("Želite izbrisati ovog korisnika?")) {
      this.userService.remove(id)
        .then(() => {
          this.getList();
        });
    }
  }

}
