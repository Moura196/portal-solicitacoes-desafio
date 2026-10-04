import { Component, inject, signal, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
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
    // Busca os detalhes frescos via GET
    this.solicitacaoService.detalharSolicitacao(this.data.id).subscribe({
      next: (solic) => {
        this.solicitacao.set(solic);

        // Preenche os campos
        this.form.patchValue({
          titulo: solic.titulo,
          descricao: solic.descricao,
          categoria: solic.categoria,
          status: solic.status as string
        });

        // Bloqueia campos sensíveis se não estiver Aberto (regra de negócio)
        if (solic.status !== 'ABERTO') {
          this.form.controls.titulo.disable();
          this.form.controls.descricao.disable();
          this.form.controls.categoria.disable();
        }

        // Se já estiver concluída, bloqueia também o status
        if (solic.status === 'CONCLUIDO') {
          this.form.controls.status.disable();
        }
      },
      error: (err) => console.error('Erro ao detalhar', err)
    });
  }

  salvar(): void {
    if (this.form.invalid) return;

    const val = this.form.getRawValue();
    const original = this.solicitacao();
    if (!original || !original.id) return;

    const requests = [];

    // 1. Verifica se houve mudança nos dados textuais
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

    // 2. Verifica se houve mudança no Status
    const statusMudou = val.status !== original.status;
    if (statusMudou) {
      requests.push(this.solicitacaoService.alterarStatus(original.id, {
        status: val.status as Status
      }));
    }

    if (requests.length === 0) {
      this.dialogRef.close(); // Fecha se o usuário não mudou nada e apertou salvar
      return;
    }

    // Dispara as requisições que precisarem simultaneamente
    forkJoin(requests).subscribe({
      next: () => this.dialogRef.close(true), // Avisa o pai que algo foi modificado!
      error: (err) => console.error('Erro ao salvar', err)
    });
  }

  excluir(): void {
    const sol = this.solicitacao();
    if (!sol || sol.status !== 'ABERTO') return;

    if (confirm('Tem certeza que deseja excluir esta solicitação? Essa ação não pode ser desfeita.')) {
      this.solicitacaoService.excluirSolicitacaoAberta(sol.id!).subscribe({
        next: () => this.dialogRef.close(true), // Avisa o pai da exclusão
        error: (err) => console.error('Erro ao excluir', err)
      });
    }
  }

  fechar(): void {
    this.dialogRef.close();
  }
}
