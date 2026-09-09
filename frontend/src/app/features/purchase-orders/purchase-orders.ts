import { Component, OnInit, signal } from '@angular/core';
import { DatePipe, DecimalPipe } from '@angular/common';
import { Api } from '../../core/services/api';

@Component({
  selector: 'app-purchase-orders',
  imports: [DatePipe, DecimalPipe],
  templateUrl: './purchase-orders.html',
  styleUrl: './purchase-orders.css',
})
export class PurchaseOrders implements OnInit {

  purchaseOrders = signal<any[]>([]);

  constructor(private api: Api) {}

  ngOnInit() {
    this.api.getPurchaseOrders().subscribe({
      next: (data: any) => {
        console.log('Purchase Orders Page DATA:', data);
        this.purchaseOrders.set(data);
      },
      error: (error) => {
        console.error('Purchase Orders Page API Error:', error);
      }
    });
  }
}