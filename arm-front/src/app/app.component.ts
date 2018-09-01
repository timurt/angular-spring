import { Component } from '@angular/core';
import { Location } from '@angular/common';

import {TokenStorage} from './core/token-storage';
import { AuthService } from './services/auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Restaurant Rentals Management Portal';

  constructor(
    private location: Location,
    public app: AuthService,
    private storage: TokenStorage) { }

  isCurrentRoute(v: string): boolean {
    return this.location.path().includes(v);
  }

  logout(): void {
    this.app.logout();
  }
}
