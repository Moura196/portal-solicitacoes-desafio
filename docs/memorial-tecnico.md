# Memorial Técnico de Desenvolvimento

**Projeto:** Portal de Solicitações
**Versão:** 1.1.0

Este memorial tem como objetivo demonstrar o processo decisório, as escolhas tecnológicas e arquiteturais, e a visão crítica aplicada durante o desenvolvimento da versão inicial do Portal de Solicitações.

---

## 1. Tecnologias Utilizadas

A stack tecnológica foi escolhida visando produtividade, manutenibilidade e adequação aos padrões corporativos modernos.

*   **Linguagens de Programação:**
    *   **Backend:** Java 17
    *   **Frontend:** TypeScript (ES2022+) e HTML5/CSS3 (Angular 21)
*   **Frameworks e Bibliotecas:**
    *   **Backend:** Spring Boot, Spring Data JPA, Spring WebMVC, Flyway DB e Spring Security (a ser implementado).
    *   **Frontend:** Angular 21 (Core, Common, Router).
*   **Banco de Dados:**
    *   PostgreSQL (SGBD Relacional)
*   **Gerenciamento e Build:**
    *   Maven (Backend), npm (Gerenciamento de Pacotes Frontend) e Angular CLI (Build/Execução Frontend)
*   **Controle de Versão:**
    *   Git (seguindo o padrão Git Flow)

---

## 2. Justificativa Técnica

### 2.1. Spring Boot e Java 17 (Backend)
*   **Motivo da Escolha:** O ecossistema Spring é o padrão ouro na indústria corporativa para desenvolvimento Java. A versão 17 traz suporte a LTS (Long Term Support), `records` e melhorias de performance no Garbage Collector.
*   **Benefícios:** Injeção de dependências robusta, configuração automática (AutoConfiguration) que acelera o setup, e integração nativa com o ecossistema de dados via JPA.
*   **Vantagens vs Alternativas:** Comparado a frameworks mais leves (como Express.js ou Flask), o Spring Boot fornece uma estrutura tipada, segura e desenhada para escalabilidade horizontal em arquiteturas de microsserviços.

### 2.2. Angular 21 (Frontend)
*   **Motivo da Escolha:** Framework que dita um fluxo de trabalho organizado para criação de Single Page Applications (SPA). A sigla SPA se refere à arquitetura web onde apenas uma página HTML é carregada, e o JavaScript atualiza a tela sem recarregar o navegador.
*   **Benefícios:** A versão 21 traz um novo motor de renderização (Ivy otimizado) e sintaxe de controle de fluxo mais limpa diretamente no template.
*   **Impacto na Manutenção:** A separação clara entre Template (HTML), Estilo (CSS) e Lógica (TS) facilita o trabalho em equipe. O uso de Injeção de Dependências no front-end emula as boas práticas do backend.

### 2.3. Estratégia Híbrida: Angular Material + CSS Customizado
*   **Motivo da Escolha:** Optou-se por uma mescla entre componentes prontos do Angular Material (como campos de formulário, modais, etc) e estilização com CSS puro (Variáveis e Flexbox/Grid) para o layout base.
*   **Benefícios:** O Angular Material foi escolhido para acelerar o desenvolvimento de componentes interativos e garantir acessibilidade e padrões de UI (Material Design). 
*   **Vantagens:** Essa abordagem híbrida demonstra pragmatismo. Usamos componentes prontos onde a roda não precisa ser reinventada (Angular Material), mas usamos CSS avançado (Flexbox, Grid) para garantir uma identidade visual exclusiva e uma arquitetura de estilos escalável sem depender inteiramente de frameworks como Tailwind.

### 2.4. PostgreSQL e Flyway
*   **Motivo da Escolha:** O PostgreSQL é o banco relacional open-source mais avançado do mercado. O Flyway garante o versionamento do banco de dados.
*   **Impacto:** Permite que o estado do banco evolua junto com o código (Migrations), garantindo reprodutibilidade em qualquer ambiente sem necessidade de scripts manuais propensos a erros.

---

## 3. Justificativa Conceitual e Arquitetural

### 3.1. Estrutura de Standalone Components (Angular)
Abandonamos a complexidade estrutural dos `NgModules` clássicos do Angular em favor dos **Standalone Components**. Isso reduz o código boilerplate, facilita o *lazy loading* (carregamento sob demanda) das rotas e torna a árvore de dependências de cada componente explícita em seu próprio decorador `@Component`.

### 3.2. Padrão "Live Filtering" e Signals
Na página Home (listagem do dashboard), adotamos o padrão de "Live Filtering". Para o gerenciamento de estado síncrono na interface (como os filtros digitados), priorizamos o uso de **Angular Signals**.
*   **O porquê:** Embora continuemos usando `Observables` (RxJS) para a comunicação assíncrona com a API (ex: `HttpClient`), os Signals são superiores para o estado da *interface gráfica*. Eles oferecem reatividade síncrona, não precisam de `.subscribe()` direto no TypeScript e não causam vazamento de memória se esquecermos de desinscrevê-los. O estado da interface reage automaticamente e com alta performance às mudanças.

