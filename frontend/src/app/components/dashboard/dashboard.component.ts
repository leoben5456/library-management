import { Component, inject } from '@angular/core';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import { map } from 'rxjs/operators';
import { PieChartComponent } from '../pie-chart/pie-chart.component';
import { RadarChartComponent } from '../radar-chart/radar-chart.component';
import { BarChartComponent } from '../bar-chart/bar-chart.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
  private breakpointObserver = inject(BreakpointObserver);

  /** Based on the screen size, switch from standard to one column per row */
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({ matches }) => {
      if (matches) {
        return [
          { component:PieChartComponent,title: 'User Breakdown', cols: 1, rows: 1 },
          { component:RadarChartComponent,title: 'Borrow & Back', cols: 1, rows: 1 },
          { component:BarChartComponent,title: 'Penalties Overview', cols: 1, rows: 1 },
          { component:PieChartComponent,title: 'Card 4', cols: 1, rows: 1 }
        ];
      }

      return [
        { component:PieChartComponent,title: 'User Breakdown', cols: 1, rows: 1 },
        { component:RadarChartComponent,title: 'Borrow & Back', cols: 1, rows: 1 },
        { component:BarChartComponent,title: 'Penalties Overview', cols: 1, rows: 1 },
        { component:PieChartComponent,title: 'Card 4', cols: 1, rows: 1 }
      ];
    })
  );


  KPIScards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({ matches }) => {
      if (matches) {
        return [
          { title: '100', content:'Total Users',  taux: '+10%', isUp: true, cols: 1, rows: 1 },
          { title: '80',content:'Books Borrowed ', taux: '-5%', isUp: false, cols: 1, rows: 1 },
          { title: '150', content:'Penalties',taux: '-15%', isUp: false, cols: 1, rows: 1 },
          { title: '200',content:'Overdue Books', taux: '+20%', isUp: true, cols: 1, rows: 1 }
        ];
      }

      return [
        { title: '100',content:'Total Users', taux: '+10%', isUp: true, cols: 1, rows: 1 },
        { title: '80',content:'Books Borrowed', taux: '-5%', isUp: false, cols: 1, rows: 1 },
        { title: '150',content:'Penalties This month', taux: '-15%', isUp: false, cols: 1, rows: 1 },
        { title: '200',content:'Overdue Books', taux: '+20%', isUp: true, cols: 1, rows: 1 }
      ];
    })
  );
}
