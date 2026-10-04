import { Categoria } from './categoria';
import { Status } from './status';

export interface SolicitacaoFiltro {

  categoria?: Categoria;
  status?: Status;
  titulo?: string;
  dataInicio?: string;
  dataFim?: string;

}
