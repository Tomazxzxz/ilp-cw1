# CW1 Student Template — CampusShop Order Import Service

A **starter project** for Coursework 1. The structure, DTOs, domain model, configuration, Dockerfile
and a runnable `.http` request file are provided; the **logic is left for you to implement** (search
for `TODO` / `UnsupportedOperationException`).

> Read the full task in [`../cw1 spec.md`](../cw1%20spec.md).

## What you implement

| File | What's missing |
|---|---|
| `service/OrderService.java` | Import flow, uniqueness check (409), lookups, delete |
| `mapper/OrderMapper.java` | Partner request ↔ domain ↔ response DTO, total calculation |
| `repository/FileOrderRepository.java` | JSON file persistence (`ObjectMapper` + `Path`/`Files`) |
| `controller/OrderController.java` | Already wired to the service — no work needed here; the endpoints answer once the service methods are implemented |
| `controller/GlobalExceptionHandler.java` | Exception → status-code mapping (400/404/409/500) |
| `src/test/.../*Test.java` | The unit/web tests (skeletons with `@Disabled` are provided) |

Provided for you: `model/`, `dto/`, `exception/`, `config/`, the `OrderRepository` interface,
`application.yml`, `Dockerfile`, `docker-compose.yml` and `http/order-import.http`.

## Build, run, test

```bash
./mvnw clean package          # build + run tests
./mvnw spring-boot:run        # run locally on :8080

# Docker (delivery format — see the spec)
docker build -t ilp:cw1 .
docker run -p 8080:8080 -e ORDER_STORAGE_FILE=/app/data/orders.json ilp:cw1
docker save ilp:cw1 -o ilp_submission_image.tar
```

The provided tests are annotated `@Disabled("TODO: implement")` so the project builds green from the
start — remove the annotations and fill them in as you implement each layer. Run the API checks in
`http/order-import.http` (IntelliJ HTTP Client) against the running app.

## Submission

Submit `ilp_submission_image.tar` plus the `source/` directory (all code + the `Dockerfile`). No
README is required. See the spec §8.

## Check your submission before uploading

`check_submission.sh` verifies — the same way the auto-marker does — that your
`ilp_submission_image.tar` loads as **one tagged image**, exposes port **8080**, starts as a
container on a dynamic host port and answers `GET /actuator/health`:

```bash
./check_submission.sh ilp_submission_image.tar
```

A missing backing service locally produces a **WARNING** with instructions (the marker
provides the infrastructure itself); a hard **FAIL** means the marker would fail too.
Requires Docker and `curl`; on Windows run it in Git Bash or WSL.
