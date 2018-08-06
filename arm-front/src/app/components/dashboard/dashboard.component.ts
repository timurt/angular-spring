import { Component, OnInit } from '@angular/core';

import { Apartment } from '../../models/apartment';
import { ApartmentService } from '../../services/apartment/apartment.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  apartments: Apartment[] = [];

  constructor(private apartmentService: ApartmentService) { }

  ngOnInit() {
    this.getApartments();
  }

  getApartments(): void {
    this.apartmentService.getApartments(0, 5, {})
      .subscribe(data => this.apartments = data.content);
  }
}
