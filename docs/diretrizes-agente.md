# Diretrizes Estratégicas para o Agente

Este documento serve como um lembrete permanente para guiar as decisões de desenvolvimento e priorização ao longo do projeto.

## Priorização de Entregas (Prazo: 05/10/2026)

Lembre-se sempre da seguinte ordem de prioridade para garantir a entrega do projeto no prazo com a maior qualidade possível:

1. **Telas Essenciais Primeiro:** Construir a base da UI/UX (Navbar, Home/Dashboard, listagem de solicitações e modais de ação). A aplicação precisa estar visualmente pronta e consumindo os endpoints que já existem.
2. **Autenticação (Requisito Obrigatório):** A autenticação (login, controle de sessão e logout) **NÃO** é um diferencial, é um requisito funcional obrigatório. Ela deve ser implementada assim que o fluxo principal das telas estiver garantido. 
    *   *Nota técnica:* Ao adicionar o Spring Security, lembre-se de configurar a cadeia de segurança para não bloquear requisições de preflight (`OPTIONS`), incluindo `http.cors(Customizer.withDefaults())`.
3. **Documentação e Entrega:** Reservar tempo de sobra no final para redigir o `README.md` detalhado e o **Memorial Técnico** (justificando arquitetura, decisões como "rotas orientadas a ação" vs REST puro, e limitações/débitos técnicos).

## Decisões Tomadas
*   O frontend deve usar componentes standalone, signals e seguir estritamente as regras de acessibilidade (WCAG AA).
*   A configuração de CORS (`@CrossOrigin(origins = "http://localhost:4200")`) será utilizada inicialmente nos controllers para destravar o desenvolvimento do frontend, e posteriormente integrada ao Spring Security.
