# Dicionário de Dados - Portal de Solicitações

Este documento descreve a estrutura do banco de dados relacional (PostgreSQL) utilizado no Portal de Solicitações Internas, com o objetivo de registrar e acompanhar as demandas internas dos colaboradores.

## Legenda de Restrições
* **PK**: Chave Primária (Primary Key)
* **FK**: Chave Estrangeira (Foreign Key)
* **NN**: Não Nulo (Not Null)
* **UQ**: Único (Unique)
* **CK**: Checagem (Check / Regra de validação)

#### Tabela: `usuarios`
Armazena as credencias dos usuários do sistema.

| Nome do Campo | Tipo de Dado | Restrições | Descrição |
| :--- | :--- | :--- | :--- |
| `id` | SERIAL | PK, NN | Identificador sequencial único do utilizador. |
| `nome` | VARCHAR(100) | NN | Nome de apresentação do colaborador. |
| `email` | VARCHAR(100) | UQ, NN | Credencial de login do utilizador |
| `senha` | VARCHAR(255) | NN | Credencial de palavra-passe encriptada para autenticação. |
| `criado_em` | TIMESTAMP | NN, DEFAULT `CURRENT_TIMESTAMP` | Horário em que o usuário foi criado (preenchido pelo banco). |

#### Tabela: `solicitacoes`
Armazena todas as demandas internas criadas pelos colaboradores.

| Nome do Campo | Tipo de Dado | Restrições | Descrição |
| :--- | :--- | :--- | :--- |
| `id` | SERIAL | PK, NN | Código de identificação único da solicitação. |
| `titulo` | VARCHAR(200) | NN | Título da solicitação para pesquisa de texto livre. |
| `descricao` | TEXT | NN | Texto livre detalhando a solicitação. |
| `categoria` | VARCHAR(50) | CK, NN | Setor da solicitação. Valores permitidos: `TI`, `RH`, `COMPRAS`, `FINANCEIRO`, `INFRAESTRUTURA`. |
| `status` | VARCHAR(30) | CK, NN, DEFAULT `'ABERTO'` | Situação da solicitação. Valores permitidos: `ABERTO`, `EM_ATENDIMENTO`, `CONCLUIDO`. É preenchido automaticamente como `ABERTO` no ato da criação. |
| `usuario_id` | INT | FK, NN | Ligação ao utilizador solicitante da solicitação (`usuarios.id`). |
| `data_abertura` | TIMESTAMP | NN, DEFAULT `CURRENT_TIMESTAMP` | Data e hora geradas automaticamente no momento da criação. |

#### Índices da tabela `solicitacoes`
Criados na migration `V2__criar_indices_solicitacoes.sql` para acelerar as consultas e filtros da listagem.

| Nome do Índice | Coluna | Finalidade |
| :--- | :--- | :--- |
| `idx_solicitacoes_status` | `status` | Filtro por status e contagem por status do Dashboard. |
| `idx_solicitacoes_categoria` | `categoria` | Filtro por categoria. |
| `idx_solicitacoes_data_abertura` | `data_abertura` | Filtro por período (data inicial/final) e ordenação por data. |
| `idx_solicitacoes_titulo` | `titulo` | Apoio à busca por título. |