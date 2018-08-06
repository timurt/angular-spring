import { Injectable } from '@angular/core';

const TOKEN_KEY = 'AuthToken';
const USER_LOGIN_KEY = 'UserLogin';
const USER_ROLE_KEY = 'UserRole';
const EXPIRE_TIME_KEY = 'ExpireTime';

@Injectable({
  providedIn: 'root'
})
export class TokenStorage {
  constructor() { }

  private clear() {
    localStorage.removeItem(EXPIRE_TIME_KEY);
    localStorage.removeItem(USER_ROLE_KEY);
    localStorage.removeItem(USER_LOGIN_KEY);
    localStorage.removeItem(TOKEN_KEY);
    localStorage.clear();
  }

  signOut() {
    this.clear();
  }

  public saveToken(token: string) {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY,  token);
  }

  public saveExpirationTime(time: string) {
    localStorage.removeItem(EXPIRE_TIME_KEY);
    localStorage.setItem(EXPIRE_TIME_KEY,  time);
  }

  public saveData(data) {
    this.clear();
    localStorage.setItem(TOKEN_KEY,  data['accessToken']);
    localStorage.setItem(USER_LOGIN_KEY,  data['login']);
    localStorage.setItem(USER_ROLE_KEY,  data['role']);
    localStorage.setItem(EXPIRE_TIME_KEY, data['expirationTime']);
  }

  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY);
  }

  public getLogin(): string {
    return localStorage.getItem(USER_LOGIN_KEY);
  }

  public getRole(): string {
    return localStorage.getItem(USER_ROLE_KEY);
  }

  public getExpirationTime(): number {
    return +localStorage.getItem(EXPIRE_TIME_KEY);
  }
}
