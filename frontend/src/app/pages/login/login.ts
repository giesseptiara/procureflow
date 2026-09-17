import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Auth } from '../../services/auth';

interface LoginResponse {
  message: string;
  username: string;
  role: string;
  token: string;
}

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  username = '';
  password = '';
  errorMessage = '';
  isLoading = false;

  private readonly apiUrl = 'http://localhost:8080/api';

  constructor(
    private http: HttpClient,
    private auth: Auth,
    private router: Router
  ) {}

  login(): void {
    this.errorMessage = '';

    if (!this.username || !this.password) {
      this.errorMessage = 'Username dan password wajib diisi.';
      return;
    }

    this.isLoading = true;

    this.http.post<LoginResponse>(
      `${this.apiUrl}/auth/login`,
      {
        username: this.username,
        password: this.password
      }
    ).subscribe({
      next: (response) => {
        this.auth.saveLogin(
          response.token,
          response.username,
          response.role
        );

        this.isLoading = false;
        this.router.navigate(['/']);
      },
      error: (error) => {
        this.isLoading = false;

        this.errorMessage =
          error.error?.message || 'Login gagal. Silakan coba lagi.';
      }
    });
  }
}