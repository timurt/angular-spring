import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { } from '@types/googlemaps';

import { Apartment } from '../../models/apartment';
import { ApartmentService } from '../../services/apartment/apartment.service';
import { GeocodingService } from '../../services/geocoding/geocoding.service';
import { AuthService } from '../../services/auth/auth.service';
import { User } from '../../models/user';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-apartment-detail',
  templateUrl: './apartment-detail.component.html',
  styleUrls: ['./apartment-detail.component.css']
})
export class ApartmentDetailComponent implements OnInit {

  @ViewChild('gmap') gmapElement: any;
  @ViewChild('nameInput') input: ElementRef;
  @Input() apartment: Apartment;
  editable = false;
  isNew = false;
  error: boolean;
  map: google.maps.Map;
  marker: google.maps.Marker;
  address = '';
  realtors: User[];

  constructor(
    private route: ActivatedRoute,
    private apartmentService: ApartmentService,
    private geocodingService: GeocodingService,
    private location: Location,
    private auth: AuthService,
    private userService: UserService) { }

  ngOnInit() {
    this.getApartment();
    this.mapInitGeo();

    if (this.auth.roleMatch(['ADMIN'])) {
      this.userService.getRealtors()
      .subscribe(realtors => {
        this.realtors = realtors;
      });
    }
  }

  modelChanged(newObj) {
    this.mapSetMarker();
  }

  mapSetMarker(): void {
    const center = {lat: +this.apartment.latitude, lng: +this.apartment.longitude};
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
        parent.apartment.longitude = e.latLng.lng();
        parent.apartment.latitude = e.latLng.lat();
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

  getApartment(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    if (id) {
      this.apartmentService.getApartment(id)
      .subscribe(apartment => {
        this.apartment = apartment;
        this.mapSetMarker();
      });
    } else {
      this.isNew = true;
      this.apartment = new Apartment();
    }
  }

  saveApartment() {
    this.apartmentService.updateApartment(this.apartment)
    .subscribe(data => {
      if (data.error) {
        this.error = data.message;
      } else {
        this.goBack();
      }
    });
  }

  setFree(): void {
    if (this.apartment.isRented) {
      this.apartmentService.freeApartment(this.apartment)
      .subscribe(() => alert('Apartment is available now'));
      this.apartment.isRented = false;
    }
  }

  setRented(): void {
    if (!this.apartment.isRented) {
      this.apartmentService.rentApartment(this.apartment)
      .subscribe(() => alert('Apartment is rented now'));
      this.apartment.isRented = true;
    }
  }

  deleteApartment() {
    if (confirm('Are you sure?')) {
      this.apartmentService.deleteApartment(this.apartment)
    .subscribe(() => this.goBack());
    } else {
      return;
    }
  }

  cancelEditing() {
    this.getApartment();
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
        parent.apartment.longitude = data.results[0].geometry.location.lng;
        parent.apartment.latitude = data.results[0].geometry.location.lat;
        parent.mapSetMarker();
      } else {
        alert('Not found');
      }
    });
  }
}
