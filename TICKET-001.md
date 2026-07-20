# TICKET-001: Endpoints de Tasks

**Reportado por:** time de produto
**Prioridade:** média

## Descrição

Os usuários do TaskFlow conseguem criar projetos mas não conseguem gerenciar as tarefas
dentro deles. Precisamos da API básica de tasks pra o time de frontend começar a integrar
essa semana.

## O que a gente precisa

- Criar uma task dentro de um projeto
- Listar as tasks de um projeto
- Atualizar uma task (o frontend principalmente precisa poder mudar o status, tipo
  arrastar um card de "a fazer" pra "em andamento")
- Deletar uma task
- Buscar uma task específica por id

Segue o mesmo padrão de código que já existe no módulo de projects (Service + Controller
+ DTO de request).

## Observações do PO

- Não esquece que toda task pertence a um projeto — se o projeto não existir, não faz
  sentido criar a task
- Alguém perguntou se dá pra atribuir uma task pra um usuário (assignee). Acho que sim,
  mas não é bloqueante pra essa entrega
- Já tivemos um bug em outro serviço onde a gente deixava criar recurso peça apontando
  pra owner_id que não existia no banco e isso gerou um `NullPointerException` estranho
  duas semanas depois. Cuidado com casos assim aqui também.

Sem mais detalhes por enquanto — se tiver dúvida de formato de resposta, endpoint, etc,
usa teu critério e segue o padrão do resto do projeto.
