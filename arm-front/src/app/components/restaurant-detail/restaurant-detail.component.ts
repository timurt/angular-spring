import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { } from '@types/googlemaps';

import { Restaurant } from '../../models/restaurant';
import { RestaurantService } from '../../services/restaurant/restaurant.service';
import { GeocodingService } from '../../services/geocoding/geocoding.service';
import { AuthService } from '../../services/auth/auth.service';
import { User } from '../../models/user';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-restaurant-detail',
  templateUrl: './restaurant-detail.component.html',
  styleUrls: ['./restaurant-detail.component.css']
})
export class RestaurantDetailComponent implements OnInit {

  @ViewChild('gmap') gmapElement: any;
  @ViewChild('nameInput') input: ElementRef;
  @Input() restaurant: Restaurant;
  editable = false;
  isNew = false;
  error: boolean;
  map: google.maps.Map;
  marker: google.maps.Marker;
  address = '';
  owners: User[];

  constructor(
    private route: ActivatedRoute,
    private restaurantService: RestaurantService,
    private geocodingService: GeocodingService,
    private location: Location,
    private auth: AuthService,
    private userService: UserService) { }

  ngOnInit() {
    this.getRestaurant();
    this.mapInitGeo();

    if (this.auth.roleMatch(['ADMIN'])) {
      this.userService.getOwners()
      .subscribe(owners => {
        this.owners = owners;
      });
    }
  }

  modelChanged(newObj) {
    this.mapSetMarker();
  }

  mapSetMarker(): void {
    const center = {lat: +this.restaurant.latitude, lng: +this.restaurant.longitude};
    this.map.setCenter(center);
    if (this.marker) {
      this.marker.setMap(null);
    }
    this.marker = new google.maps.Marker({position: center, map: this.map});
  }

  mapInitGeo(): void {
    this.map = new google.maps.Map(this.gmapElement.nativeElement, {
      center: {lat: -34.397, lng: 150.644},
      zoom: 6
    });

    const parent = this;
    this.map.addListener('click', function(e) {
      if (parent.editable === true || parent.isNew === true) {
        parent.restaurant.longitude = e.latLng.lng();
        parent.restaurant.latitude = e.latLng.lat();
        parent.mapSetMarker();
        parent.input.nativeElement.focus();
        parent.input.nativeElement.blur();
      }
    });

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position) {
        const pos = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
        if (!parent.marker) {
          parent.map.setCenter(pos);
        }
      }, function() {
      });
    }
  }

  getRestaurant(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    if (id) {
      this.restaurantService.getRestaurant(id)
      .subscribe(restaurant => {
        this.restaurant = restaurant;
        this.mapSetMarker();
      });
    } else {
      this.isNew = true;
      this.restaurant = new Restaurant();
    }
  }

  saveRestaurant() {
    this.restaurantService.updateRestaurant(this.restaurant)
    .subscribe(data => {
      if (data.error) {
        this.error = data.message;
      } else {
        this.goBack();
      }
    });
  }

  deleteRestaurant() {
    if (confirm('Are you sure?')) {
      this.restaurantService.deleteRestaurant(this.restaurant)
    .subscribe(() => this.goBack());
    } else {
      return;
    }
  }

  cancelEditing() {
    this.getRestaurant();
    this.editable = false;
  }

  goBack(): void {
    this.location.back();
  }

  showInputs(): boolean {
    return this.editable === true || this.isNew === true;
  }

  getCoordinates(): void {
    const parent = this;
    this.geocodingService.getCoordinates(this.address)
    .subscribe(data => {
      if (data.status === 'OK' && data.results) {
        parent.address = data.results[0].formatted_address;
        parent.restaurant.longitude = data.results[0].geometry.location.lng;
        parent.restaurant.latitude = data.results[0].geometry.location.lat;
        parent.mapSetMarker();
      } else {
        alert('Not found');
      }
    });
  }
}
