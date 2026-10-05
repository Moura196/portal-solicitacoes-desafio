import { Injectable, inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private snackBar = inject(MatSnackBar);

  success(message: string) {
    this.snackBar.open(message, 'Fechar', {
      duration: 5000,
      panelClass: ['success-snackbar'],
      horizontalPosition: 'end',
      verticalPosition: 'top',
    });
  }

  error(message: string, details?: any) {
    let finalMessage = message;

    if (details) {
      const msgDetails = details.mensagem || details.message || 'Erro inesperado';
      finalMessage = `${message} - ${msgDetails}`;
    }

    this.snackBar.open(finalMessage, 'Fechar', {
      duration: 7000,
      panelClass: ['error-snackbar'],
      horizontalPosition: 'end',
      verticalPosition: 'top',
    });
  }
}
