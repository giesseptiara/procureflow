import { Component, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Api } from '../../core/services/api';

@Component({
  selector: 'app-deliveries',
  imports: [DatePipe],
  templateUrl: './deliveries.html',
  styleUrl: './deliveries.css',
})
export class Deliveries implements OnInit {

  deliveries = signal<any[]>([]);

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
  }
}