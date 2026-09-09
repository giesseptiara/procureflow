import { Component, OnInit, signal } from '@angular/core';
import { DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Api } from '../../core/services/api';

@Component({
  selector: 'app-purchase-orders',
  imports: [DatePipe, DecimalPipe, FormsModule],
  templateUrl: './purchase-orders.html',
  styleUrl: './purchase-orders.css',
})
export class PurchaseOrders implements OnInit {

  purchaseOrders = signal<any[]>([]);
  quotations = signal<any[]>([]);

  showForm = signal(false);

  formData = {
    quotationId: ''
  };

  constructor(private api: Api) {}

  openForm() {
    this.showForm.set(true);
  }

  closeForm() {
    this.showForm.set(false);
  }

  createPurchaseOrder() {
  this.api.createPurchaseOrder(
    Number(this.formData.quotationId)
  ).subscribe({
    next: (data) => {
      console.log('Purchase Order Created:', data);

      this.closeForm();

      this.formData = {
        quotationId: ''
      };

      this.api.getPurchaseOrders().subscribe({
        next: (data: any) => {
          this.purchaseOrders.set(data);
        }
      });
    },

    error: (error) => {
      console.error('Create Purchase Order Error:', error);
      alert(
        error.error?.message ||
        'Failed to create purchase order'
      );
    }
  });
}

issuePurchaseOrder(id: number) {
  this.api.issuePurchaseOrder(id).subscribe({
    next: (data) => {
      console.log('Purchase Order Issued:', data);

      this.api.getPurchaseOrders().subscribe({
        next: (data: any) => {
          this.purchaseOrders.set(data);
        }
      });
    },

    error: (error) => {
      console.error('Issue Purchase Order Error:', error);
      alert(
        error.error?.message ||
        'Failed to issue purchase order'
      );
    }
  });
}

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

    this.api.getQuotations().subscribe({
      next: (data: any) => {
        console.log('Quotations for Purchase Order:', data);
        this.quotations.set(data);
      },
      error: (error) => {
        console.error('Quotations API Error:', error);
      }
    });

  }
}