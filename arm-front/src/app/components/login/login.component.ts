import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {

  loginForm = {login: '', password: ''};
  registerForm = {login: '', password: '', name : '', roleType : 'CLIENT'};
  loginError: string;
  registerError: string;

  isLoginTab = true;

  constructor(private app: AuthService,
    private http: HttpClient,
    private router: Router) { }

  ngOnInit() {
  }

  loginTab(): void {
    this.isLoginTab = true;
    this.loginForm = {login: '', password: ''};
    this.loginError = '';
  }

  registerTab(): void {
    this.isLoginTab = false;
    this.registerForm = {login: '', password: '', name : '', roleType : 'CLIENT'};
    this.registerError = '';
  }

  login() {
    this.app.authenticate(this.loginForm).subscribe(
      data => {
        if (data.error) {
          this.loginError = data.message;
        } else {
          this.router.navigateByUrl('/');
        }
      }
    );
    return false;
  }

  register() {
    this.app.register(this.registerForm).subscribe(
      data => {
        if (data) {
          this.registerError = data.message;
        } else {
          this.loginTab();
        }
      }
    );
    return false;
  }
}
