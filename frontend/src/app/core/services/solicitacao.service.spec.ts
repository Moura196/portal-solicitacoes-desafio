import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { SolicitacaoService } from './solicitacao.service';
import { environment } from '../../../environments/environment';

describe('SolicitacaoService', () => {
  let service: SolicitacaoService;
  let httpMock: HttpTestingController;
  const apiUrl = `${environment.apiUrl}/solicitacoes`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(SolicitacaoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create a solicitacao', () => {
    const mockRequest: any = { titulo: 'Teste', descricao: 'Desc', categoria: 'TI' };
    const mockResponse: any = { id: 1, ...mockRequest };

    service.criarSolicitacao(mockRequest).subscribe(res => {
      expect(res).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${apiUrl}/cadastrar`);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  it('should edit a solicitacao', () => {
    const mockRequest: any = { titulo: 'Novo' };
    service.editarSolicitacaoAberta(1, mockRequest).subscribe();

    const req = httpMock.expectOne(`${apiUrl}/editar/1`);
    expect(req.request.method).toBe('PATCH');
    req.flush({});
  });

  it('should delete a solicitacao', () => {
    service.excluirSolicitacaoAberta(1).subscribe();

    const req = httpMock.expectOne(`${apiUrl}/excluir/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('should list solicitacoes with query params', () => {
    const mockFiltro: any = { status: 'ABERTO' };
    
    service.listarSolicitacoes(mockFiltro).subscribe();

    const req = httpMock.expectOne(request => request.url === `${apiUrl}/listar` && request.params.get('status') === 'ABERTO');
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('should detail a solicitacao', () => {
    service.detalharSolicitacao(5).subscribe();

    const req = httpMock.expectOne(`${apiUrl}/detalhar/5`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });

  it('should alter status', () => {
    service.alterarStatus(2, { status: 'CONCLUIDO' } as any).subscribe();

    const req = httpMock.expectOne(`${apiUrl}/alterarStatus/2`);
    expect(req.request.method).toBe('PATCH');
    req.flush({});
  });

  it('should get dashboard metrics', () => {
    service.getDashboardMetrics().subscribe();

    const req = httpMock.expectOne(`${apiUrl}/dashboard`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });
});
