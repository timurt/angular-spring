import { Component, OnInit } from '@angular/core';

import { Restaurant } from '../../models/restaurant';
import { RestaurantService } from '../../services/restaurant/restaurant.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  restaurants: Restaurant[] = [];

  constructor(private restaurantService: RestaurantService) { }

  ngOnInit() {
    this.getRestaurants();
  }

  getRestaurants(): void {
    this.restaurantService.getRestaurants(0, 4, {}, 'dateDesc')
      .subscribe(data => this.restaurants = data.content);
  }
}
