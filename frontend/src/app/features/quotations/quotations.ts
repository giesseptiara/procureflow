import { Component, OnInit, signal } from '@angular/core';
import { DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Api } from '../../core/services/api';

@Component({
  selector: 'app-quotations',
  imports: [DatePipe, DecimalPipe, FormsModule],
  templateUrl: './quotations.html',
  styleUrl: './quotations.css',
})
export class Quotations implements OnInit {

  quotations = signal<any[]>([]);
  purchaseRequests = signal<any[]>([]);
  vendors = signal<any[]>([]);

  showForm = signal(false);

  formData = {
    purchaseRequestId: '',
    vendorId: '',
    offeredPrice: null,
    notes: ''
  };

  constructor(private api: Api) {}

  openForm() {
    this.showForm.set(true);
  }

  closeForm() {
    this.showForm.set(false);
  }

  ngOnInit() {

    this.api.getQuotations().subscribe({
      next: (data: any) => {
        console.log('Quotations Page DATA:', data);
        this.quotations.set(data);
      },
      error: (error) => {
        console.error('Quotations Page API Error:', error);
      }
    });

    this.api.getPurchaseRequests().subscribe({
      next: (data: any) => {
        console.log('Purchase Requests for Quotation:', data);
        this.purchaseRequests.set(data);
      },
      error: (error) => {
        console.error('Purchase Requests API Error:', error);
      }
    });

    this.api.getVendors().subscribe({
      next: (data: any) => {
        console.log('Vendors for Quotation:', data);
        this.vendors.set(data);
      },
      error: (error) => {
        console.error('Vendors API Error:', error);
      }
    });

  }

}