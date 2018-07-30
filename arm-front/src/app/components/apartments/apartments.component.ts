import { Component, OnInit } from '@angular/core';
import { Apartment } from '../../models/apartment';
import { Pagination } from '../../models/pagination';
import { ApartmentService } from '../../services/apartment/apartment.service';

@Component({
  selector: 'app-apartments',
  templateUrl: './apartments.component.html',
  styleUrls: ['./apartments.component.css']
})
export class ApartmentsComponent implements OnInit {

  apartments: Apartment[];

  pagination : Pagination = new Pagination();

  filters = {}

  constructPagination(data) : void {
    this.pagination.currentPage = (+data.number) + 1;
    this.pagination.totalPages = +data.totalPages;
    this.pagination.size = +data.size;

    this.pagination.range = [];
    let min = this.pagination.currentPage - (this.pagination.currentPage - 1) % 5;
    this.pagination.hasPrev = min > 1;
    this.pagination.hasNext = (min + 4) < this.pagination.totalPages;
    for (let index = min; index <= Math.min(min + 4, this.pagination.totalPages); index ++) {
      this.pagination.range.push(index);
    }
  }

  getApartments() : void {
    this.apartmentService.getApartments(
      this.pagination.currentPage - 1, 
      this.pagination.size,
      this.filters)
      .subscribe(data => {
        console.log(data);
        this.apartments = data.content;
        this.constructPagination(data);
      });
  }

  onSizeSelect(size : number): void {
    this.pagination.size = size;
    this.getApartments();
  }

  onPageSelect(page : number): void {
    this.pagination.currentPage = page;
    this.getApartments();
  }

  onPrevPage() : void {
    let min = this.pagination.currentPage - (this.pagination.currentPage - 1) % 5;
    if (min - 1 > 0) {
      this.pagination.currentPage = min - 1;
      this.getApartments();
    }
  }

  onNextPage() : void {
    let min = this.pagination.currentPage - (this.pagination.currentPage - 1) % 5;
    let max = min + 5;
    if (min <= this.pagination.totalPages) {
      this.pagination.currentPage = max;
      this.getApartments();
    }
  }

  onFiltersApply() : void {
    this.getApartments();
  }

  onFiltersReset() : void {
    this.filters = {};
    this.getApartments();
  }

  constructor(private apartmentService : ApartmentService) {

  }

  ngOnInit() {
    this.getApartments();
  }

}
