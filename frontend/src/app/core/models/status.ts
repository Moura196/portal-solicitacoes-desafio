export type Status = 'ABERTO' | 'EM_ATENDIMENTO' | 'CONCLUIDO';

export const STATUS: Record<Status, string> = {
    'ABERTO': 'Aberto',
    'EM_ATENDIMENTO': 'Em Atendimento',
    'CONCLUIDO': 'Concluído'
};

export const STATUS_CLASS: Record<Status, string> = {
    'ABERTO': 'status-aberto',
    'EM_ATENDIMENTO': 'status-atendimento',
    'CONCLUIDO': 'status-concluido'
};