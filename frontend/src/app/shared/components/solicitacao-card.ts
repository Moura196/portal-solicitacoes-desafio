import { Component, computed, input } from '@angular/core';
import { Solicitacao } from '../../core/models/solicitacao';
import { STATUS, STATUS_CLASS } from '../../core/models/status';
import { CATEGORIA } from '../../core/models/categoria';
import { MatIconModule } from '@angular/material/icon';
import { DatePipe } from '@angular/common';

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

}
