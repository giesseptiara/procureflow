import { Component, OnInit } from '@angular/core';
import { Api } from '../../core/services/api';

@Component({
  selector: 'app-dashboard',
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {

  purchaseRequests: any[] = [];
  quotations: any[] = [];
  purchaseOrders: any[] = [];
  deliveries: any[] = [];

  constructor(private api: Api) {}

  ngOnInit() {
    this.api.getPurchaseRequests().subscribe({
      next: (data: any) => {
        this.purchaseRequests = data;
      },
      error: (error) => {
        console.error('Purchase Requests API Error:', error);
      }
    });

    this.api.getQuotations().subscribe({
      next: (data: any) => {
        this.quotations = data;
      },
      error: (error) => {
        console.error('Quotations API Error:', error);
      }
    });

    this.api.getPurchaseOrders().subscribe({
      next: (data: any) => {
        this.purchaseOrders = data;
      },
      error: (error) => {
        console.error('Purchase Orders API Error:', error);
      }
    });

    this.api.getDeliveries().subscribe({
      next: (data: any) => {
        this.deliveries = data;
      },
      error: (error) => {
        console.error('Deliveries API Error:', error);
      }
    });
  }

}