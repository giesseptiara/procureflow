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

  createQuotation() {
  this.api.createQuotation({
    purchaseRequestId: Number(this.formData.purchaseRequestId),
    vendorId: Number(this.formData.vendorId),
    offeredPrice: Number(this.formData.offeredPrice),
    notes: this.formData.notes
  }).subscribe({
    next: (data) => {
      console.log('Quotation Created:', data);

      this.closeForm();

      this.formData = {
        purchaseRequestId: '',
        vendorId: '',
        offeredPrice: null,
        notes: ''
      };

      this.api.getQuotations().subscribe({
        next: (data: any) => {
          this.quotations.set(data);
        }
      });
    },

    error: (error) => {
      console.error('Create Quotation Error:', error);
      alert(error.error?.message || 'Failed to create quotation');
    }
  });
}

selectQuotation(id: number) {
  this.api.selectQuotation(id).subscribe({
    next: (data) => {
      console.log('Quotation Selected:', data);

      this.api.getQuotations().subscribe({
        next: (data: any) => {
          this.quotations.set(data);
        }
      });
    },

    error: (error) => {
      console.error('Select Quotation Error:', error);
      alert(error.error?.message || 'Failed to select quotation');
    }
  });
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