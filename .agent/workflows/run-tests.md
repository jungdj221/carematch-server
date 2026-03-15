---
description: Guideline for running standard unit and integration tests.
---
# CareMatch Test Workflow

When the user asks you to "run tests" or tests are required before a commit, follow these steps:

1. Identify the modified Java files and their corresponding Test classes in `src/test/java`.
2. Ensure the code compiles cleanly.
// turbo
3. Run the Gradle test command:
   `./gradlew test`
4. If tests fail, read the generated HTML report or terminal errors carefully.
5. Propose a specific fix for the failing tests to the user.
