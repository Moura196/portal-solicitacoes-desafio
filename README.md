# Portal de Solicitações

**Versão:** 1.2.0
**Status:** Release Final

O **Portal de Solicitações** é uma aplicação web Full Stack desenvolvida para gerenciar, visualizar e acompanhar solicitações em tempo real. O sistema visa oferecer uma interface limpa, responsiva e de alta performance, aliada a um backend robusto capaz de processar as regras de negócio de forma eficiente.

---

## 🛠 Pré-requisitos

Para executar este projeto localmente, você pode utilizar duas abordagens. Para a mais rápida, basta ter:

*   **Docker e Docker Compose** instalados.

Caso prefira rodar manualmente sem Docker, você precisará de:
*   **Linguagem Backend:** [Java 17](https://jdk.java.net/17/)
*   **Framework Backend:** [Spring Boot](https://spring.io/projects/spring-boot) (com Maven)
*   **Linguagem/Framework Frontend:** [Node.js](https://nodejs.org/) (v18+) e [Angular CLI](https://angular.io/cli) (v21)
*   **Banco de Dados:** [PostgreSQL](https://www.postgresql.org/) (v14+)

---

## ⚙️ Configuração & Credenciais de Demonstração

> O sistema possui bloqueio de rotas e segurança completa via JWT. Para acessar e avaliar, utilize as credenciais pré-cadastradas automaticamente pelas migrações do Flyway:
*   **E-mail:** avaliador@bitsolucoes.com
*   **Senha:** 123456

---

## 🚀 Instalação e Execução

### Opção 1: Via Docker (Recomendado)
Para uma execução sem configurações adicionais na máquina do avaliador:
1. Abra um terminal na pasta raiz do projeto (onde está o arquivo `docker-compose.yml`).
2. Execute o comando:
```bash
docker-compose up -d --build
```
Isso iniciará o Banco de Dados, compilará o Backend e o Frontend, servindo a aplicação nas seguintes portas:
*   **Frontend (Nginx):** `http://localhost:80`
*   **Backend (Spring Boot):** `http://localhost:8080`

### Opção 2: Execução Manual

#### 1. Banco de Dados
Certifique-se de que o PostgreSQL está rodando na porta padrão (`5432`) e o banco de dados `portal_solicitacoes_db` foi criado com usuário `root` e senha `password` (conforme `application.properties`).

#### 2. Backend (Java - Spring Boot)
Abra um terminal na pasta `/backend`:
```bash
mvn clean install -DskipTests
mvn spring-boot:run
```
O Backend estará rodando em: `http://localhost:8080`.

#### 3. Frontend (Angular)
Abra um terminal na pasta `/frontend`:
```bash
npm install --legacy-peer-deps
ng serve
```
O Frontend estará rodando em: `http://localhost:4200`.

---

## 🔒 Módulo de Autenticação

A segurança do projeto está totalmente operacional de ponta a ponta:
*   **Backend:** Autenticação stateless via **JWT (JSON Web Tokens)** e Spring Security, com filtro (`SecurityFilter`) validando as requisições privadas.
*   **Frontend:** Protegido via `AuthGuard` no Angular, mantendo estado reativo da sessão.
*   **Integração:** `AuthInterceptor` configurado para anexar o cabeçalho `Authorization: Bearer <token>` automaticamente.

---

## ✨ Diferenciais Implementados

Nesta entrega final (1.2.0), todos os diferenciais solicitados foram implementados com foco na qualidade corporativa:

*   ✅ **Responsividade Avançada com CSS Puro:** Sistema de design desenvolvido "do zero" (variáveis, flexbox, CSS moderno).
*   ✅ **Testes Automatizados Unitários:** Cobertura de testes implementada nos *Services*, *Controllers* (Backend) via JUnit/Mockito, e no Frontend (*Interceptors*) via Vitest.
*   ✅ **Docker e Docker Compose:** Ambientes isolados (Multi-stage build) orquestrados de forma limpa.
*   ✅ **CI/CD (GitHub Actions):** Pipeline configurado (`build-and-test.yml`) no push para rodar todos os testes automaticamente e garantir integração contínua.

---
*Documentação desenvolvida para a avaliação técnica. Consulte o Memorial Técnico para entender as decisões arquiteturais.*
