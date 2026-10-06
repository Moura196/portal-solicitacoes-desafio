import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NovaSolicitacaoCompomente } from './nova-solicitacao';

describe('NovaSolicitacao', () => {
  let component: NovaSolicitacaoCompomente;
  let fixture: ComponentFixture<NovaSolicitacaoCompomente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NovaSolicitacaoCompomente],
      providers: [
        provideNoopAnimations(),
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: {} }
      ]
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
