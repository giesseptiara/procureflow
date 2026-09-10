import { Component, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Api } from '../../core/services/api';

@Component({
  selector: 'app-deliveries',
  imports: [DatePipe, FormsModule],
  templateUrl: './deliveries.html',
  styleUrl: './deliveries.css',
})
export class Deliveries implements OnInit {

  deliveries = signal<any[]>([]);
  purchaseOrders = signal<any[]>([]);

  showForm = signal(false);

  formData = {
  purchaseOrderId: '',
  receivedQuantity: null,
  receivedDate: '',
  condition: '',
  notes: ''
};

recordDelivery() {
  this.api.createDelivery({
    purchaseOrderId: Number(this.formData.purchaseOrderId),
    receivedQuantity: Number(this.formData.receivedQuantity),
    receivedDate: this.formData.receivedDate,
    condition: this.formData.condition,
    notes: this.formData.notes
  }).subscribe({
    next: (data) => {
      console.log('Delivery Created:', data);

      this.closeForm();

      this.formData = {
        purchaseOrderId: '',
        receivedQuantity: null,
        receivedDate: '',
        condition: '',
        notes: ''
      };

      this.api.getDeliveries().subscribe({
        next: (data: any) => {
          this.deliveries.set(data);
        }
      });

      this.api.getPurchaseOrders().subscribe({
        next: (data: any) => {
          this.purchaseOrders.set(data);
        }
      });
    },

    error: (error) => {
      console.error('Create Delivery Error:', error);
      alert(
        error.error?.message ||
        'Failed to record delivery'
      );
    }
  });
}

  openForm() {
    this.showForm.set(true);
  }

  closeForm() {
    this.showForm.set(false);
  }

  constructor(private api: Api) {}

  ngOnInit() {
    this.api.getDeliveries().subscribe({
      next: (data: any) => {
        console.log('Deliveries Page DATA:', data);
        this.deliveries.set(data);
      },
      error: (error) => {
        console.error('Deliveries Page API Error:', error);
      }
    });

    this.api.getPurchaseOrders().subscribe({
  next: (data: any) => {
    console.log('Purchase Orders for Delivery:', data);
    this.purchaseOrders.set(data);
  },
  error: (error) => {
    console.error('Purchase Orders API Error:', error);
  }
});
  }
}