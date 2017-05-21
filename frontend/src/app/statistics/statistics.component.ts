import {AfterViewInit, Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import {Router} from "@angular/router";
import '../rxjs-operators';
import {BaseChartDirective} from "ng2-charts";

@Component({
  selector: 'statistics',
  templateUrl: 'statistics.component.html',
  styleUrls: ['statistics.component.css'],
})
export class StatisticsComponent implements AfterViewInit{
  @ViewChildren(BaseChartDirective) charts: QueryList<BaseChartDirective>;

  title = 'Statistics';
  errorMessage: any;
  selectedHotelId: number = 0;
  // PolarArea
  polarAreaLegend:boolean = true;
  polarAreaChartType:string = 'pie';
  public lineChartOptions:any = {
    responsive: true
  };

  kilometersData = {
    labels: ["Siječanj", "Veljača", "Ožujak", "Travanj", "Svibanj"],
    datasets: [
      {
        label: "Prijeđeni kilometri",
        fill: false,
        lineTension: 0.1,
        backgroundColor: "rgba(75,192,192,0.4)",
        borderColor: "rgba(75,192,192,1)",
        borderCapStyle: 'butt',
        borderDash: [],
        borderDashOffset: 0.0,
        borderJoinStyle: 'miter',
        pointBorderColor: "rgba(75,192,192,1)",
        pointBackgroundColor: "#fff",
        pointBorderWidth: 1,
        pointHoverRadius: 5,
        pointHoverBackgroundColor: "rgba(75,192,192,1)",
        pointHoverBorderColor: "rgba(220,220,220,1)",
        pointHoverBorderWidth: 2,
        pointRadius: 1,
        pointHitRadius: 10,
        data: [65, 59, 80, 81, 56, 55, 40],
        spanGaps: false,
      }
      ]
  };

  actionsData = {
    labels: ["Siječanj", "Veljača", "Ožujak", "Travanj", "Svibanj"],
    datasets: [
      {
        label: "Uspješne akcije",
        fill: false,
        lineTension: 0.1,
        backgroundColor: "rgba(75,192,192,0.4)",
        borderColor: "rgba(75,192,192,1)",
        borderCapStyle: 'butt',
        borderDash: [],
        borderDashOffset: 0.0,
        borderJoinStyle: 'miter',
        pointBorderColor: "rgba(75,192,192,1)",
        pointBackgroundColor: "#fff",
        pointBorderWidth: 1,
        pointHoverRadius: 5,
        pointHoverBackgroundColor: "rgba(75,192,192,1)",
        pointHoverBorderColor: "rgba(220,220,220,1)",
        pointHoverBorderWidth: 2,
        pointRadius: 1,
        pointHitRadius: 10,
        data: [33, 15, 30, 81, 50, 10, 40],
        spanGaps: false,
      }
    ]
  };

  actionTypesData = {
    labels: ["Spašavanja", "Potrage", "Dežurstva", "Prevencije", "Tečajevi", "Vježbe", "Promidžba"],
    datasets: [
      {
        label: "Vrste akcija za trenutni mjesec",
        fill: false,
        lineTension: 0.1,
        backgroundColor: "rgb(244, 206, 66, 0.4)",
        borderColor: "rgba(75,192,192,1)",
        borderCapStyle: 'butt',
        borderDash: [],
        borderDashOffset: 0.0,
        borderJoinStyle: 'miter',
        pointBorderColor: "rgb(244, 206, 66, 1)",
        pointBackgroundColor: "#fff",
        pointBorderWidth: 1,
        pointHoverRadius: 5,
        pointHoverBackgroundColor: "rgb(244, 206, 66, 1)",
        pointHoverBorderColor: "rgb(244, 206, 66, 1)",
        pointHoverBorderWidth: 2,
        pointRadius: 1,
        pointHitRadius: 10,
        data: [23, 12, 50, 2, 11, 5, 57, 5],
        spanGaps: false,
      }
    ]
  };

  constructor(
    private router: Router
  ) {
  }

  ngAfterViewInit(): void {
    this.updateChart();
  }

  private updateChart(){
    console.debug(this.charts);
    for(let chart of this.charts.toArray()) {
      chart.ngOnChanges({});
    }
  }

  // events
  chartClicked(e:any):void {
    //console.log(e);
  }

  chartHovered(e:any):void {
    //console.log(e);
  }

  goHome(): void {
    this.router.navigate(['/home']);
  }

}
