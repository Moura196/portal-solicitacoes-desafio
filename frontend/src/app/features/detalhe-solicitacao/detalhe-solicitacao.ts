import { Component, inject, signal, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { DatePipe } from '@angular/common';
import { forkJoin } from 'rxjs';
import { SolicitacaoService } from '../../core/services/solicitacao.service';
import { Categoria, CATEGORIA } from '../../core/models/categoria';
import { Status, STATUS } from '../../core/models/status';
import { Solicitacao } from '../../core/models/solicitacao';
import { NotificationService } from '../../core/services/notification.service';
import { ConfirmDialogComponent } from '../../shared/components/confirm-dialog/confirm-dialog';

@Component({
  selector: 'app-detalhe-solicitacao',
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    DatePipe
  ],
  templateUrl: './detalhe-solicitacao.html',
  styleUrl: './detalhe-solicitacao.css'
})
export class DetalheSolicitacaoComponent implements OnInit {
  private fb = inject(FormBuilder);
  private solicitacaoService = inject(SolicitacaoService);
  private dialogRef = inject(MatDialogRef<DetalheSolicitacaoComponent>);
  public data = inject(MAT_DIALOG_DATA); // Injeta os dados passados na abertura do Modal (o ID)
  private notificationService = inject(NotificationService);
  private dialog = inject(MatDialog);

  solicitacao = signal<Solicitacao | null>(null);

  categorias = Object.keys(CATEGORIA) as Categoria[];
  categoriaLabels = CATEGORIA;

  statusList = Object.keys(STATUS) as Status[];
  statusLabels = STATUS;

  form = this.fb.nonNullable.group({
    titulo: ['', [Validators.required, Validators.minLength(5)]],
    descricao: ['', [Validators.required, Validators.minLength(10)]],
    categoria: ['', Validators.required],
    status: ['', Validators.required]
  });

  ngOnInit(): void {
    this.solicitacaoService.detalharSolicitacao(this.data.id).subscribe({
      next: (solic) => {
        this.solicitacao.set(solic);

        this.form.patchValue({
          titulo: solic.titulo,
          descricao: solic.descricao,
          categoria: solic.categoria,
          status: solic.status as string
        });

        if (solic.status !== 'ABERTO') {
          this.form.controls.titulo.disable();
          this.form.controls.descricao.disable();
          this.form.controls.categoria.disable();
        }

        if (solic.status === 'CONCLUIDO') {
          this.form.controls.status.disable();
        }
      },
      error: (err) => {
        console.error('Erro ao detalhar', err);
        this.notificationService.error('Erro ao buscar detalhes da solicitação', err.error);
      }
    });
  }

  salvar(): void {
    if (this.form.invalid) return;

    const val = this.form.getRawValue();
    const original = this.solicitacao();
    if (!original || !original.id) return;

    const requests = [];

    const detalhesMudaram = val.titulo !== original.titulo ||
      val.descricao !== original.descricao ||
      val.categoria !== original.categoria;

    if (detalhesMudaram && original.status === 'ABERTO') {
      requests.push(this.solicitacaoService.editarSolicitacaoAberta(original.id, {
        titulo: val.titulo,
        descricao: val.descricao,
        categoria: val.categoria as Categoria
      }));
    }

    const statusMudou = val.status !== original.status;
    if (statusMudou) {
      requests.push(this.solicitacaoService.alterarStatus(original.id, {
        status: val.status as Status
      }));
    }

    if (requests.length === 0) {
      this.dialogRef.close();
      return;
    }

    forkJoin(requests).subscribe({
      next: () => {
        this.notificationService.success('Solicitação atualizada com sucesso!');
        this.dialogRef.close(true);
      },
      error: (err) => {
        console.error('Erro ao salvar', err);
        let errorDetails = err.error;
        if (errorDetails && errorDetails.mensagem) {
          if (errorDetails.mensagem.includes('A solicitação só pode passar de EM_ATENDIMENTO para CONCLUIDO')) {
            this.notificationService.error('Apenas transição para "Concluído" é permitida.');
            return;
          }
          if (errorDetails.mensagem.includes('A solicitação só pode passar de ABERTO para EM_ATENDIMENTO')) {
            this.notificationService.error('Apenas transição para "Em Atendimento" é permitida.');
            return;
          }
        }
        this.notificationService.error('Erro ao atualizar solicitação', errorDetails);
      }
    });
  }

  excluir(): void {
    const sol = this.solicitacao();
    if (!sol || sol.status !== 'ABERTO') return;

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '400px',
      data: {
        title: 'Confirmar Exclusão',
        message: 'Tem certeza que deseja excluir esta solicitação? Essa ação não pode ser desfeita.'
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.solicitacaoService.excluirSolicitacaoAberta(sol.id!).subscribe({
          next: () => {
            this.notificationService.success('Solicitação excluída com sucesso!');
            this.dialogRef.close(true);
          },
          error: (err) => {
            console.error('Erro ao excluir', err);
            this.notificationService.error('Erro ao excluir solicitação', err.error);
          }
        });
      }
    });
  }

  fechar(): void {
    this.dialogRef.close();
  }

  houveAlteracao(): boolean {
    const original = this.solicitacao();
    if (!original) return false;

    const current = this.form.getRawValue();
    return current.titulo !== original.titulo ||
      current.descricao !== original.descricao ||
      current.categoria !== original.categoria ||
      current.status !== original.status;
  }
}
