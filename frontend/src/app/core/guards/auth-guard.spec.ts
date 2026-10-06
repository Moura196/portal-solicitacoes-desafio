import { TestBed } from '@angular/core/testing';
import { Router, RouterStateSnapshot, ActivatedRouteSnapshot } from '@angular/router';
import { authGuard } from './auth-guard';
import { AuthService } from '../auth/auth';
import { vi } from 'vitest';

describe('authGuard', () => {
  let mockAuthService: any;
  let mockRouter: any;

  beforeEach(() => {
    mockAuthService = {
      isAuthenticated: vi.fn()
    };
    
    mockRouter = {
      createUrlTree: vi.fn()
    };

    TestBed.configureTestingModule({
      providers: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter }
      ]
    });
  });

  const runGuard = () => TestBed.runInInjectionContext(() => {
    return authGuard({} as ActivatedRouteSnapshot, {} as RouterStateSnapshot);
  });

  it('should return true if user is authenticated', () => {
    mockAuthService.isAuthenticated.mockReturnValue(true);
    
    const result = runGuard();
    
    expect(result).toBe(true);
  });

  it('should return a UrlTree to /login if user is not authenticated', () => {
    mockAuthService.isAuthenticated.mockReturnValue(false);
    const mockUrlTree = {} as any;
    mockRouter.createUrlTree.mockReturnValue(mockUrlTree);
    
    const result = runGuard();
    
    expect(result).toBe(mockUrlTree);
    expect(mockRouter.createUrlTree).toHaveBeenCalledWith(['/login']);
  });
});
