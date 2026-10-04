import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { NovaSolicitacaoCompomente } from '../../features/nova-solicitacao';

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
    // TODO: Criar funcionalidade depois de criar o componente necessário
    console.log('Ação: Pesquisar clicado');
  }

  protected sair(): void {
    // TODO: Criar funcionalidade depois de implementar a autenticação
    console.log('Ação: Sair clicado');
  }

}
