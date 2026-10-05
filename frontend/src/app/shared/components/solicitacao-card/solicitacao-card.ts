import { Component, computed, inject, input } from '@angular/core';
import { Solicitacao } from '../../../core/models/solicitacao';
import { STATUS, STATUS_CLASS } from '../../../core/models/status';
import { CATEGORIA } from '../../../core/models/categoria';
import { MatIconModule } from '@angular/material/icon';
import { DatePipe } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { SolicitacaoService } from '../../../core/services/solicitacao.service';
import { DetalheSolicitacaoComponent } from '../../../features/detalhe-solicitacao/detalhe-solicitacao';

@Component({
  selector: 'app-solicitacao-card',
  imports: [
    MatIconModule,
    DatePipe
  ],
  templateUrl: './solicitacao-card.html',
  styleUrl: './solicitacao-card.css',
})

export class SolicitacaoCardComponente {
  solicitacao = input.required<Solicitacao>();

  private dialog = inject(MatDialog);
  private solicitacaoService = inject(SolicitacaoService);

  statusLabel = computed(() => {
    const statusValue = this.solicitacao().status;
    return statusValue ? STATUS[statusValue] : 'Desconhecido';
  });

  statusClass = computed(() => {
    const statusValue = this.solicitacao().status;
    return statusValue ? STATUS_CLASS[statusValue] : '';
  });

  categoriaLabel = computed(() => {
    const catValue = this.solicitacao().categoria;
    return catValue ? CATEGORIA[catValue] : 'Desconhecida';
  });

  abrirDetalhes(): void {
    const ref = this.dialog.open(DetalheSolicitacaoComponent, {
      width: '750px',
      maxHeight: '90vh',
      data: { id: this.solicitacao().id }
    });

    ref.afterClosed().subscribe(houveModificacao => {
      if (houveModificacao) {
        const filtrosAtuais = this.solicitacaoService.filtroAtual();
        this.solicitacaoService.filtroAtual.set({ ...filtrosAtuais });
      }
    });
  }

}
