# AGENTS Guide

## Project Scope
- This repo is a small Java 21 playground implementing core Reactive Streams interfaces manually (`Publisher`, `Subscriber`, `Subscription`).
- Main entrypoint is `src/main/java/com/github/raphaelfontoura/Main.java`; active scenario is controlled by uncommenting one `demo*` method call.

## Architecture (Read These First)
- `src/main/java/com/github/raphaelfontoura/publisher/PublisherImpl.java`
  - `PublisherImpl` only wires subscriber to a new `SubscriptionImpl` via `subscriber.onSubscribe(...)`.
- `src/main/java/com/github/raphaelfontoura/publisher/SubscriptionImpl.java`
  - Holds state (`count`, `isCancelled`) and enforces backpressure-like limits (`MAX_ITEMS = 10`).
  - `request(long requested)` is the core behavior: validates request size, emits fake emails, completes at max, and cancels on terminal paths.
- `src/main/java/com/github/raphaelfontoura/subscriber/SubscriberImpl.java`
  - Logs lifecycle callbacks and exposes `getSubscription()` so demos can call `request()`/`cancel()`.
- `src/main/java/com/github/raphaelfontoura/Main.java`
  - `demo1..demo4` are behavior scenarios: simple subscribe, incremental requests, cancel flow, and validation error (`request(11)`).

## Runtime/Data Flow
- Flow is: `Main` -> `PublisherImpl.subscribe(...)` -> `Subscriber.onSubscribe(subscription)` -> `SubscriptionImpl.request(n)` -> `Subscriber.onNext/onError/onComplete`.
- Items are generated with `javafaker` (`faker.internet().emailAddress()`), not from external services or persistence.

## Build, Run, and Verify
- Build classes: `mvn clean compile`
- Run demos (preferred): `mvn exec:java -Dexec.mainClass="com.github.raphaelfontoura.Main"`
- Run tests: `mvn test` (test tree exists but may be minimal/empty initially).
- Logging is configured in `src/main/resources/logback.xml` (root `INFO`, stdout pattern).

## Project-Specific Conventions
- Implementation class naming uses `*Impl` suffix (`PublisherImpl`, `SubscriptionImpl`, `SubscriberImpl`).
- Package layout is role-based: `publisher` and `subscriber` packages under `com.github.raphaelfontoura`.
- Side effects are intentionally visible through SLF4J logs (`log.info` in subscribe/request/cancel/complete paths).
- Error path in this playground is explicit runtime validation failure (`new RuntimeException("validation failed")`) when request exceeds max.

## Dependencies and Integration Points
- Dependency management uses Reactor BOM in `pom.xml` (`io.projectreactor:reactor-bom:2024.0.1`).
- Reactive interfaces come from `org.reactivestreams` API; this code does not use Reactor operators/Flux in the current implementation.
- Netty/Reactor Netty dependencies are present in `pom.xml` but not consumed by current source files.
- Java version is pinned to 21 in `pom.xml` (`<source>21</source>`, `<target>21</target>`).

## Existing AI/Agent Instructions Discovery
- One glob search was run for common AI instruction files (`AGENTS.md`, `CLAUDE.md`, `.cursorrules`, `README.md`, etc.).
- No matching files were found before creating this `AGENTS.md`.

