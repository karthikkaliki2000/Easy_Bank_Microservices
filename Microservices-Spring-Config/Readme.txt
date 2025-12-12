🏦 EazyBank Microservices
This project demonstrates a cloud‑native microservices architecture using Docker Compose. It includes core banking services (Accounts, Loans, Cards, Message), infrastructure (RabbitMQ, Redis, MySQL, Keycloak), and observability (Grafana, Loki, Tempo, Prometheus, Alloy, MinIO).

🚀 Core Microservices
Config Server – Centralized configuration management

Eureka Server – Service discovery and registry

Gateway Server – API gateway with Redis caching and Keycloak JWT validation

Accounts Service – Manages customer account data

Loans Service – Handles loan information

Cards Service – Manages card details

Message Service – RabbitMQ‑based messaging microservice

🐇 Messaging & Identity
RabbitMQ – Message broker with management UI (5672, 15672)

Keycloak – Identity and access management (7080)

🗄️ Databases
Each service has its own isolated MySQL instance:

Accounts DB → 3306

Loans DB → 3307

Cards DB → 3308

⚡ Caching
Redis → 6379

📊 Observability Stack
Grafana → Dashboards (3000)

Prometheus → Metrics (9090)

Loki → Log aggregation (read/write/backend/gateway)

Tempo → Distributed tracing (3110, 4318)

Alloy → Telemetry pipeline (12345)

MinIO → Object storage for Loki data (9000)

🛠️ Health Checks
All services include Docker healthchecks for readiness:

RabbitMQ → rabbitmq-diagnostics check_port_connectivity

MySQL → container‑level health via microservice-db-config

Redis → redis-cli ping

Spring Boot services → /actuator/health/readiness

📂 Project Structure (Suggested)
Code
eazybank-microservices/
│── accounts-service/
│── loans-service/
│── cards-service/
│── message-service/
│── configserver/
│── eurekaserver/
│── gatewayserver/
│── observability/
│   ├── grafana/
│   ├── prometheus/
│   ├── loki/
│   ├── tempo/
│   └── alloy/
│── docker-compose.yml
│── README.md
▶️ Running the Stack
Start all services:

bash
docker compose up -d
Stop all services:

bash
docker compose down
🌐 Access Points
RabbitMQ → http://localhost:15672

Keycloak → http://localhost:7080

Grafana → http://localhost:3000

Prometheus → http://localhost:9090

Tempo → http://localhost:3110

MinIO → http://localhost:9000

🔑 Next Steps
Configure Keycloak realms & clients for OAuth2/JWT validation.

Add Grafana dashboards for Accounts, Loans, Cards metrics.

Integrate distributed tracing with Tempo + OpenTelemetry.

Use RabbitMQ exchanges/queues for async communication between services.
