export type Status = 'ABERTO' | 'EM_ATENDIMENTO' | 'CONCLUIDO';

export const STATUS: Record<Status, string> = {
    'ABERTO': 'Aberto',
    'EM_ATENDIMENTO': 'Em Atendimento',
    'CONCLUIDO': 'Concluído'
};