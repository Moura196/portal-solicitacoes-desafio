import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { Categoria, CATEGORIA } from '../../core/models/categoria';
import { Status, STATUS } from '../../core/models/status';

@Component({
  selector: 'app-consulta-solicitacao',
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatDatepickerModule
  ],
  providers: [provideNativeDateAdapter()], // Necessário para o DatePicker funcionar
  templateUrl: './consulta-solicitacao.html',
  styleUrl: './consulta-solicitacao.css'
})
export class ConsultaSolicitacaoComponent {
  private fb = inject(FormBuilder);
  private dialogRef = inject(MatDialogRef<ConsultaSolicitacaoComponent>);

  categorias = Object.keys(CATEGORIA) as Categoria[];
  categoriaLabels = CATEGORIA;

  statusList = Object.keys(STATUS) as Status[];
  statusLabels = STATUS;

  form = this.fb.group({
    titulo: [''],
    categoria: [''],
    status: [''],
    dataInicio: [''],
    dataFim: ['']
  });

  pesquisar(): void {
    const rawValue = this.form.value;

    // Filtra para mandar para a API apenas os campos que o usuário preencheu
    const filtroLimpo = Object.fromEntries(
      Object.entries(rawValue).filter(([_, value]) => value !== null && value !== '')
    );

    this.dialogRef.close(filtroLimpo);
  }

  limpar(): void {
    this.form.reset();
  }

  cancelar(): void {
    this.dialogRef.close();
  }
}
