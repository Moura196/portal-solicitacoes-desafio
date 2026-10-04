import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaSolicitacao } from './consulta-solicitacao';

describe('ConsultaSolicitacao', () => {
  let component: ConsultaSolicitacao;
  let fixture: ComponentFixture<ConsultaSolicitacao>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsultaSolicitacao]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsultaSolicitacao);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
