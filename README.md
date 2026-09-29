# Pack-it-flawlessly Backend

Spring Boot REST API for managing packages and their contents. The code is organized by responsibility under `dev.noby.packit`: controllers, DTOs, entities, mapper, repository, services, security, configuration, and exceptions.

## Run locally

Requires Java 21.

```bash
./mvnw spring-boot:run
```

The API listens on port `8888` by default. H2 uses a file database (`./packit-db.mv.db`) so data survives application restarts. Set `PACKIT_DB_PATH` to change the database file location. The database file is ignored by Git.

The Cognito issuer and CORS origins can be changed with `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI` and `PACKIT_CORS_ALLOWED_ORIGINS`. The latter accepts a comma-separated list; local development defaults to `http://localhost:4200`.

## API routes

| Method | Route | Permission |
|---|---|---|
| `GET` | `/api/v1/packages` | `packit-api/read` or `packit-api/manage` |
| `POST` | `/api/v1/packages` | `packit-api/manage` |
| `GET` | `/api/v1/packages/{packageId}` | `packit-api/read` or `packit-api/manage` |
| `PUT` | `/api/v1/packages/{packageId}` | `packit-api/manage` |
| `PATCH` | `/api/v1/packages/{packageId}/status` | `packit-api/manage` |
| `GET` | `/api/v1/packages/{packageId}/items` | `packit-api/read` or `packit-api/manage` |
| `POST` | `/api/v1/packages/{packageId}/items` | `packit-api/manage` |
| `PUT` | `/api/v1/packages/{packageId}/items/{itemId}` | `packit-api/manage` |
| `GET` | `/api/v1/dashboard/summary` | `packit-api/read` or `packit-api/manage` |

Packages use a soft-delete lifecycle: set status to `ARCHIVED`. Status progression is `RECEIVED → PREPARING → READY → IN_TRANSIT → DELIVERED`; any non-archived package can be archived. Archived packages cannot be edited or reactivated.

## Authentication

Spring Security runs as an OAuth2 resource server and validates Cognito access tokens. Read routes accept either the read or manage scope so an administrator with god mode can read as well as write. Mutation routes require manage. API Gateway should use the same scope policy on its methods, while continuing to validate the JWT at the gateway boundary.
