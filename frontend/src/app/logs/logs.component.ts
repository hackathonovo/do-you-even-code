import { Component, OnInit } from '@angular/core';
import { Log } from '../models/log';
import { LogService } from '../services/log.service';
import '../rxjs-operators';
import {user} from "../session";
import {AuthenticationService} from "../services/authentication.service";

@Component({
  moduleId: module.id,
  selector: 'my-logs',
  templateUrl: 'logs.component.html',
  styleUrls: ['logs.component.css'],
  providers: [LogService]
})

export class LogComponent implements OnInit {
  list: Log[];
  errorMessage: any;

  constructor(
    private logService: LogService,
    private authService: AuthenticationService) {

    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.logService.headers.append('Authorization', user.token);
          this.getList();
        });
    } else {
      this.getList();
    }
  }

  ngOnInit(): void {

  }

  private getList(): void {
    this.logService.list().subscribe(
      list => this.list = list,
      error =>  this.errorMessage = <any>error
    );
  }

  remove(id: number): void {
    if(confirm("Želite obrisati ovaj zapisnik?")) {
      this.logService.remove(id)
        .then(() => {
          this.getList();
        });
    }
  }

}
