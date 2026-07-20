# TaskFlow API

API de gerenciamento de projetos e tarefas. Backend em Java 17 + Spring Boot 3.

## Como rodar

Pré-requisitos: JDK 17+ e Maven instalados (ou use o wrapper `./mvnw` se preferir gerar um).

```bash
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`. O H2 já vem populado com 2 usuários e 2 projetos
(veja `src/main/resources/data.sql`). Console do banco em `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:taskflow`, user `sa`, senha em branco).

## Estrutura do projeto

```
model/       -> entidades JPA
repository/  -> interfaces Spring Data
service/     -> regras de negócio
controller/  -> endpoints REST
dto/         -> objetos de request/response
exception/   -> exceções customizadas + handler global
```

## O que já existe (módulo de Projects)

CRUD completo de projetos em `/api/projects`. Use esse módulo como referência de padrão
de código do time: como o Service trata erros, como o Controller retorna status HTTP,
como a validação é feita via DTO com Bean Validation.

- `GET /api/projects` — lista todos
- `GET /api/projects/{id}` — busca por id (404 se não existir)
- `POST /api/projects` — cria (valida `name` obrigatório)
- `PUT /api/projects/{id}` — atualiza
- `DELETE /api/projects/{id}` — remove

## O que falta (seu trabalho)

O módulo de **Tasks** só tem a entidade e o repository prontos (`model/Task.java`,
`repository/TaskRepository.java`). Service e Controller não existem ainda.

Veja `TICKET-001.md` pra sua primeira tarefa.
