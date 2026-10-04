import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NovaSolicitacaoCompomente } from '../../../features/nova-solicitacao/nova-solicitacao';
import { ConsultaSolicitacaoComponent } from '../../../features/consulta-solicitacao/consulta-solicitacao';
import { SolicitacaoService } from '../../services/solicitacao.service';

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

  protected novaSolicitacao(): void {
    const dialogRef = this.dialog.open(NovaSolicitacaoCompomente, {
      width: '550px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(resultado => {
      if (resultado) {
        // TODO: Implementar um alerta ou notificação para o usuário
        console.log('Nova solicitação criada com sucesso!', resultado);
      }
    });
  }

  protected pesquisar(): void {
    const dialogRef = this.dialog.open(ConsultaSolicitacaoComponent, {
      width: '600px'
    });
    dialogRef.afterClosed().subscribe(filtro => {
      if (filtro) {
        console.log('Aplicando filtros:', filtro);
        this.solicitacaoService.filtroAtual.set(filtro);
      }
    });
  }

  protected sair(): void {
    // TODO: Criar funcionalidade depois de implementar a autenticação
    console.log('Ação: Sair clicado');
  }

}
