import { Component, OnInit, signal } from '@angular/core';
import { Api } from '../../core/services/api';

@Component({
  selector: 'app-dashboard',
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {

  purchaseRequests = signal<any[]>([]);
  quotations = signal<any[]>([]);
  purchaseOrders = signal<any[]>([]);
  deliveries = signal<any[]>([]);

  constructor(private api: Api) {}

  ngOnInit() {

    this.api.getPurchaseRequests().subscribe({
      next: (data: any) => {
        console.log('Purchase Requests DATA:', data);
        this.purchaseRequests.set(data);
      },
      error: (error) => {
        console.error('Purchase Requests API Error:', error);
      }
    });

    this.api.getQuotations().subscribe({
      next: (data: any) => {
        console.log('Quotations DATA:', data);
        this.quotations.set(data);
      },
      error: (error) => {
        console.error('Quotations API Error:', error);
      }
    });

    this.api.getPurchaseOrders().subscribe({
      next: (data: any) => {
        console.log('Purchase Orders DATA:', data);
        this.purchaseOrders.set(data);
      },
      error: (error) => {
        console.error('Purchase Orders API Error:', error);
      }
    });

    this.api.getDeliveries().subscribe({
      next: (data: any) => {
        console.log('Deliveries DATA:', data);
        this.deliveries.set(data);
      },
      error: (error) => {
        console.error('Deliveries API Error:', error);
      }
    });

  }
}