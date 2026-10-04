import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalheSolicitacaoComponent } from './detalhe-solicitacao';

describe('DetalheSolicitacaoComponent', () => {
  let component: DetalheSolicitacaoComponent;
  let fixture: ComponentFixture<DetalheSolicitacaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalheSolicitacaoComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DetalheSolicitacaoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
