import { Component, OnInit } from '@angular/core';

import { User } from '../../models/user';
import { Pagination } from '../../models/pagination';

import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: User[];

  pagination : Pagination = new Pagination();

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

  getUsers() : void {
    this.userService.getUsers(
      this.pagination.currentPage - 1, 
      this.pagination.size)
      .subscribe(data => {
        console.log(data);
        this.users = data.content;
        this.constructPagination(data);
      });
  }

  onSizeSelect(size : number): void {
    this.pagination.size = size;
    this.getUsers();
  }

  onPageSelect(page : number): void {
    this.pagination.currentPage = page;
    this.getUsers();
  }

  onPrevPage() : void {
    let min = this.pagination.currentPage - (this.pagination.currentPage - 1) % 5;
    if (min - 1 > 0) {
      this.pagination.currentPage = min - 1;
      this.getUsers();
    }
  }

  onNextPage() : void {
    let min = this.pagination.currentPage - (this.pagination.currentPage - 1) % 5;
    let max = min + 5;
    if (min <= this.pagination.totalPages) {
      this.pagination.currentPage = max;
      this.getUsers();
    }
  }

  constructor(private userService : UserService) { }

  ngOnInit() {
    this.getUsers();
  }

}
