# Memorial Técnico de Desenvolvimento

**Projeto:** Portal de Solicitações
**Versão:** 1.0.0

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
*   **Frontend (Feature-Driven):** Organizado em pastas bem definidas: `/core` (layout como a Navbar, modelos e serviços globais), `/shared` (componentes reutilizáveis como o `solicitacao-card`) e `/features` (os módulos de negócio independentes, como `home`, `consulta-solicitacao` e `nova-solicitacao`).

### 3.4. Tratamento de Exceções Global (Backend)
Para garantir uma API robusta e padronizada, implementamos um `GlobalExceptionHandler` utilizando a anotação `@RestControllerAdvice`. Isso permite capturar qualquer erro lançado pela aplicação de forma centralizada e devolver um objeto JSON limpo e padronizado para o frontend, emulando o padrão *Problem Details*.

---

## 4. Estratégia de Autenticação (Planejamento)

A versão 1.0.0 foi desenhada com foco na estrutura e no domínio visual da aplicação, postergando o bloqueio de acessos. No entanto, a arquitetura já prevê o encaixe do módulo de segurança:

1.  **Backend:** Será implementado o `Spring Security`. Um filtro interceptará requisições, validando um Token JWT. O usuário e suas permissões (Roles) estarão modelados no PostgreSQL.
2.  **Frontend:** Um `HttpInterceptor` adicionará automaticamente o cabeçalho `Authorization: Bearer <token>` em todas as chamadas. A proteção de telas se dará através da interface `CanActivate` nas rotas do Angular.

Essa etapa iniciará o ciclo de desenvolvimento da branch `feature/auth` para a versão `1.1.0`.

---

## 5. Análise Crítica

Em um processo de avaliação honesta, reconheço as seguintes limitações nesta entrega inicial e as melhorias que seriam implementadas em um cenário corporativo de produção real:

*   **Ausência de Testes Automatizados:** O foco inicial foi a entrega de valor funcional. Em um ambiente corporativo, a adoção de TDD ou cobertura via JUnit (Backend) e Jasmine/Jest (Frontend) é mandatória antes de qualquer *merge* para a branch principal.
*   **Estratégia de Cache e Paginação:** Atualmente, a busca retorna conjuntos inteiros de dados. É imperativo implementar Paginação (*Pageable* do Spring Data) na API e cache (ex: Redis) para endpoints de leitura frequente visando escalabilidade para milhões de registros.
*   **Pipelines de CI/CD:** A construção e o deploy estão manuais. O próximo passo de infraestrutura seria a criação de rotinas no GitHub Actions para garantir a execução de *linters*, testes e build automatizado em containers Docker (com o Dockerfile e docker-compose.yml que serão implementados).

O projeto, em sua concepção atual (1.0.0), cumpre rigorosamente os requisitos fundamentais de estruturação, qualidade de código e domínio tecnológico exigidos.
