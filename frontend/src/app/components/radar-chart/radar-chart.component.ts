import { Component, ViewChild } from '@angular/core';
import { ApexOptions, ChartComponent } from 'ng-apexcharts';

@Component({
  selector: 'app-radar-chart',
  templateUrl: './radar-chart.component.html',
  styleUrl: './radar-chart.component.scss'
})
export class RadarChartComponent {
  @ViewChild("chart") chart!: ChartComponent;
  public chartOptions: Partial<ApexOptions>;

  constructor() {
    this.chartOptions = {
      series: [
        {
          name: "Series 1",
          data: [20, 100, 40, 30, 120, 80, 33]
        }
      ],
      chart: {
        height: 350,
        type: "radar",
        offsetY:-45
      },
      dataLabels: {
        enabled: true
      },
      plotOptions: {
        radar: {
          size: 140,
          polygons: {
            strokeColors: "#e9e9e9",
            fill: {
              colors: ["#f8f8f8", "#fff"]
            }
          }
        }
      },
      
      colors: ["#FF4560"],
      markers: {
        size: 4,
        colors: ["#fff"],
        strokeColors: ["#FF4560"],
        strokeWidth: 2
      },
      tooltip: {
        y: {
          formatter: function(val) {
            return val.toString();
          }
        }
      },
      xaxis: {
        categories: [
          "Sunday",
          "Monday",
          "Tuesday",
          "Wednesday",
          "Thursday",
          "Friday",
          "Saturday"
        ]
      },
      yaxis: {
        tickAmount: 7,
        labels: {
          formatter: function(val, i) {
            if (i % 2 === 0) {
              return val.toString();
            } else {
              return "";
            }
          }
        }
      }
    };
  }
}
