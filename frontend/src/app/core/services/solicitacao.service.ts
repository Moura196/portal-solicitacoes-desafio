import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Solicitacao } from '../models/solicitacao';
import { DashboardMetricas } from '../models/dashboard-metrics';
import { environment } from '../../../environments/environment';
import { SolicitacaoRequest } from '../models/solicitacao-request';
import { AlterarSolicitacao } from '../models/alterar-solicitacao';
import { SolicitacaoFiltro } from '../models/solicitacao-filtro';
import { AlteraStatus } from '../models/altera-status';

@Injectable({
  providedIn: 'root',
})
export class SolicitacaoService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/solicitacoes`;

  criarSolicitacao(solicitacao: SolicitacaoRequest): Observable<Solicitacao> {
    return this.http.post<Solicitacao>(`${this.apiUrl}/cadastrar`, solicitacao);
  }

  editarSolicitacaoAberta(solicitacaoID: number, solicitacao: AlterarSolicitacao): Observable<Solicitacao> {
    return this.http.patch<Solicitacao>(`${this.apiUrl}/editar/${solicitacaoID}`, solicitacao);
  }

  excluirSolicitacaoAberta(solicitacaoID: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/excluir/${solicitacaoID}`);
  }

  listarSolicitacoes(filtro?: SolicitacaoFiltro): Observable<Solicitacao[]> {
    let params = new HttpParams();
    if (filtro) {
      if (filtro.categoria) params = params.set('categoria', filtro.categoria);
      if (filtro.status) params = params.set('status', filtro.status);
      if (filtro.titulo) params = params.set('titulo', filtro.titulo);
      if (filtro.dataInicio) params = params.set('dataInicio', filtro.dataInicio);
      if (filtro.dataFim) params = params.set('dataFim', filtro.dataFim);
    }

    return this.http.get<Solicitacao[]>(`${this.apiUrl}/listar`, { params });
  }

  detalharSolicitacao(solicitacaoID: number): Observable<Solicitacao> {
    return this.http.get<Solicitacao>(`${this.apiUrl}/detalhar/${solicitacaoID}`);
  }

  alterarStatus(solicitacaoID: number, novoStatus: AlteraStatus): Observable<Solicitacao> {
    return this.http.patch<Solicitacao>(`${this.apiUrl}/alterarStatus/${solicitacaoID}`, novoStatus);
  }

  getDashboardMetrics(): Observable<DashboardMetricas> {
    return this.http.get<DashboardMetricas>(`${this.apiUrl}/dashboard`);
  }

}
