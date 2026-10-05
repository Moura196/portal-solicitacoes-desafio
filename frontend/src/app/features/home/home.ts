import { Component, computed, inject, signal, OnInit, effect } from '@angular/core';
import { SolicitacaoService } from '../../core/services/solicitacao.service';
import { DashboardMetricas } from '../../core/models/dashboard-metrics';
import { Solicitacao } from '../../core/models/solicitacao';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { SolicitacaoCardComponente } from '../../shared/components/solicitacao-card/solicitacao-card';

@Component({
  selector: 'app-home',
  imports: [
    MatIconModule,
    MatButtonModule,
    SolicitacaoCardComponente
  ],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class HomeComponente implements OnInit {

  private solicitacaoService = inject(SolicitacaoService);

  metricas = signal<DashboardMetricas | null>(null);
  solicitacoes = signal<Solicitacao[]>([]);

  abertas = computed(() => this.solicitacoes().filter(s => s.status === 'ABERTO'));
  emAtendimento = computed(() => this.solicitacoes().filter(s => s.status === 'EM_ATENDIMENTO'));
  concluidas = computed(() => this.solicitacoes().filter(s => s.status === 'CONCLUIDO'));

  isConcluidasCollapsed = signal(false);

  hasActiveFilters = computed(() => {
    const f = this.solicitacaoService.filtroAtual();
    return !!(f.categoria || f.status || f.titulo || f.dataInicio || f.dataFim);
  });

  toggleConcluidas(): void {
    this.isConcluidasCollapsed.update(val => !val);
  }

  clearFilters(): void {
    this.solicitacaoService.filtroAtual.set({});
  }

  constructor() {
    effect(() => {
      const filtros = this.solicitacaoService.filtroAtual();
      this.listarSolicitacoes(filtros);
    });
  }

  ngOnInit(): void {
    this.carregarDashboard();
  }

  private carregarDashboard(): void {
    this.solicitacaoService.getDashboardMetrics().subscribe({
      next: (dados) => this.metricas.set(dados),
      error: (err) => console.error('Erro ao carregar métricas', err)
    });
  }

  private listarSolicitacoes(filtro: any = {}): void {
    this.solicitacaoService.listarSolicitacoes(filtro).subscribe({
      next: (dados) => this.solicitacoes.set(dados),
      error: (err) => console.error('Erro ao carregar solicitações', err)
    });

  }
}