### 3.3. Organização em Camadas (Backend e Frontend)
*   **Backend (Arquitetura em N-Camadas):** Adotamos a clássica separação `Controller` (Interface HTTP) -> `Service` (Regras de Negócio) -> `Repository` (Acesso a Dados). Isso garante o princípio da Responsabilidade Única (SRP).
*   **Frontend (Feature-Driven):** Organizado em pastas bem definidas: `/core` (layout como a Navbar, modelos e serviços globais), `/shared` (componentes reutilizáveis como o `solicitacao-card` e modais padronizados) e `/features` (os módulos de negócio independentes, como `home`, `consulta-solicitacao`, `login` e `nova-solicitacao`).

### 3.4. Experiência do Usuário (UX) e Notificações Globais
Para garantir uma navegação fluida, criamos um `NotificationService` injetável em toda a aplicação utilizando o `MatSnackBar`. 
*   **Tratamento Amigável de Erros:** O serviço abstrai as respostas do backend, transformando códigos HTTP de validação (400) em toasts na tela com mensagens compreensíveis. 
*   **Feedback Imediato:** Modais de criação e detalhamento se comunicam via `dialogRef.close(true)`. Ao confirmar a alteração, a listagem reage imediatamente consumindo a API novamente no background (Live Reloading), dispensando atualizações manuais de página (F5) pelo usuário. O uso de modais de confirmação padronizados (`ConfirmDialogComponent`) assegura que ações destrutivas (ex: deletar solicitação) tenham fricção adequada.

### 3.5. Tratamento de Exceções Global (Backend)
Para garantir uma API robusta e padronizada, implementamos um `GlobalExceptionHandler` utilizando a anotação `@RestControllerAdvice`. Isso permite capturar qualquer erro lançado pela aplicação de forma centralizada e devolver um objeto JSON limpo e padronizado para o frontend, emulando o padrão *Problem Details*.

### 3.6. Boas Práticas de Injeção de Dependências (SOLID)
Por toda a aplicação (incluindo Controllers, Services e módulos de Segurança), a injeção de dependências via campos (`@Autowired`) foi evitada. Adotou-se, de forma padronizada, a **Injeção via Construtor** com variáveis `final`. Esta decisão de design arquitetural favorece o princípio de imutabilidade, tornando explícitas as dependências obrigatórias das classes e facilitando imensamente a criação de cenários de testes unitários sem a necessidade de frameworks de reflexão.

---

## 4. Estratégia de Autenticação

A segurança da aplicação foi implementada utilizando **Spring Security** em conjunto com **Tokens JWT (JSON Web Token)**.

1.  **Backend (Filtro e Autenticação):** Desenvolvemos um filtro customizado (`SecurityFilter`) que intercepta requisições, valida a presença e a integridade do JWT, e autentica o usuário no contexto do Spring (`SecurityContextHolder`). Isso mantém a aplicação *stateless* e escalável.
2.  **Gestão de Segredos (Decisão Arquitetural):** O `TokenService` utiliza a propriedade `api.security.token.secret` para assinar os tokens. Optou-se por definir um valor default diretamente via anotação (`@Value("${api.security.token.secret:my-secret-key-super-secure}")`). **Justificativa técnica:** Para fins de avaliação técnica e execução local, essa abordagem permite que o avaliador rode a aplicação sem a necessidade de configurar variáveis de ambiente na sua máquina. Em um cenário de Produção corporativo, a boa prática mandaria remover o fallback da anotação e injetar essa chave estritamente através de variáveis de ambiente seguras (ex: AWS Secrets Manager, GitHub Secrets ou `.env`), blindando o algoritmo de assinatura.
3.  **Tratamento de Exceções no Nível de Filtro:** Problemas comuns de JWT (como tokens expirados ou malformados) estouram no nível do filtro (antes de chegar aos Controllers). Para não perder o padrão de resposta da API, injetamos o `HandlerExceptionResolver` diretamente no filtro. Dessa forma, as exceções geradas no filtro são delegadas e tratadas de forma padronizada pelo `GlobalExceptionHandler`, retornando respostas JSON consistentes com HTTP Status 401.
4.  **Configurações de Proteção e CORS:** O modelo de sessão foi explicitamente configurado como `STATELESS` e a proteção contra CSRF foi desabilitada, uma vez que a autenticação baseada em JWT não depende de cookies de sessão (mitigando nativamente ataques CSRF). As regras de CORS foram definidas explicitamente, inclusive liberando as requisições *Preflight* (`OPTIONS`) necessárias para que os navegadores permitam chamadas vindas da aplicação Angular.
5.  **Frontend (Angular):** A autenticação no cliente é gerenciada por um `AuthService` que realiza a requisição de login e salva o Token JWT no `localStorage`. Utilizamos *Angular Signals* (`signal<boolean>`) para manter o estado reativo da sessão na interface gráfica. Protegemos as rotas privadas (como a Dashboard) utilizando um `AuthGuard` funcional. Além disso, implementamos um `AuthInterceptor` funcional que anexa o cabeçalho `Authorization: Bearer <token>` automaticamente em todas as requisições HTTP caso o usuário esteja autenticado, garantindo a comunicação segura e simplificada com a API.

