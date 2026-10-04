import { Categoria } from "./categoria";
import { Status } from "./status";
import { Usuario } from "./usuario";

export interface Solicitacao {

    id?: number;
    titulo: string;
    descricao: string;
    categoria: Categoria;
    status?: Status;
    dataAbertura?: string;
    solicitante?: Usuario;

}
