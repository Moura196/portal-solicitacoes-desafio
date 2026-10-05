# Portal de Solicitações

**Versão:** 1.0.0
**Status:** Release Inicial

O **Portal de Solicitações** é uma aplicação web Full Stack desenvolvida para gerenciar, visualizar e acompanhar solicitações em tempo real. O sistema visa oferecer uma interface limpa, responsiva e de alta performance, aliada a um backend robusto capaz de processar as regras de negócio de forma eficiente.

---

## 🛠 Pré-requisitos

Para executar este projeto localmente, você precisará ter instalado em sua máquina:

*   **Linguagem Backend:** [Java 17](https://jdk.java.net/17/)
*   **Framework Backend:** [Spring Boot](https://spring.io/projects/spring-boot) (com Maven)
*   **Linguagem/Framework Frontend:** [Node.js](https://nodejs.org/) (v18+) e [Angular CLI](https://angular.io/cli) (v21)
*   **Banco de Dados:** [PostgreSQL](https://www.postgresql.org/) (v14+)
*   **(A ser implementado):** [Docker](https://www.docker.com/) para subir o banco de dados facilmente.

---

## ⚙️ Configuração

### Variáveis de Ambiente (Backend)
O backend em Spring Boot precisa se conectar ao banco de dados PostgreSQL. Configure as variáveis no arquivo `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/portal_solicitacoes
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=validate
# O Flyway cuidará da estrutura das tabelas automaticamente (já está configurado no projeto)
```

### Credenciais de Demonstração
> O sistema possui bloqueio de rotas e segurança completa via JWT. Para acessar e avaliar, utilize as credenciais pré-cadastradas automaticamente pelas migrações do Flyway:
*   **E-mail:** avaliador@bitsolucoes.com
*   **Senha:** 123456

---

## 🚀 Instalação e Execução

### 1. Banco de Dados
Certifique-se de que o PostgreSQL está rodando na porta padrão (`5432`) e o banco de dados `portal_solicitacoes` foi criado.

### 2. Backend (Java - Spring Boot)
Abra um terminal na pasta `/backend`:

```bash
# 1. Instalar as dependências e compilar o projeto
mvn clean install -DskipTests

# 2. Executar a aplicação (O Flyway criará as tabelas automaticamente)
mvn spring-boot:run
```
O Backend estará rodando e disponível em: `http://localhost:8080`.

### 3. Frontend (Angular)
Abra um terminal na pasta `/frontend`:

```bash
# 1. Instalar as dependências do Node
npm install

# 2. Iniciar o servidor de desenvolvimento
ng serve
```
O Frontend estará rodando e disponível em: `http://localhost:4200`.

---

## 🔒 Módulo de Autenticação

A segurança do projeto está totalmente operacional de ponta a ponta:
*   **Backend:** Autenticação stateless via **JWT (JSON Web Tokens)** e Spring Security, com filtro (`SecurityFilter`) validando as requisições privadas.
*   **Frontend:** Protegido via `AuthGuard` no Angular, mantendo estado reativo da sessão.
*   **Integração:** `AuthInterceptor` configurado para anexar o cabeçalho `Authorization: Bearer <token>` automaticamente em todas as chamadas à API.

---

## ✨ Diferenciais Implementados

Nesta primeira entrega, os seguintes diferenciais arquiteturais e visuais foram implementados:

*   ✅ **Responsividade Avançada com CSS Puro:** Não utilizamos bibliotecas como Bootstrap ou Tailwind. Todo o sistema de design (variáveis, grid, flexbox, responsividade) foi criado "do zero", desenvolvendo domínio de UI/UX e CSS.
*   *(Opcional)* Docker e Docker Compose (Em processo de estruturação).
*   *(Opcional)* Testes Automatizados Unitários (Em processo de estruturação).
*   *(Opcional)* CI/CD (GitHub Actions) (Em processo de estruturação).

---
*Documentação desenvolvida para a avaliação técnica. Consulte o Memorial Técnico para entender as decisões arquiteturais.*