---

## 5. Metodologia de Desenvolvimento Assistida por IA

O desenvolvimento deste portal contou com o suporte de ferramentas de Inteligência Artificial Generativa. Para garantir que o uso da IA não se transformasse em geração de código descontrolada ("vibe coding"), adotou-se uma abordagem rigorosa de **Engenharia de Prompt e Gestão de Agentes**:

*   **Documentação de Escopo:** Foram criados e mantidos artefatos específicos (`checklist-desenvolvimento.md`, `descricao-desafio.md`, `diretrizes-agente.md`, `requisitos-desafio.md`) para servir como base de conhecimento (contexto) e guiar o comportamento da IA.
*   **Controle Arquitetural:** O documento de diretrizes foi fundamental para impor regras como o uso de Standalone Components no Angular e a Injeção de Dependência via construtor no Spring Boot. Isso garantiu que o código gerasse soluções dentro dos padrões arquiteturais predefinidos, e não de forma aleatória.
*   **Revisão Crítica Humana:** A IA atuou como ferramenta aceleradora (pair programming), mas todas as decisões de arquitetura, fluxo de telas e aprovação do código foram estritamente arquitetadas e validadas através de intervenção humana, comprovando domínio sobre a stack tecnológica.

---

## 6. Análise Crítica

Em um processo de avaliação honesta e de melhoria contínua, destaco algumas observações sobre a arquitetura e as entregas deste projeto:

*   **Implementação e Curva de Aprendizado em Testes Unitários:** Para garantir a confiabilidade das regras de negócio e da segurança, foi desenvolvida uma suíte de testes unitários tanto no Backend (focando em *Services*, *Controllers* e *Security* via JUnit/Mockito) quanto no Frontend (focando em *Services*, *Guards* e *Interceptors* via Vitest). Reconheço, contudo, que a automação e a elaboração minuciosa de testes representam uma área de estudo onde ainda possuo muito espaço para crescimento. A implementação atual serviu como um excelente laboratório prático para lidar com *Mocks* e *Spies*, e meu objetivo a curto prazo é aprofundar meus conhecimentos nessas práticas para desenvolver uma intuição ainda mais forte (como o TDD) na construção de aplicações complexas e resilientes.
*   **Pipeline e DevOps (Próximos Passos):** A conteinerização e a automação de pipelines representam a fronteira final para simular perfeitamente um cenário corporativo.
*   **Estratégia de Cache e Paginação:** Atualmente, a busca retorna conjuntos inteiros de dados. É imperativo implementar Paginação (*Pageable* do Spring Data) na API e cache (ex: Redis) para endpoints de leitura frequente visando escalabilidade para milhões de registros.
*   **Pipelines de CI/CD:** A construção e o deploy estão manuais. O próximo passo de infraestrutura seria a criação de rotinas no GitHub Actions para garantir a execução de *linters*, testes e build automatizado em containers Docker (com o Dockerfile e docker-compose.yml que serão implementados).
*   **Evolução no Uso de IA e Compartilhamento de Contexto:** Embora a base de conhecimento auxiliar tenha guiado o desenvolvimento individual com sucesso, o próximo passo para escalar a equipe seria padronizar essas diretrizes em arquivos nativos de repositório (como `.github/copilot-instructions.md` ou `.cursorrules`). Além disso, poderíamos integrar essas regras arquiteturais em bots de *AI Code Review* no pipeline, garantindo que o código gerado por qualquer desenvolvedor do time passe por um crivo automático antes do merge.
*   **Maturidade no Versionamento de Código:** Atualmente o projeto segue o modelo *Git Flow*, que é excelente para releases muito estruturadas. Contudo, em um cenário focado em entregas contínuas e de alta cadência (*Continuous Deployment*), uma evolução arquitetural seria a transição para **Trunk-Based Development** aliado ao uso de *Feature Flags*. Complementar a isso, a integração de ferramentas de *Semantic Release* poderia automatizar o versionamento e a geração de *Changelogs* utilizando as tags de *Conventional Commits* (ex: `feat:`, `docs:`) já praticadas neste repositório.

O projeto, em sua concepção atual (1.1.0), cumpre rigorosamente os requisitos fundamentais de estruturação, qualidade de código e domínio tecnológico exigidos.
