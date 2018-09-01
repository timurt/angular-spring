import { Component, OnInit, ViewChild } from '@angular/core';

import { Restaurant } from '../../models/restaurant';
import { Pagination } from '../../models/pagination';
import { RestaurantService } from '../../services/restaurant/restaurant.service';
import { AuthService } from '../../services/auth/auth.service';
import { Filter } from '../../models/filter';

@Component({
  selector: 'app-restaurants',
  templateUrl: './restaurants.component.html',
  styleUrls: ['./restaurants.component.css']
})
export class RestaurantsComponent implements OnInit {
  @ViewChild('gmap') gmapElement: any;

  restaurants: Restaurant[];
  pagination: Pagination = new Pagination();
  filters: Filter = new Filter();
  sortBy = 'dateDesc';
  map: google.maps.Map;
  markers: google.maps.Marker[] = [];

  constructor(private restaurantService: RestaurantService, public auth: AuthService) {}

  ngOnInit() {
    this.mapInitGeo();
    this.getRestaurants();
  }

  constructPagination(data): void {
    this.pagination.currentPage = (+data.number) + 1;
    this.pagination.totalPages = +data.totalPages;
    this.pagination.size = +data.size;

    this.pagination.range = [];
    const min = this.pagination.currentPage - (this.pagination.currentPage - 1) % 5;
    this.pagination.hasPrev = min > 1;
    this.pagination.hasNext = (min + 4) < this.pagination.totalPages;
    for (let index = min; index <= Math.min(min + 4, this.pagination.totalPages); index ++) {
      this.pagination.range.push(index);
    }
  }

  getRestaurants(): void {
    this.restaurantService.getRestaurants(
      this.pagination.currentPage - 1,
      this.pagination.size,
      this.filters,
      this.sortBy)
      .subscribe(data => {
        console.log(data);
        this.restaurants = data.content;
        this.constructPagination(data);
        this.setMarkers();
      });
  }

  onSizeSelect(size: number): void {
    this.pagination.size = size;
    this.getRestaurants();
  }

  onSortSelect(sortBy: string): void {
    this.sortBy = sortBy;
    this.getRestaurants();
  }

  onPageSelect(page: number): void {
    this.pagination.currentPage = page;
    this.getRestaurants();
  }

  onPrevPage(): void {
    const min = this.pagination.currentPage - (this.pagination.currentPage - 1) % 5;
    if (min - 1 > 0) {
      this.pagination.currentPage = min - 1;
      this.getRestaurants();
    }
  }

  onNextPage(): void {
    const min = this.pagination.currentPage - (this.pagination.currentPage - 1) % 5;
    const max = min + 5;
    if (min <= this.pagination.totalPages) {
      this.pagination.currentPage = max;
      this.getRestaurants();
    }
  }

  onFiltersApply(): void {
    this.getRestaurants();
  }

  onFiltersReset(): void {
    this.filters = new Filter();
    this.getRestaurants();
  }

  setWifi(hasWifi: boolean): void {
    this.filters['hasWifi'] = hasWifi;
  }

  deleteWifi(): void {
    delete  this.filters['hasWifi'];
  }

  setMarkers(): void {
    for (const marker of this.markers) {
      marker.setMap(null);
    }
    this.markers = [];
    for (const restaurant of this.restaurants) {
      const center = {lat: +restaurant.latitude, lng: +restaurant.longitude};
      const marker = new google.maps.Marker(
        {
        position: center,
        map: this.map,
        title: restaurant.name
      });

      marker['json'] = {
        name : restaurant.name,
        description : restaurant.description,
        averageCheck : restaurant.averageCheck,
        numberOfSeats : restaurant.numberOfSeats,
        hasWifi : restaurant.hasWifi,
        id : restaurant.id
      };
      marker.addListener('click', function() {
        const contentString = '<div id="content">' +
        '<div id="siteNotice">' +
        '</div>' +
        `<h1 id="firstHeading" class="firstHeading">${marker['json'].name}</h1>` +
        '<div id="bodyContent">' +
        `<p>${marker['json'].description}</p>` +
        `<p>Average paycheck : ${marker['json'].averageCheck} $</p>` +
        `<p>Number of seats : ${marker['json'].numberOfSeats} seats </p>` +
        `<a href="/detail/${marker['json'].id}">More</a>` +
        '</div>' +
        '</div>';
        const infowindow = new google.maps.InfoWindow({
          content: contentString
        });
        infowindow.open(this.map, marker);
      });
      this.markers.push(marker);
    }
  }

  mapInitGeo(): void {
    this.map = new google.maps.Map(this.gmapElement.nativeElement, {
      center: {lat: -34.397, lng: 150.644},
      zoom: 6
    });

    const parent = this;
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position) {
        const pos = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
        parent.map.setCenter(pos);
      }, function() {
      });
    }
  }

}
