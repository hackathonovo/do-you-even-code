import { Component, OnInit } from '@angular/core';
import { Location }                 from '@angular/common';
import '../../rxjs-operators';
import { TimeTableService } from '../../services/timetable.service';
import {TimeTable} from "../../models/timetable";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {UserService} from "app/services/user.service";
import {User} from "../../models/user";

@Component({
  moduleId: module.id,
  selector: 'my-timetable-create',
  templateUrl: 'timetable-create.component.html',
  styleUrls: ['timetable-create.component.css']
})
export class TimeTableCreateComponent {
  private model: TimeTable;
  user: User;

  constructor(
    private timetableService: TimeTableService,
    private location: Location,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router) {
    this.model = new TimeTable();

    this.route.params
      .switchMap((params: Params) => this.userService.get(+params['userId']))
      .subscribe(user => {
        this.user = user as User;
        this.model.user_id = this.user.id;
      });
  }

  save(): void {
    console.debug(this.model);

    if(this.model.from && this.model.to) {
      this.model.from = this.formatLocalDate(this.model.from);
      this.model.to = this.formatLocalDate(this.model.to);

      this.timetableService.create(this.model)
        .then(() => this.router.navigateByUrl("/timetables"));
    }
  }

  private formatLocalDate(date): string {
    let now = new Date(date),
      tzo = -now.getTimezoneOffset(),
      dif = tzo >= 0 ? '+' : '-',
      pad = function(num) {
        var norm = Math.abs(Math.floor(num));
        return (norm < 10 ? '0' : '') + norm;
      };
    return now.getFullYear()
      + '-' + pad(now.getMonth()+1)
      + '-' + pad(now.getDate())
      + 'T' + pad(now.getHours())
      + ':' + pad(now.getMinutes())
      + ':' + pad(now.getSeconds())
      + dif + pad(tzo / 60)
      + ':' + pad(tzo % 60);
  }

  goBack(): void {
    this.location.back();
  }
}
