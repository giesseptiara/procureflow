import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Auth } from './services/auth';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  constructor(
    private router: Router,
    public auth: Auth
  ) {}

  get showLayout(): boolean {
    return this.router.url !== '/login';
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

}
