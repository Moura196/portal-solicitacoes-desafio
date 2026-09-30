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
| `nome` | VARCHAR(150) | NN | Nome de apresentação do colaborador. |
| `email` | VARCHAR(100) | UQ, NN | Credencial de login do utilizador |
| `senha` | VARCHAR(255) | NN | Credencial de palavra-passe encriptada para autenticação. |
| `criado_em` | TIMESTAMP | NN | Horário em que o usuário foi criado. |

#### Tabela: `solicitacoes`
Armazena todas as demandas internas criadas pelos colaboradores.

| Nome do Campo | Tipo de Dado | Restrições | Descrição |
| :--- | :--- | :--- | :--- |
| `id` | SERIAL | PK, NN | Código de identificação único da demanda. |
| `titulo` | VARCHAR(200) | NN | Título da solicitação para pesquisa de texto livre. |
| `descricao` | TEXT | NN | Texto livre detalhando a solicitação. |
| `categoria` | VARCHAR(50) | NN | Setor da demanda, como TI, RH, Compras, Financeiro ou Infraestrutura. |
| `status` | VARCHAR(30) | CK, NN | Situação da demanda (Aberto, Em Atendimento, Concluído). É preenchido automaticamente como "Aberto" no ato da criação |
| `usuario_id` | INT | FK, NN | Ligação ao utilizador solicitante da demanda. |
| `data_abertura` | TIMESTAMP | NN | Data e hora geradas automaticamente no momento da criação. |