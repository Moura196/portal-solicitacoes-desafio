import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NovaSolicitacaoCompomente } from '../../../features/nova-solicitacao/nova-solicitacao';
import { ConsultaSolicitacaoComponent } from '../../../features/consulta-solicitacao/consulta-solicitacao';
import { SolicitacaoService } from '../../services/solicitacao.service';
import { AuthService } from '../../auth/auth';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-navbar',
  imports: [
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class NavbarComponente {

  private dialog = inject(MatDialog);
  private solicitacaoService = inject(SolicitacaoService);
  private authService = inject(AuthService);
  private notificationService = inject(NotificationService);
  protected isAutenticado = this.authService.isAuthenticated;

  protected novaSolicitacao(): void {
    const dialogRef = this.dialog.open(NovaSolicitacaoCompomente, {
      width: '550px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(resultado => {
      if (resultado) {
        this.notificationService.success('Nova solicitação criada com sucesso!');
        this.solicitacaoService.recarregarLista();
      }
    });
  }

  protected pesquisar(): void {
    const dialogRef = this.dialog.open(ConsultaSolicitacaoComponent, {
      width: '750px'
    });
    dialogRef.afterClosed().subscribe(filtro => {
      if (filtro) {
        console.log('Aplicando filtros:', filtro);
        this.solicitacaoService.filtroAtual.set(filtro);
      }
    });
  }

  protected sair(): void {
    this.authService.logout();
  }

}
