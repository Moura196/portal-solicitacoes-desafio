import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from './auth';
import { environment } from '../../../environments/environment';
import { vi } from 'vitest';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  let mockRouter: any;

  beforeEach(() => {
    mockRouter = { navigate: vi.fn() };
    
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: Router, useValue: mockRouter }
      ]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    
    // Limpa o localStorage antes de cada teste
    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify(); // Garante que não existem requisições pendentes
  });

  it('should login and store token', () => {
    const mockResponse = { token: 'fake-jwt-token' };
    
    service.login({ email: 'test@test.com', senha: '123' }).subscribe();
    
    const req = httpMock.expectOne(`${environment.apiUrl}/auth/login`);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
    
    expect(localStorage.getItem('auth_token')).toBe('fake-jwt-token');
    expect(service.isAuthenticated()).toBe(true);
  });

  it('should logout, remove token and navigate to login', () => {
    localStorage.setItem('auth_token', 'fake-token');
    
    service.logout();
    
    expect(localStorage.getItem('auth_token')).toBeNull();
    expect(service.isAuthenticated()).toBe(false);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/login']);
  });
});
