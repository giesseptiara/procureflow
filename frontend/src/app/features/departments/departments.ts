import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { Api } from '../../core/services/api';

@Component({
  selector: 'app-departments',
  imports: [FormsModule],
  templateUrl: './departments.html',
  styleUrl: './departments.css',
})
export class Departments implements OnInit {

  departments = signal<any[]>([]);
  showForm = signal(false);

  formData = {
    name: ''
  };

  constructor(private api: Api) {}

  openForm() {
    this.showForm.set(true);
  }

  closeForm() {
    this.showForm.set(false);
  }

  createDepartment() {
    this.api.createDepartment(this.formData).subscribe({
      next: (data) => {
        console.log('Department Created:', data);

        this.closeForm();

        this.formData = {
          name: ''
        };

        this.api.getDepartments().subscribe({
          next: (data: any) => {
            this.departments.set(data);
          }
        });
      },

      error: (error) => {
        console.error('Create Department Error:', error);

        alert(
          error.error?.message ||
          'Failed to create department'
        );
      }
    });
  }

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