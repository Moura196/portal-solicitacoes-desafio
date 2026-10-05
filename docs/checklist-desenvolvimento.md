### 1. Modelagem & Banco de Dados (SQL com Flyway)

* [x] Adicionar dependências do **Flyway** e do PostgreSQL no `pom.xml` do projeto backend.
* [x] Criar a estrutura de diretórios obrigatória do Flyway (`src/main/resources/db/migration/`).
* [x] Criar o script de migração inicial (ex: `V1__criar_tabelas.sql` contendo as tabelas (`usuarios`, `solicitacoes`, `categorias`)) utilizando scripts SQL.
* [x] Definir constraints, chaves primárias, estrangeiras e enums/checks para os status no script SQL (`Aberto`, `Em Atendimento`, `Concluído`).
* [x] Incluir comandos `INSERT` com dados iniciais (*seeds*) para categorias e informações de acesso aos usuários de teste (pode ser no fim do script `V1` ou em um novo arquivo `V2__inserir_dados_iniciais.sql`).
* [x] Redigir o dicionário de dados em Markdown (descrição dos campos, tipos e finalidade de cada tabela).
* [x] Criar migration com índices nas colunas usadas em filtros/consultas (quando implementar a funcionalidade de filtros):
  * `idx_solicitacoes_status` → `status`
  * `idx_solicitacoes_categoria` → `categoria`
  * `idx_solicitacoes_data_abertura` → `data_abertura`
  * `idx_solicitacoes_titulo` → `titulo`
* [x] Atualizar o documento dicionario-de-dados.md com as informações dos índices. Verificar se falta mais algo a ser acrescentado no documento.

---

### 2. Backend & Regras de Negócio

* [x] **Configuração Base (JPA e Flyway):**
* [x] Ajustar o arquivo `application.properties` com a instrução `spring.jpa.hibernate.ddl-auto=validate` para que o Flyway assuma o controle da criação do banco e o Hibernate apenas valide a compatibilidade das entidades.

* [ ] **Autenticação:**
* [ ] Implementar endpoint de login validando credenciais.
* [ ] Configurar controle de sessão / geração e validação de token (JWT ou sessão).
* [ ] Proteger as rotas de solicitações contra acesso não autenticado.

* [x] **Módulo de Solicitações:**
* [x] `POST /solicitacoes`: criação com título, descrição, categoria, data automática, solicitante vindo da sessão e status inicial travado em `Aberto`.
* [x] `GET /solicitacoes`: listagem com suporte a filtros (período/datas, categoria, status e busca textual por título).
* [x] `GET /solicitacoes/{id}`: detalhamento da solicitação.
* [x] `PATCH /solicitacoes/{id}`: edição de título, descrição ou categoria (restrito a solicitações com status `Aberto`).
* [x] `DELETE /solicitacoes/{id}`: exclusão permitida apenas se o status for `Aberto`.
* [x] `PATCH /solicitacoes/{id}/status`: transição de status (`Aberto` -> `Em Atendimento` -> `Concluído`).

* [x] **Dashboard:**
* [x] Endpoint para consolidação de métricas: total geral e contagem por cada status.

* [x] **Qualidade & Robustez:**
* [x] Implementar tratamento global de exceções e retornos HTTP semânticos (400, 401, 403, 404, 500).
* [x] Validar campos obrigatórios no payload antes de persistir.

---

### 3. Frontend & Experiência do Usuário (UX)

* [x] **Configuração Inicial do Projeto:**
  * [x] Criar projeto `frontend` na raiz com Angular CLI (Standalone Components).
  * [x] Instalar e configurar **Angular Material** para componentes base.
  * [x] Configurar o design system com **Vanilla CSS** (variáveis globais, fontes, paleta de cores).
  * [x] Criar estrutura base de pastas (`core/`, `shared/`, `features/`).

