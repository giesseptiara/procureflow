import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Api } from './core/services/api';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  protected readonly title = signal('ProcureFlow');

  departments: any[] = [];

  constructor(private api: Api) {}

  ngOnInit() {
    this.api.getDepartments().subscribe({
      next: (data: any) => {
        console.log('Departments:', data);
        console.log('First department:', data[0]);
        this.departments = data;
      },
      error: (error) => {
        console.error('API Error:', error);
      }
    });
  }
}