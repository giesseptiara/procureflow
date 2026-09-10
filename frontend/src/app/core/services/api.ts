import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class Api {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getDepartments() {
    return this.http.get(`${this.baseUrl}/departments`);
  }

  createDepartment(data: any) {
    return this.http.post(`${this.baseUrl}/departments`, data);
  }

  getPurchaseRequests() {
    return this.http.get(`${this.baseUrl}/purchase-requests`);
  }

  createPurchaseRequest(data: any) {
    return this.http.post(`${this.baseUrl}/purchase-requests`, data);
  }

  approvePurchaseRequest(id: number, approved: boolean, rejectionReason?: string) {
    return this.http.put(`${this.baseUrl}/purchase-requests/${id}/approval`, {
      approved,
      rejectionReason
    });
  }

  rejectPurchaseRequest(id: number, rejectionReason: string) {
    return this.http.put(`${this.baseUrl}/purchase-requests/${id}/approval`, {
      approved: false,
      rejectionReason
    });
  }

  getQuotations() {
    return this.http.get(`${this.baseUrl}/quotations`);
  }

  createQuotation(data: any) {
    return this.http.post(`${this.baseUrl}/quotations`, data);
  }

  selectQuotation(id: number) {
    return this.http.put(`${this.baseUrl}/quotations/${id}/select`, {});
  }

  getPurchaseOrders() {
    return this.http.get(`${this.baseUrl}/purchase-orders`);
  }

  createPurchaseOrder(quotationId: number) {
    return this.http.post(
      `${this.baseUrl}/purchase-orders?quotationId=${quotationId}`,
      {}
    );
  }

  issuePurchaseOrder(id: number) {
    return this.http.put(
      `${this.baseUrl}/purchase-orders/${id}/issue`,
      {}
    );
  }

  completePurchaseOrder(id: number) {
    return this.http.put(
      `${this.baseUrl}/purchase-orders/${id}/complete`,
      {}
    );
  }

  getDeliveries() {
    return this.http.get(`${this.baseUrl}/deliveries`);
  }

  createDelivery(data: any) {
    return this.http.post(
      `${this.baseUrl}/deliveries`,
      data
    );
  }

  getVendors() {
    return this.http.get(`${this.baseUrl}/vendors`);
  }

  createVendor(data: any) {
    return this.http.post(`${this.baseUrl}/vendors`, data);
  }

}