import { Component, OnInit } from '@angular/core';
import { TimeTable } from '../models/timetable';
import { TimeTableService } from '../services/timetable.service';
import '../rxjs-operators';
import {user} from "../session";
import {AuthenticationService} from "../services/authentication.service";

@Component({
  moduleId: module.id,
  selector: 'my-timetables',
  templateUrl: 'timetables.component.html',
  styleUrls: ['timetables.component.css'],
  providers: [TimeTableService]
})

export class TimeTableComponent implements OnInit {
  list: TimeTable[];
  errorMessage: any;

  constructor(
    private timetableService: TimeTableService,
    private authService: AuthenticationService) {

    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.timetableService.headers.append('Authorization', user.token);
          this.getList();
        });
    } else {
      this.getList();
    }
  }

  ngOnInit(): void {

  }

  private getList(): void {
    this.timetableService.list().subscribe(
      list => this.list = list,
      error =>  this.errorMessage = <any>error
    );
  }

  remove(id: number): void {
    if(confirm("Are you sure you wish to delete this TimeTable?")) {
      this.timetableService.remove(id)
        .then(() => {
          this.getList();
        });
    }
  }

}
