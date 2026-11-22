# Task Manager API

A REST API for managing projects and the tasks that belong to them, supporting full create, read, update and delete operations plus filtering of tasks by project and status. It demonstrates a layered Spring Boot design with JPA persistence, request validation, and consistent JSON error responses.

## Tech stack

- Java 17
- Spring Boot 4 (Web, Data JPA, Validation)
- H2 in-memory database
- Maven

## Architecture

Requests flow from controller to service to repository: controllers handle HTTP, services hold the business logic and transactions, and repositories talk to the database through Spring Data JPA. Separate request and response DTOs keep the API contract independent from the JPA entities, which avoids exposing internal fields and sidesteps lazy-loading problems when serialising related entities (a task returns `projectId` and `projectName` instead of the whole `Project`). A global exception handler turns exceptions such as "not found" and validation failures into consistent JSON error responses.

## Running it

```
mvn spring-boot:run
Requires Java 17 and Maven. Data is stored in an in-memory H2 database, so it resets every time the app restarts.
```

The API starts on http://localhost:8080.

## API endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/projects | List all projects |
| GET | /api/projects/{id} | Get a single project by id |
| POST | /api/projects | Create a new project |
| PUT | /api/projects/{id} | Update an existing project's name and description |
| DELETE | /api/projects/{id} | Delete a project (returns 204 No Content) |
| GET | /api/tasks | List tasks, optional ?projectId= and ?status= filters |
| GET | /api/tasks/{id} | Get a single task by id |
| POST | /api/tasks | Create a task in a project (status defaults to TODO, priority to MEDIUM) |
| PUT | /api/tasks/{id} | Update an existing task |
| DELETE | /api/tasks/{id} | Delete a task (returns 204 No Content) |

## Example requests

Create a project:

```
curl -X POST http://localhost:8080/api/projects \
  -H "Content-Type: application/json" \
  -d '{"name":"Portfolio","description":"Demo"}'
```

```json
{
  "id": 1,
  "name": "Portfolio",
  "description": "Demo",
  "createdAt": "2026-11-22T07:35:00.852745869"
}
```

Create a task in that project:

```
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Design homepage mockup", "description": "Wireframes and visual design", "priority": "HIGH", "dueDate": "2026-12-05", "projectId": 1}'
```

```json
{
  "id": 1,
  "title": "Design homepage mockup",
  "description": "Wireframes and visual design",
  "status": "TODO",
  "priority": "HIGH",
  "dueDate": "2026-12-05",
  "createdAt": "2026-11-22T07:36:12.204518331",
  "projectId": 1,
  "projectName": "Portfolio"
}
```

Filter tasks by project and status:

```
curl "http://localhost:8080/api/tasks?projectId=1&status=TODO"
```

```json
[
  {
    "id": 1,
    "title": "Design homepage mockup",
    "description": "Wireframes and visual design",
    "status": "TODO",
    "priority": "HIGH",
    "dueDate": "2026-12-05",
    "createdAt": "2026-11-22T07:36:12.204518331",
    "projectId": 1,
    "projectName": "Portfolio"
  }
]
```

## Error responses

**404 Not Found** (`curl -i localhost:8080/api/projects/99`):

```
HTTP/1.1 404
Content-Type: application/json
```

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Project not found with id: 99",
  "fieldErrors": null,
  "timestamp": "2026-11-22T07:35:00.963076844"
}
```

Creating a task for a project that doesn't exist also returns a 404 (`POST /api/tasks` with `{"title":"Orphan","projectId":99}`) with the same message.

**400 Bad Request** (`POST /api/projects` with a missing name):

```
HTTP/1.1 400
Content-Type: application/json
```

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "fieldErrors": {
    "name": "Project name is required"
  },
  "timestamp": "2026-11-22T07:35:00.982261791"
}
```

## Possible next steps

- Integration tests with MockMvc
- Pagination on the list endpoints
- PostgreSQL for persistent storage
- JWT authentication
