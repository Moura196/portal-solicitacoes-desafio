import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitacaoCardComponente } from './solicitacao-card';

describe('SolicitacaoCard', () => {
  let component: SolicitacaoCardComponente;
  let fixture: ComponentFixture<SolicitacaoCardComponente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SolicitacaoCardComponente]
    })
      .compileComponents();

    fixture = TestBed.createComponent(SolicitacaoCardComponente);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
