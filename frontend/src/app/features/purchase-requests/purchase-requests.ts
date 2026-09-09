import { Component, OnInit, signal } from '@angular/core';
import { DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Api } from '../../core/services/api';

@Component({
  selector: 'app-purchase-requests',
  imports: [DatePipe, DecimalPipe, FormsModule],
  templateUrl: './purchase-requests.html',
  styleUrl: './purchase-requests.css',
})
export class PurchaseRequests implements OnInit {

  purchaseRequests = signal<any[]>([]);
  departments = signal<any[]>([]);
  showForm = signal(false);

  formData = {
    departmentId: '',
    itemName: '',
    quantity: null,
    estimatedCost: null,
    reason: ''
  };

  constructor(private api: Api) {}

  openForm() {
    this.showForm.set(true);
  }

  closeForm() {
    this.showForm.set(false);
  }

  createRequest() {
  this.api.createPurchaseRequest({
    departmentId: Number(this.formData.departmentId),
    itemName: this.formData.itemName,
    quantity: Number(this.formData.quantity),
    estimatedCost: Number(this.formData.estimatedCost),
    reason: this.formData.reason
  }).subscribe({
    next: (data) => {
      console.log('Purchase Request Created:', data);

      this.closeForm();

      this.formData = {
        departmentId: '',
        itemName: '',
        quantity: null,
        estimatedCost: null,
        reason: ''
      };

      this.api.getPurchaseRequests().subscribe({
        next: (data: any) => {
          this.purchaseRequests.set(data);
        }
      });
    },
    error: (error) => {
      console.error('Create Purchase Request Error:', error);
      alert(error.error?.message || 'Failed to create purchase request');
    }
  });
}

approveRequest(id: number) {
  this.api.approvePurchaseRequest(id, true).subscribe({
    next: (data) => {
      console.log('Purchase Request Approved:', data);

      this.api.getPurchaseRequests().subscribe({
        next: (data: any) => {
          this.purchaseRequests.set(data);
        }
      });
    },
    error: (error) => {
      console.error('Approve Purchase Request Error:', error);
      alert(error.error?.message || 'Failed to approve purchase request');
    }
  });
}

rejectRequest(id: number) {
  const reason = prompt('Masukkan alasan penolakan:');

  if (!reason || !reason.trim()) {
    return;
  }

  this.api.rejectPurchaseRequest(id, reason.trim()).subscribe({
    next: (data) => {
      console.log('Purchase Request Rejected:', data);

      this.api.getPurchaseRequests().subscribe({
        next: (data: any) => {
          this.purchaseRequests.set(data);
        }
      });
    },
    error: (error) => {
      console.error('Reject Purchase Request Error:', error);
      alert(error.error?.message || 'Failed to reject purchase request');
    }
  });
}

  ngOnInit() {

    this.api.getPurchaseRequests().subscribe({
      next: (data: any) => {
        console.log('Purchase Requests Page DATA:', data);
        this.purchaseRequests.set(data);
      },
      error: (error) => {
        console.error('Purchase Requests Page API Error:', error);
      }
    });

    this.api.getDepartments().subscribe({
      next: (data: any) => {
        console.log('Departments for Purchase Request:', data);
        this.departments.set(data);
      },
      error: (error) => {
        console.error('Departments API Error:', error);
      }
    });

  }

}