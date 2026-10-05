O documento **"Seleção DEV Jr. 09_2026 - mini-projeto Full Stack"** da **bit Soluções** detalha a avaliação técnica da 2ª etapa do processo seletivo para Desenvolvedor(a) de Sistemas Júnior. A proposta consiste no desenvolvimento de um **Portal de Solicitações Internas** com backend, frontend e banco de dados SQL.

Abaixo está a estrutura do documento acompanhada do resumo de cada seção:

---

### 1. Contexto do Projeto, Prazo e Entrega

* **Resumo:** Define o objetivo do desafio: construir um sistema web onde colaboradores registram e acompanham demandas internas. O prazo final é segunda-feira, **5 de outubro de 2026**, via GitHub ou pacote por e-mail.
* **Entregáveis exigidos:** Código-fonte completo (frontend e backend), scripts SQL com dicionário de dados, Memorial Técnico de Desenvolvimento, README com guia de execução e evidências de funcionamento (prints ou vídeo opcional).

### 2. Requisitos Funcionais

Define as regras de negócio divididas em 5 módulos:

* **Autenticação:** Login (usuário/senha), controle de sessão e logout; acesso restrito a usuários autenticados.
* **Cadastro de Solicitações:** Formulário com título, descrição e categoria (ex.: TI, RH, Compras, Financeiro, Infraestrutura). Campos automáticos: data de criação, usuário solicitante e status inicial "Aberto". Permite criar, editar e excluir solicitações abertas.
* **Gerenciamento das Solicitações:** Listagem com código, título, categoria, solicitante, data e status (Aberto, Em Atendimento, Concluído), além de detalhamento e alteração de status.
* **Consulta e Filtros:** Busca por período, categoria, status e texto livre (título).
* **Dashboard:** Cards de métricas simples com total de solicitações e contagens por status (abertas, em atendimento, concluídas).

### 3. Requisitos Técnicos

* **Resumo:** Oferece total autonomia para a escolha da stack tecnológica (linguagens, frameworks e banco SQL).
* **Obrigatoriedades:**
* **Backend:** API REST/dados, regras de negócio, tratamento de erros e validações.
* **Frontend:** Interface integrada consumindo a API, contendo formulários e listagens.
* **Banco de Dados:** Scripts SQL (ou equivalente) para recriar toda a estrutura necessária.



### 4. Critérios de Avaliação

* **Resumo:** Avalia não apenas se o sistema funciona, mas a maturidade do candidato. Os eixos avaliados são:
1. *Funcionamento da Solução* (conformidade com os requisitos);
2. *Organização do Código* (arquitetura de pastas, convenções e clareza);
3. *Boas Práticas* (validações, tratamento de exceções, segurança básica);
4. *Estrutura do Backend* (camadas, endpoints, modelagem);
5. *Estrutura do Frontend* (componentização, UX, navegação);
6. *Documentação* (clareza do README e profundidade do Memorial Técnico).



### 5. Memorial Técnico Obrigatório

* **Resumo:** Documento conceitual indispensável para justificar o processo decisório. Deve conter:
* **Tecnologias Utilizadas:** Lista completa de ferramentas, bibliotecas e bancos.
* **Justificativa Técnica:** Motivo da escolha, prós/contras em relação a alternativas e impacto em escalabilidade/produtividade.
* **Justificativa Conceitual:** Padrões de arquitetura, camadas, modelagem de dados, autenticação e comunicação client-server.
* **Análise Crítica:** Limitações conhecidas, pontos de melhoria e o que seria feito diferente em um cenário corporativo real.



### 6. Requisitos de Deploy & README

* **Resumo:** Exige que a aplicação rode sem necessidade de ajustes manuais imprevistos pelo avaliador. O README precisa detalhar pré-requisitos, passo a passo de instalação (banco, backend, frontend), variáveis de ambiente, comandos de inicialização e credenciais de usuários para teste.

### 7. Diferenciais e Considerações Finais

* **Resumo:** Lista itens opcionais que agregam valor: Docker / Docker Compose, testes automatizados, CI/CD e layout responsivo. Conclui enfatizando que a clareza, coerência e capacidade de documentar são mais valorizadas do que a complexidade desnecessária de código.