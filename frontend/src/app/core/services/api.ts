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

}