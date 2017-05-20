import { Component, OnInit } from '@angular/core';
import { Action } from '../models/action';
import { ActionService } from '../services/action.service';
import '../rxjs-operators';
import {user} from "../session";
import {AuthenticationService} from "../services/authentication.service";

@Component({
  moduleId: module.id,
  selector: 'my-actions',
  templateUrl: 'actions.component.html',
  styleUrls: ['actions.component.css'],
  providers: [ActionService]
})

export class ActionComponent implements OnInit {
  list: Action[];
  errorMessage: any;

  constructor(
    private actionService: ActionService,
    private authService: AuthenticationService) {

    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.actionService.headers.append('Authorization', user.token);
          this.getList();
        });
    } else {
      this.getList();
    }
  }

  ngOnInit(): void {

  }

  private getList(): void {
    this.actionService.list().subscribe(
      list => this.list = list,
      error =>  this.errorMessage = <any>error
    );
  }

  remove(id: number): void {
    if(confirm("Želite izbrisati ovu akciju?")) {
      this.actionService.remove(id)
        .then(() => {
          this.getList();
        });
    }
  }

}
