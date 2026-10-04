import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NovaSolicitacaoCompomente } from './nova-solicitacao';

describe('NovaSolicitacao', () => {
  let component: NovaSolicitacaoCompomente;
  let fixture: ComponentFixture<NovaSolicitacaoCompomente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NovaSolicitacaoCompomente]
    })
      .compileComponents();

    fixture = TestBed.createComponent(NovaSolicitacaoCompomente);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
