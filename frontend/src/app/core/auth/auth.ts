import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { LoginRequest, LoginResponse } from './auth.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  private readonly API_URL = environment.apiUrl + '/auth';
  private readonly TOKEN_KEY = 'auth_token';

  public isAuthenticated = signal<boolean>(this.hasToken());

  login(credentials: LoginRequest) {
    return this.http.post<LoginResponse>(`${this.API_URL}/login`, credentials).pipe(
      tap(response => {
        this.setToken(response.token);
        this.isAuthenticated.set(true);
      })
    );
  }

  logout() {
    localStorage.removeItem(this.TOKEN_KEY);
    this.isAuthenticated.set(false);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  private setToken(token: string) {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  private hasToken(): boolean {
    return !!this.getToken();
  }
}
