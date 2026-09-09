import { Component, OnInit, signal } from '@angular/core';
import { Api } from '../../core/services/api';

@Component({
  selector: 'app-vendors',
  imports: [],
  templateUrl: './vendors.html',
  styleUrl: './vendors.css',
})
export class Vendors implements OnInit {

  vendors = signal<any[]>([]);

  constructor(private api: Api) {}

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