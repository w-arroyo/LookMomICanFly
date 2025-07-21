<div align="center">
  <img src="./logo.png" alt="LOOK MOM I CAN FLY Logo" width="700" height="150">
</div>

<p style="font-size: 1.1em; text-align: center; margin: 2em 0;">
  A senior thesis project that simulates the operations of platforms like StockX, with a heavy focus on backend architecture and clean code practices.
</p>

<div class="create-ask-container" style="max-width: 1200px; margin: 70px auto 0 auto; padding: 20px; min-height: 530px;">

## Project Overview

"LOOK MOM I CAN FLY" is a full-stack e-commerce platform built to showcase advanced programming skills, particularly in backend development. This project emulates the mechanics of a real-time resale marketplace, enabling users to buy and sell high-demand products such as sneakers and streetwear.

## Backend

[You can check the daily development process in my repository.](https://github.com/w-arroyo/LookMomICanFly_Backend) The core strength of this project lies in its robust backend, developed with a modern, scalable, and maintainable architecture using:

- **Spring Boot (Java 21)** — A high-performance backend framework used to build RESTful APIs, manage business logic, and handle all server-side operations.
- **Flyway** — Manages all version-controlled SQL database migrations, ensuring consistency.
- **Gradle** — Used as the project's build tool, optimized for performance and continuous delivery.
- **Spring Security + JWT** — Implements secure user authentication, role-based access control, and token-based session handling.
- **SonarQube** — All backend code passed Sonar's Quality Gates, ensuring code quality, maintainability, and zero critical bugs. [Check here the analysis.](https://sonarcloud.io/project/overview?id=w-arroyo_LookMomICanFly)
- **Redis** — Currently on development. Handles JWT token validation, token blacklisting (invalidation), and stores additional user session metadata -IP address, token creation timestamp...-.
- **Caffeine** — Currently on development. Caches frequently searched products in memory to reduce database load and improve response times.
- **Stripe API** — Integrates secure payment handling.
- **Twilio API** — Sends SMS notifications for real-time transaction updates.
- **Thymeleaf** — Used to send dynamic and styled HTML emails.


This backend follows industry standards and embraces clean code principles. Extensive attention was given to architecture, security, and maintainability.

## CI/CD & DevOps

- **GitHub Actions** — Configured for continuous integration and to enforce quality checks before allowing merges to the main branch.
- **Version Control** — Git used rigorously throughout the project with structured commits and pull request reviews.
- **Docker** — Containerizes the full application.

## Frontend

- **Angular 19 (TypeScript)** — Provides a modern SPA built on RxJS for reactive programming using observables. [Check it on my frontend repository.](https://github.com/w-arroyo/LookMomICanFly_Frontend)
- A lightweight JavaScript script was developed to dynamically update the status of active orders and sales.

## Database

Relational SQL database — Structured for transactional integrity and optimized query performance.

## Why This Project?

This project is the culmination of my software developing degree and was built to demonstrate my ability to architect, develop, and maintain clean and intelligent backend and frontend systems.

</div>
