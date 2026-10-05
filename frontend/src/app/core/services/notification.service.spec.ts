import { TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationService } from './notification.service';
import { vi } from 'vitest';

describe('NotificationService', () => {
  let service: NotificationService;
  let mockSnackBar: any;

  beforeEach(() => {
    mockSnackBar = { open: vi.fn() };

    TestBed.configureTestingModule({
      providers: [
        { provide: MatSnackBar, useValue: mockSnackBar }
      ]
    });
    service = TestBed.inject(NotificationService);
  });

  it('should show success message', () => {
    service.success('Sucesso!');
    expect(mockSnackBar.open).toHaveBeenCalledWith('Sucesso!', 'Fechar', expect.objectContaining({
      panelClass: ['success-snackbar']
    }));
  });

  it('should show error message', () => {
    service.error('Erro ao salvar');
    expect(mockSnackBar.open).toHaveBeenCalledWith('Erro ao salvar', 'Fechar', expect.objectContaining({
      panelClass: ['error-snackbar']
    }));
  });

  it('should show error message with details', () => {
    service.error('Erro', { mensagem: 'Detalhe do erro' });
    expect(mockSnackBar.open).toHaveBeenCalledWith('Erro - Detalhe do erro', 'Fechar', expect.objectContaining({
      panelClass: ['error-snackbar']
    }));
  });
});
