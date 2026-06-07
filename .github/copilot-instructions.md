<!-- Auto-generated guidance for AI coding agents. -->
# Copilot instructions for this repository

This file helps AI coding agents become productive quickly in this Kotlin project. The repository currently contains no discoverable source files, so these instructions focus on expected locations, reconnaissance steps, and safe defaults to follow when implementing or modifying code.

1. Quick reconnaissance
- Look for build system files in repository root: `gradlew`, `gradlew.bat`, `build.gradle`, `build.gradle.kts`, `settings.gradle.kts`.
- Check source layouts: `src/main/kotlin`, `src/main/java`, `src/test`.
- Look for infra or packaging: `Dockerfile`, `docker-compose.yml`, `k8s/`, `deployment/`.
- If this is an Android module, check `app/src/main/AndroidManifest.xml` and `build.gradle` inside `app/`.

2. Common commands (try these after locating the build files)
- Gradle wrapper (preferred when present): `./gradlew build`, `./gradlew test`, `./gradlew run`.
- Gradle (system): `gradle build` (only if wrapper missing).
- Run unit tests: `./gradlew test`.

3. Project-specific heuristics for 'forward-sms'
- Expect core components under `src/main/kotlin`, e.g. packages like `com.example.forward` or `sms.forward`.
- Search for classes or files containing `Forward`, `Sms`, `Gateway`, `Service`, `Controller` to find the forwarding pipeline.
- Look for configuration in `resources` (e.g., `src/main/resources/application.yml` or `application.properties`) describing upstream/downstream endpoints.

4. Integration and external dependencies
- Inspect `build.gradle(.kts)` for external libs (HTTP clients, messaging, Twilio, etc.).
- If secrets or credentials are required, prefer reading `.env` or environment-specific files and do NOT commit credentials.

5. Coding conventions & patterns to follow
- Prefer idiomatic Kotlin: use data classes for simple DTOs, use coroutines (`suspend`) for async flows if present elsewhere in the codebase.
- Follow existing package structure and naming; change only when refactoring consistently across the project.

6. PR & testing guidance for agents
- Run `./gradlew test` before proposing code changes.
- When adding runtime/config changes, include local-run instructions in the PR description (which command to run and which env vars are required).

7. If repository is empty or missing key files
- Ask the repository owner: which build system/Gradle variant, main package name, and any service credentials or external endpoints to mock.
- Propose a minimal runnable structure when asked: `build.gradle.kts`, `gradlew` wrapper, `src/main/kotlin`, `src/test` and a README with run instructions.

8. Helpful file references for agents
- Check for: `build.gradle.kts`, `gradlew`, `src/main/kotlin`, `src/test`, `src/main/resources/application.yml`, `Dockerfile`.

9. Contact & escalation
- If unsure about architecture or you would need secrets/config to proceed, open an issue or request the owner to provide the missing artifacts.

If you'd like, I can (A) generate a minimal Kotlin Gradle project scaffold for `forward-sms`, or (B) wait for you to push existing sources and then merge/update this file with concrete examples. Which do you prefer?
