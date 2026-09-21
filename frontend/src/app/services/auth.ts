import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Auth {

  private readonly tokenKey = 'procureflow_token';
  private readonly usernameKey = 'procureflow_username';
  private readonly roleKey = 'procureflow_role';

  saveLogin(token: string, username: string, role: string): void {
    localStorage.setItem(this.tokenKey, token);
    localStorage.setItem(this.usernameKey, username);
    localStorage.setItem(this.roleKey, role);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getUsername(): string | null {
    return localStorage.getItem(this.usernameKey);
  }

  getRole(): string | null {
    return localStorage.getItem(this.roleKey);
  }

  hasRole(role: string): boolean {
    return this.getRole() === role;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.usernameKey);
    localStorage.removeItem(this.roleKey);
  }
}