* [x] **Integração Base (API):**
  * [x] Configurar variáveis de ambiente apontando para o backend (Spring Boot).
  * [x] Criar serviços base HTTP.
  * [x] *Ajuste Temporário (Frontend/Backend):* Preparar um Mock/Dummie de usuário no frontend ou ajustar temporariamente o backend para que o campo "usuário solicitante" funcione na criação da solicitação antes da autenticação final.

* [ ] **Listagem e Filtros:**
  * [ ] Construir a listagem de solicitações (ex: Angular Material Table) com dados mockados inicialmente e depois conectados à API.
  * [ ] Implementar paginação ou scroll adequado e estados de carregamento (Loading spinners).
  * [ ] Adicionar os filtros acionáveis (período, categoria, status e texto do título) e conectar à API de listagem.

* [ ] **Formulários & CRUD (Solicitações):**
  * [ ] Criar o formulário de abertura de chamado com `Reactive Forms` (com validações visuais).
  * [ ] Desenvolver o **Modal de Detalhamento** (Dialog do Material), exibindo todas as informações da solicitação.
  * [ ] Incluir ações no Modal para alteração de status (Aberto -> Em Atendimento -> Concluído).
  * [ ] Aplicar bloqueio visual/desabilitação de botões para impedir a edição ou exclusão caso o status não seja `Aberto`.
  * [ ] Configurar alertas visuais para sucesso ou erro em requisições (ex: `SnackBar` do Material).

* [ ] **Dashboard & Refinamento Visual:**
  * [ ] Implementar a página inicial (Dashboard) com os Cards de métricas visíveis no topo consumindo a API.
  * [ ] Realizar a refinação visual de toda a aplicação: adicionar micro-animações, aplicar glassmorphism e alinhar os espaços com CSS Grid/Flexbox para uma experiência *Premium*.

* [ ] **Fluxo de Autenticação (Última Etapa):**
  * [ ] *(Backend)* Finalizar os ajustes de segurança: geração e validação de token (ou sessão), protegendo todas as rotas de API desenvolvidas anteriormente.
  * [ ] Criar a tela de login funcional no frontend.
  * [ ] Implementar `HttpInterceptor` para adicionar credenciais/tokens de forma global nas requisições feitas pelo Angular.
  * [ ] Configurar *Route Guards* para proteger rotas privadas e fazer o redirecionamento correto (ex: usuário não logado é enviado para /login).
  * [ ] Adicionar funcionalidade de logout e limpeza completa da sessão no frontend.

---

### 4. Diferenciais Competitivos

* [ ] Configurar `Dockerfile` e `docker-compose.yml` para subir banco, backend e frontend com apenas um comando.
* [ ] Adicionar testes unitários/integração nos fluxos críticos do backend (regras de status e CRUD).
* [ ] Garantir responsividade da interface para desktop e mobile.

---

### 5. Documentação & Entrega (Critério Eliminatório/Crucial)

* [ ] **README.md:**
* [ ] Pré-requisitos de sistema.
* [ ] Passo a passo exato para subir a aplicação localmente (ou via Docker).
* [ ] Variáveis de ambiente exemplificadas (`.env.example`).
* [ ] Usuários e senhas de teste explícitos para o avaliador logar imediatamente.


* [ ] **Memorial Técnico de Desenvolvimento:**
* [ ] Lista completa de tecnologias, frameworks e bibliotecas utilizadas.
* [ ] Justificativa técnica (por que escolheu essa stack vs alternativas).
* [ ] Justificativa conceitual (arquitetura adotada, fluxo client-server, modelagem).
* [ ] Análise crítica (débitos técnicos conscientes, limitações e melhorias para produção).


* [ ] **Evidências:**
* [ ] Capturar prints das telas principais e dos fluxos em execução (ou link de vídeo curto).


* [ ] **Revisão Final:**
* [ ] Testar a execução do projeto do zero em um ambiente limpo antes do push final até 05/10/2026.