import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { SolicitacaoService } from '../core/services/solicitacao.service';
import { Categoria, CATEGORIA } from '../core/models/categoria';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-nova-solicitacao',
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule
  ],
  templateUrl: './nova-solicitacao.html',
  styleUrl: './nova-solicitacao.css',
})
export class NovaSolicitacaoCompomente {

  private fb = inject(FormBuilder);
  private solicitacaoService = inject(SolicitacaoService);
  private dialogRef = inject(MatDialogRef<NovaSolicitacaoCompomente>);

  categorias = Object.keys(CATEGORIA) as Categoria[];
  categoriaLabels = CATEGORIA;

  form = this.fb.nonNullable.group({
    titulo: ['', [Validators.required, Validators.minLength(5)]],
    descricao: ['', [Validators.required, Validators.minLength(10)]],
    categoria: ['', Validators.required]
  });

  salvar(): void {
    if (this.form.invalid) return;

    this.solicitacaoService.criarSolicitacao(this.form.getRawValue() as any).subscribe({
      next: (novaSolicitacao) => {
        this.dialogRef.close(novaSolicitacao);
      },
      error: (err) => {
        console.error('Erro ao criar solicitação', err);
      }
    });
  }

  cancelar(): void {
    this.dialogRef.close();
  }

}
