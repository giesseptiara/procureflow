import { Component, OnInit, signal } from '@angular/core';
import { Api } from '../../core/services/api';

@Component({
  selector: 'app-departments',
  imports: [],
  templateUrl: './departments.html',
  styleUrl: './departments.css',
})
export class Departments implements OnInit {

  departments = signal<any[]>([]);

  constructor(private api: Api) {}

  ngOnInit() {
    this.api.getDepartments().subscribe({
      next: (data: any) => {
        console.log('Departments Page DATA:', data);
        this.departments.set(data);
      },
      error: (error) => {
        console.error('Departments Page API Error:', error);
      }
    });
  }
}