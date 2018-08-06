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

  @Input() user: User;

  editable = false;
  isNew = false;

  error: string;

  newPassword: string;
  passwordError: string;

  constructor(private route: ActivatedRoute,
    private userService: UserService,
    private location: Location) { }

  ngOnInit() {
    this.getUser();
  }

  getUser(): void {
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

  isValid(): boolean {
    if (!this.user.name) {
      return false;
    }
    if (!this.user.login) {
      return false;
    }
    if (!this.user.roleType) {
      return false;
    }
    return true;
  }

  saveUser() {
    if (this.isValid()) {
      this.userService.updateUser(this.user)
      .subscribe(data => {
        if (data) {
          this.error = data.message;
        } else {
          this.goBack();
        }
      });
    } else {
      this.error = 'Fill all fields';
    }
  }

  deleteUser() {
    if (confirm('Are you sure?')) {
      this.userService.deleteUser(this.user)
    .subscribe(() => this.goBack());
    } else {
      return;
    }
  }

  cancelEditing() {
    this.getUser();
    this.editable = false;
  }

  goBack(): void {
    this.location.back();
  }

  showInputs(): boolean {
    return this.editable === true || this.isNew === true;
  }

  changePassword(): void {
    const passdata = {
      id : this.user.id,
      password : this.newPassword
    };
    this.userService.changePassword(passdata)
      .subscribe(data => {
        if (data) {
          this.passwordError = data.message;
        } else {
          alert('Password successfully changed');
          this.newPassword = '';
        }
      });
  }
}
