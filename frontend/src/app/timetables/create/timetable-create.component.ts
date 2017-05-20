import { Component, OnInit } from '@angular/core';
import { Location }                 from '@angular/common';
import '../../rxjs-operators';
import { TimeTableService } from '../../services/timetable.service';
import {TimeTable} from "../../models/timetable";
import {AuthenticationService} from "../../services/authentication.service";
import {user} from "app/session";

@Component({
  moduleId: module.id,
  selector: 'my-timetable-create',
  templateUrl: 'timetable-create.component.html',
  styleUrls: ['timetable-create.component.css']
})
export class TimeTableCreateComponent implements OnInit {
  private model: TimeTable;

  constructor(
    private timetableService: TimeTableService,
    private location: Location,
    private authService: AuthenticationService) {
    this.model = new TimeTable();
  }

  ngOnInit(): void {
    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.timetableService.headers.append('Authorization', user.token);
        });
    }

    this.model = new TimeTable();
  }

  save(): void {
    this.timetableService.create(this.model)
      .then(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
