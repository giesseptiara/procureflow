import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { Api } from '../../core/services/api';

@Component({
  selector: 'app-vendors',
  imports: [FormsModule],
  templateUrl: './vendors.html',
  styleUrl: './vendors.css',
})
export class Vendors implements OnInit {

  vendors = signal<any[]>([]);
  showForm = signal(false);

  formData = {
    name: '',
    email: '',
    phone: '',
    address: ''
  };

  constructor(private api: Api) {}

  openForm() {
    this.showForm.set(true);
  }

  closeForm() {
    this.showForm.set(false);
  }

  createVendor() {
    this.api.createVendor(this.formData).subscribe({
      next: (data) => {
        console.log('Vendor Created:', data);

        this.closeForm();

        this.formData = {
          name: '',
          email: '',
          phone: '',
          address: ''
        };

        this.api.getVendors().subscribe({
          next: (data: any) => {
            this.vendors.set(data);
          }
        });
      },

      error: (error) => {
        console.error('Create Vendor Error:', error);

        alert(
          error.error?.message ||
          'Failed to create vendor'
        );
      }
    });
  }

  ngOnInit() {
    this.api.getVendors().subscribe({
      next: (data: any) => {
        console.log('Vendors Page DATA:', data);
        this.vendors.set(data);
      },

      error: (error) => {
        console.error('Vendors Page API Error:', error);
      }
    });
  }
}