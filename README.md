# PR Guard (Phase 1)

PR Guard is a GitHub App backend foundation that will eventually review pull requests for risky AI-generated backend code.

## Public repository secret policy
- Never commit real secrets.
- Never commit `.env` or private key files.
- Never log secrets, signatures, auth headers, or raw payloads.
- Phase 1 stores only webhook metadata, not full webhook bodies.

## Environment variables
| Variable | Required in phase 1 | Notes |
|---|---|---|
| SPRING_PROFILES_ACTIVE | no | local/test/prod |
| SERVER_PORT | no | default 8080 |
| APP_BASE_URL | yes | app base URL |
| SPRING_DATASOURCE_URL | yes | PostgreSQL JDBC URL |
| SPRING_DATASOURCE_USERNAME | yes | DB user |
| SPRING_DATASOURCE_PASSWORD | yes | DB password |
| PRGUARD_GITHUB_WEBHOOK_SECRET | yes in prod | webhook HMAC secret |
| PRGUARD_REQUIRE_WEBHOOK_SIGNATURE | yes in prod | must not be false in prod |
| PRGUARD_GITHUB_APP_ID | no | placeholder |
| PRGUARD_GITHUB_APP_SLUG | no | placeholder |
| PRGUARD_GITHUB_PRIVATE_KEY_BASE64 | no | placeholder |
| PRGUARD_GITHUB_API_BASE_URL | no | placeholder |
| PRGUARD_GITHUB_WEB_BASE_URL | no | placeholder |

## Local setup
1. Copy `.env.example` to `.env` and adjust fake local values.
2. Start DB: `docker compose up -d`.
3. Run app: `mvn spring-boot:run`.

## Testing
`mvn test`

## Webhook verification
`POST /api/github/webhook` verifies `X-Hub-Signature-256` using HMAC SHA-256 and shared secret.

## GitHub App setup placeholders
App ID/private key/API calls are intentionally placeholders in phase 1.

> Warning: never commit `.env`, `.pem`, `.key`, `.p8`, `.p12`, `.jks`, `.keystore`, or `secrets/` files.
