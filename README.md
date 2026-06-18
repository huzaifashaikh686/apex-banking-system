# Apex Banking System

A multi-threaded, in-memory banking core engine built using Spring Boot. This project simulates essential retail banking operations like managing accounts, depositing/withdrawing funds, tracking transaction histories, and performing secure account-to-account money transfers.

## 🚀 Key Features
* **Account Types:** Supports abstract account management with specific behaviors for Savings Accounts (earning interest) and Current Accounts (with an overdraft limit).
* **Concurrency Safety:** Core data stores use thread-safe Java collections (`ConcurrentHashMap` and `CopyOnWriteArrayList`) to protect data from race conditions during simultaneous API requests.
* **Deadlock Prevention:** The money transfer algorithm dynamically sorts account locks sequentially by account number to completely eliminate circular-wait deadlocks.
* **API Documentation:** Built-in Swagger/OpenAPI UI integration for easy endpoint testing.

## 🛠️ Current Architecture Note
The system currently processes and stores all banking operations using low-latency concurrent Java memory structures to focus heavily on core multi-threading safety. 

An **H2 Relational Database** configuration and basic `schema.sql` database table layout are already pre-wired and initialized on startup. This is left in place intentionally to serve as a stepping stone for transitioning to a full Spring Data JPA or JDBC persistence layer in the next development phase.

## 📦 How to Run the Project

### Prerequisites
* Java 17 or higher
* Maven (or use the included Maven wrapper)

### Steps
1. Clone or download this project folder.
2. Open the project root directory in your terminal or IDE (VS Code / IntelliJ).
3. Run the application using the following Maven command:
   ```bash
   ./mvnw spring-boot:run
