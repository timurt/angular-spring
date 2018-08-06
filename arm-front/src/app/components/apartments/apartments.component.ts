import { Component, OnInit, ViewChild } from '@angular/core';

import { Apartment } from '../../models/apartment';
import { Pagination } from '../../models/pagination';
import { ApartmentService } from '../../services/apartment/apartment.service';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-apartments',
  templateUrl: './apartments.component.html',
  styleUrls: ['./apartments.component.css']
})
export class ApartmentsComponent implements OnInit {
  @ViewChild('gmap') gmapElement: any;

  apartments: Apartment[];
  pagination: Pagination = new Pagination();
  filters = {};
  map: google.maps.Map;
  markers: google.maps.Marker[] = [];

  constructor(private apartmentService: ApartmentService, private auth: AuthService) {}

  ngOnInit() {
    this.mapInitGeo();
    this.getApartments();
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

  getApartments(): void {
    this.apartmentService.getApartments(
      this.pagination.currentPage - 1,
      this.pagination.size,
      this.filters)
      .subscribe(data => {
        console.log(data);
        this.apartments = data.content;
        this.constructPagination(data);
        this.setMarkers();
      });
  }

  onSizeSelect(size: number): void {
    this.pagination.size = size;
    this.getApartments();
  }

  onPageSelect(page: number): void {
    this.pagination.currentPage = page;
    this.getApartments();
  }

  onPrevPage(): void {
    const min = this.pagination.currentPage - (this.pagination.currentPage - 1) % 5;
    if (min - 1 > 0) {
      this.pagination.currentPage = min - 1;
      this.getApartments();
    }
  }

  onNextPage(): void {
    const min = this.pagination.currentPage - (this.pagination.currentPage - 1) % 5;
    const max = min + 5;
    if (min <= this.pagination.totalPages) {
      this.pagination.currentPage = max;
      this.getApartments();
    }
  }

  onFiltersApply(): void {
    this.getApartments();
  }

  onFiltersReset(): void {
    this.filters = {};
    this.getApartments();
  }

  setRented(isRented: boolean): void {
    this.filters['isRented'] = isRented;
  }

  deleteRented(): void {
    delete  this.filters['isRented'];
  }

  setMarkers(): void {
    for (const marker of this.markers) {
      marker.setMap(null);
    }
    this.markers = [];
    for (const apartment of this.apartments) {
      const center = {lat: +apartment.latitude, lng: +apartment.longitude};
      const marker = new google.maps.Marker(
        {
        position: center,
        map: this.map,
        title: apartment.name
      });

      marker['json'] = {
        name : apartment.name,
        description : apartment.description,
        pricePerMonth : apartment.pricePerMonth,
        numberOfRooms : apartment.numberOfRooms,
        floorAreaSize : apartment.floorAreaSize,
        id : apartment.id
      };
      marker.addListener('click', function() {
        const contentString = '<div id="content">' +
        '<div id="siteNotice">' +
        '</div>' +
        `<h1 id="firstHeading" class="firstHeading">${marker['json'].name}</h1>` +
        '<div id="bodyContent">' +
        `<p>${marker['json'].description}</p>` +
        `<p>Number of rooms : ${marker['json'].numberOfRooms}</p>` +
        `<p>Price per month : ${marker['json'].pricePerMonth} $ </p>` +
        `<p>Floor area size : ${marker['json'].floorAreaSize} m<sup>2</sup></p>` +
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
