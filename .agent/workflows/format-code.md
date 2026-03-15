---
description: Steps to format the codebase to standard conventions.
---
# CareMatch Format Code

When asked to "format code" or prepare the code for PR, do the following:

1. Check current uncommitted changes via `git status`.
// turbo
2. Format the codebase using spotless plugin:
   `./gradlew spotlessApply`
3. Verify the formatting completed successfully and stage the changes with Git if requested by the user.
