# Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.
O formato baseia-se no [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/), 
e este projeto adere ao [Semantic Versioning](https://semver.org/lang/pt-BR/).

## [1.1.0] - 2026-10-05

### Added
- Implementação completa de Autenticação JWT (Backend e Frontend).
- Criação da interface de Login e Guards de rotas no Angular.
- Sistema de notificações globais amigáveis (Toasts/Snackbars).
- Banner de feedback visual para filtros ativos na página inicial.
- Recurso de colapsar (acordeão) a coluna de solicitações 'Concluídas'.

### Changed
- Refatoração da UX no detalhamento de solicitações (bloqueio de botões e transições de status).
- Criação de modais de confirmação de exclusão padronizados.

### Fixed
- Correção na formatação ISO e fuso horário do filtro de datas para evitar divergências na busca da API.

## [1.0.0] - 2026-10-04 (Release Inicial)

### Added
- Dashboard principal com listagem de solicitações e métricas (cards).
- Sistema de "Live Filtering" na tabela utilizando Signals.
- Navbar responsiva com menu de navegação fluido.
- Layout base da aplicação construído com CSS puro (Variáveis e Flexbox/Grid).
- Estrutura completa de Backend em Spring Boot (Java) conectada ao banco de dados PostgreSQL.
- Estrutura completa de Frontend em Angular 17+ (Standalone Components e Signals).

### Changed
- Refatoração da estilização global para utilizar Design System e tokens de cores.
