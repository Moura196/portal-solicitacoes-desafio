# 2ª ETAPA - DESENVOLVIMENTO DE MINI-PROJETO FULL STACK
**bit Soluções - Seleção DEV Jr. 09/2026**

---

## 1. Contexto do Projeto
Desenvolvimento de um **Portal de Solicitações Internas**, permitindo que colaboradores registrem demandas internas e acompanhem sua evolução até a conclusão.

O sistema deverá possuir:
- **Backend**: API REST/dados com regras de negócio e validações.
- **Frontend**: Interface web integrada consumindo a API.
- **Banco de Dados**: Persistência de dados em banco SQL.

---

## 2. Prazo e Entrega
- **Prazo Final**: Segunda-feira, **5 de outubro de 2026**.
- **Forma de Entrega**: Repositório GitHub (ou similar) ou pacote por e-mail.
- **Entregáveis Obrigatórios**:
  - Código-fonte completo (Backend e Frontend);
  - Instruções completas de execução;
  - Banco de dados (scripts de criação de tabelas e dicionário de dados);
  - **Memorial Técnico de Desenvolvimento**;
  - **README.md** com descrição do projeto;
  - Evidências de funcionamento (prints ou vídeo opcional).

---

## 3. Requisitos Funcionais

### 3.1. Autenticação
- **Funcionalidades**: Login (usuário e senha), controle de sessão e logout.
- **Campos**: Usuário, Senha.
- **Regra**: Apenas usuários autenticados podem acessar o sistema.

### 3.2. Cadastro de Solicitações
- **Campos informados**:
  - Título
  - Descrição
  - Categoria (Categorias sugeridas: TI, RH, Compras, Financeiro, Infraestrutura)
- **Campos automáticos**:
  - Data de criação
  - Usuário solicitante
  - Status inicial = `Aberto`
- **Ações**:
  - Criar solicitação
  - Editar solicitação aberta
  - Excluir solicitação aberta

### 3.3. Gerenciamento das Solicitações
- **Visualização (Listagem)**: Código, Título, Categoria, Solicitante, Data de abertura, Status.
- **Status válidos**: `Aberto`, `Em Atendimento`, `Concluído`.
- **Ações**:
  - Alterar status
  - Consultar detalhes

### 3.4. Consulta e Filtros
Permitir pesquisa por:
- Período
- Categoria
- Status
- Texto livre (título)

### 3.5. Dashboard
Exibir indicadores simples:
- Quantidade total de solicitações;
- Solicitações abertas;
- Solicitações em atendimento;
- Solicitações concluídas.

---

## 4. Requisitos Técnicos
Autonomia total do candidato para escolha de tecnologias, frameworks, bibliotecas e banco de dados SQL.

### Obrigatoriedades:
- **Backend**:
  - API para acesso aos dados da aplicação;
  - Persistência das informações em banco SQL;
  - Implementação das regras de negócio;
  - Tratamento adequado de erros e validações.
- **Frontend**:
  - Interface para interação do usuário;
  - Consumo da API desenvolvida;
  - Formulários e listagens previstas nos requisitos funcionais.
- **Banco de Dados**:
  - Persistência em banco SQL à escolha;
  - Scripts SQL (o mecanismo equivalente) para recriar toda a estrutura necessária.

---

## 5. Critérios de Avaliação

1. **Funcionamento da Solução**: Correção das funcionalidades, cumprimento dos requisitos e consistência geral.
2. **Organização do Código**: Estrutura de diretórios, separação de responsabilidades, padronização de nomenclaturas e clareza.
3. **Boas Práticas de Desenvolvimento**: Tratamento de erros, validações, reutilização de código, segurança básica e organização de componentes.
4. **Estrutura do Backend**: Organização da API, modelagem de dados, uso adequado de camadas e consistência dos endpoints.
5. **Estrutura do Frontend**: Organização dos componentes, navegação, consumo da API e experiência do usuário (UX).
6. **Documentação**: Qualidade do README, clareza das instruções e profundidade do Memorial Técnico.

---

## 6. Memorial Técnico Obrigatório
Documento indispensável denominado **MEMORIAL TÉCNICO DE DESENVOLVIMENTO**, contendo:

- **Tecnologias Utilizadas**: Relação de linguagens, frameworks frontend/backend, banco de dados, ferramentas de autenticação, validação, containerização, testes e serviços cloud.
- **Justificativa Técnica**: Motivo da escolha, benefícios para o cenário proposto, vantagens sobre alternativas e impacto em manutenção, escalabilidade ou produtividade.
- **Justificativa Conceitual**: Estrutura geral da aplicação, organização das camadas, estratégia de modelagem de dados, padrões de projeto, estratégia de autenticação e comunicação client-server.
- **Análise Crítica**: Limitações conhecidas da solução, melhorias futuras, requisitos aperfeiçoáveis e decisões que seriam diferentes em ambiente corporativo de produção.

---

## 7. Requisitos de Deploy & README.md
O README.md deve permitir executar a aplicação sem necessidade de adaptações e conter obrigatoriamente:

- **Pré-requisitos**: Linguagens, banco de dados e dependências.
- **Instalação**: Passo a passo completo para Backend, Frontend e Banco de dados.
- **Configuração**: Variáveis de ambiente e credenciais de demonstração.
- **Execução**: Comandos para rodar Backend e Frontend.
- **Acesso**: Informações de acesso aos usuários de teste.

---

## 8. Diferenciais (Não Obrigatórios)
- Docker e Docker Compose;
- Testes automatizados;
- CI/CD;
- Responsividade.

---

## 9. Informações de Contato da Empresa
- **Empresa**: bit Soluções (Tecnologia e Energias Renováveis)
- **Endereço**: CEP 58.030-130, Bairro dos Estados, João Pessoa - PB
- **E-mail**: contato@bitsolucoes.info
- **Telefone**: +55 83 99303-8383
