import { Component, ViewChild } from '@angular/core';
import { ChartComponent, ApexOptions } from 'ng-apexcharts';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrl: './pie-chart.component.scss'
})
export class PieChartComponent {
  @ViewChild("chart") chart!: ChartComponent;
  public chartOptions: Partial<ApexOptions>;

  constructor() {
    this.chartOptions = {
      series: [44, 55],
      chart: {
        type: "donut",
        width: 420,
        offsetX: 100,
        offsetY:0
      },


      labels: ["Students", "Proffessors"],
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: 200
            },
            legend: {
              position: "bottom"
            }
          }
        }
      ]
    };
  }
}
