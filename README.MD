# Transactions Routine
## Description

A backend application built with Java 21, Spring Boot, and MySQL, responsible for managing customer transactions and accounts.

Each cardholder (customer) owns an account that stores their personal and financial data.
Every time the customer performs an operation, a transaction is created and linked to their corresponding account.

Each transaction has a specific operation type:

- Normal purchase
- Withdrawal
- Credit voucher
- Installment purchase

**Rules**:
- Transactions of type purchase and withdrawal are stored with negative amounts.
- Transactions of type credit voucher are stored with positive amounts.
---

## Tech Stack
- Java 21
- Spring Boot 3
- Spring Data JPA
- Flyway (database migrations)
- MySQL 8
- JUnit 5
- Logback
- Lombok
---
## Running with Docker Compose
This application uses Docker Compose to run both the app and the MySQL database.
```bash
docker compose up --build -d
```
The app will start on http://localhost:8080
The MySQL database will run inside a container on port 3307 (mapped to container port 3306).

To stop the containers
```bash
docker compose down
```
---
## Running Integration Tests
```bash
docker compose up -d mysql
mvn clean test
```
---
## API Documentation
The API documentation is available via OpenAPI and can be accessed through the following [link](http://localhost:8080/swagger-ui/index.html).
