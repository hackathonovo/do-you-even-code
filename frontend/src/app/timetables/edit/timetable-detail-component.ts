import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';
import '../../rxjs-operators';
import { TimeTableService } from '../../services/timetable.service';
import {TimeTable} from "../../models/timetable";
import {AuthenticationService} from "../../services/authentication.service";
import {user} from "../../session";

@Component({
  moduleId: module.id,
  selector: 'my-timetable-detail',
  templateUrl: 'timetable-detail.component.html',
  styleUrls: ['timetable-detail.component.css']
})
export class TimeTableDetailComponent implements OnInit {
  @Input()
  model: TimeTable;

  constructor(
    private timetableService: TimeTableService,
    private route: ActivatedRoute,
    private location: Location,
    private authService: AuthenticationService) {}

  ngOnInit(): void {
    if(!user.token) {
      this.authService.handshake()
        .subscribe(auth => {
          user.authenticate(auth);
          this.timetableService.headers.append('Authorization', user.token);
          this.getEntity();
        });
    } else {
      this.getEntity();
    }
  }

  private getEntity(): void {
    this.route.params
      .switchMap((params: Params) => this.timetableService.get(+params['id']))
      .subscribe(timetable => this.model = timetable as TimeTable);
  }

  save(): void {
    this.timetableService.update(this.model)
      .then(() => this.goBack());
  }

  goBack(): void {
    this.location.back();
  }
}
