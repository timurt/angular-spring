import { Component, OnInit, Input, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { User } from '../../models/user';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  @Input() user : User;

  editable = false;
  isNew = false;

  constructor(private route: ActivatedRoute,
    private userService: UserService,
    private location: Location) { }

  ngOnInit() {
    this.getUser();
  }

  getUser() : void {
    const id = +this.route.snapshot.paramMap.get('id');
    if (id) {
      this.userService.getUser(id)
      .subscribe(user => {
        this.user = user;
      });
    } else {
      this.isNew = true;
      this.user = new User();
    }
  }

  saveUser() {
    this.userService.updateUser(this.user)
    .subscribe(() => this.goBack())
  }

  deleteUser() {
    this.userService.deleteUser(this.user)
    .subscribe(() => this.goBack())
  }

  cancelEditing() {
    this.getUser();
    this.editable = false;
  }

  goBack(): void {
    this.location.back();
  }

  showInputs() : boolean {
    return this.editable == true || this.isNew == true;
  }

}
