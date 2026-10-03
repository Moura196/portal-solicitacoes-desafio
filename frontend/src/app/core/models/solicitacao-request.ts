import { Categoria } from './categoria';

export interface SolicitacaoRequest {

  titulo: string;
  descricao: string;
  categoria: Categoria;

}
