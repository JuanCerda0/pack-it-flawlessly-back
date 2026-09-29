# Pack-it-flawlessly Backend

Spring Boot REST API for managing packages and their contents. The code is organized by responsibility under `dev.noby.packit`: controllers, DTOs, entities, mapper, repository, services, security, configuration, and exceptions.

## Run locally

Requires Java 21.

```bash
./mvnw spring-boot:run
```

The API listens on port `8080` by default. H2 uses a file database (`./packit-db.mv.db`) so data survives application restarts. Set `PACKIT_DB_PATH` to change the database file location. The database file is ignored by Git.

The Cognito issuer and CORS origins can be changed with `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` and `PACKIT_CORS_ALLOWED_ORIGINS`. The latter accepts a comma-separated list; local development defaults to `http://localhost:4200`.

## API routes

| Method | Route | Permission |
|---|---|---|
| `GET` | `/api/v1/packages` | Cognito group `ADMIN` or `SUPERVISOR` |
| `POST` | `/api/v1/packages` | Cognito group `ADMIN` |
| `GET` | `/api/v1/packages/{packageId}` | Cognito group `ADMIN` or `SUPERVISOR` |
| `PUT` | `/api/v1/packages/{packageId}` | Cognito group `ADMIN` |
| `PATCH` | `/api/v1/packages/{packageId}/status` | Cognito group `ADMIN` |
| `GET` | `/api/v1/packages/{packageId}/items` | Cognito group `ADMIN` or `SUPERVISOR` |
| `POST` | `/api/v1/packages/{packageId}/items` | Cognito group `ADMIN` |
| `PUT` | `/api/v1/packages/{packageId}/items/{itemId}` | Cognito group `ADMIN` |
| `GET` | `/api/v1/dashboard/summary` | Cognito group `ADMIN` or `SUPERVISOR` |

Packages use a soft-delete lifecycle: set status to `ARCHIVED`. Status progression is `RECEIVED → PREPARING → READY → IN_TRANSIT → DELIVERED`; any non-archived package can be archived. Archived packages cannot be edited or reactivated.

## Authentication

Spring Security runs as an OAuth2 resource server and validates Cognito access tokens. It maps `cognito:groups` to Spring roles: `ADMIN` can read and mutate; `SUPERVISOR` can read. The backend rejects ID tokens by checking the Cognito `token_use` claim. OAuth scopes are not used by the backend for RBAC in this milestone. API Gateway can require `packit-api/read` on methods to validate access tokens; the backend remains responsible for differentiating permissions by group.
