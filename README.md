# GlobalBooks Inc. – SOA & Microservices Project
### CCS3341 Coursework | Due: 27 March 2026

---

## Project Structure

```
SOA & Microservices/
├── CatalogService/              ← Java JAX-WS SOAP WAR (Tomcat 9)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/globalbooks/catalog/
│       │   ├── CatalogPortType.java       (SEI)
│       │   ├── CatalogServiceImpl.java    (implementation)
│       │   ├── model/                     (Book, PriceResponse)
│       │   ├── exception/                 (BookNotFoundException)
│       │   └── security/                  (WsSecurityHandler)
│       └── webapp/WEB-INF/
│           ├── sun-jaxws.xml              (JAX-WS endpoint config)
│           ├── web.xml                    (servlet config)
│           └── wsdl/catalog.wsdl         (WSDL contract)
│
├── OrdersService/               ← Spring Boot REST (port 8081)
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/main/java/com/globalbooks/orders/
│       ├── OrdersApplication.java
│       ├── controller/OrderController.java
│       ├── service/OrderService.java
│       ├── model/                         (Order, OrderItem, etc.)
│       ├── messaging/OrderEventPublisher.java
│       ├── security/OAuth2ResourceServerConfig.java
│       └── config/RabbitMQConfig.java
│
├── PaymentsService/             ← Spring Boot + RabbitMQ consumer (port 8082)
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/main/java/com/globalbooks/payments/
│       └── consumer/PaymentsConsumer.java
│
├── ShippingService/             ← Spring Boot + RabbitMQ consumer (port 8083)
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/main/java/com/globalbooks/shipping/
│       └── consumer/ShippingConsumer.java
│
├── bpel/
│   ├── PlaceOrder.bpel           ← BPEL 2.0 orchestration process
│   └── deploy.xml                ← Apache ODE deployment descriptor
│
├── rabbitmq/
│   └── definitions.json          ← Exchange, queue, DLQ, binding definitions
│
├── docs/
│   ├── SOA_Architecture_Design.md
│   ├── UDDI_Registry_Metadata.xml
│   ├── Governance_Policy.md
│   └── openapi.yaml              ← OrdersService OpenAPI 3.0 spec
│
├── testing/
│   ├── soap/                     ← SOAP UI test XML files
│   │   ├── test_get_book_price.xml
│   │   ├── test_get_book_by_id.xml
│   │   └── test_search_books.xml
│   └── rest/
│       └── test_orders_api.bat   ← curl REST test script
│
└── docker-compose.yml            ← Full stack deployment (ILO4)
```

---

## Quick Start

### 1. Start Infrastructure

```cmd
:: Start RabbitMQ
net start RabbitMQ

:: Start Keycloak (first time)
docker run -d --name keycloak -p 9000:8080 ^
  -e KEYCLOAK_ADMIN=admin ^
  -e KEYCLOAK_ADMIN_PASSWORD=admin ^
  quay.io/keycloak/keycloak:latest start-dev

:: Start Tomcat 9 (serves CatalogService, ODE, jUDDI)
cd %CATALINA_HOME%\bin && startup.bat
```

### 2. Build and Deploy CatalogService

```cmd
cd CatalogService
mvn clean package
copy target\CatalogService.war %CATALINA_HOME%\webapps\
```

### 3. Start Spring Boot Services

```cmd
:: Each in its own terminal
cd OrdersService   && mvn spring-boot:run
cd PaymentsService && mvn spring-boot:run
cd ShippingService && mvn spring-boot:run
```

### 4. OR use Docker Compose for OrdersService + PaymentsService + ShippingService

```cmd
docker-compose up --build -d
```

---

## Service Endpoints

| Service | URL |
|---------|-----|
| CatalogService WSDL | http://localhost:8080/CatalogService/catalog?wsdl |
| OrdersService REST  | http://localhost:8081/api/v1/orders |
| PaymentsService     | http://localhost:8082 |
| ShippingService     | http://localhost:8083 |
| Apache ODE          | http://localhost:8080/ode/ |
| jUDDI GUI           | http://localhost:8080/juddi-gui/ |
| RabbitMQ Dashboard  | http://localhost:15672 (guest/guest) |
| Keycloak Admin      | http://localhost:9000 (admin/admin) |

---

## Assignment Question Coverage

| Question | Answer in... |
|----------|-------------|
| Q1 – SOA principles | `docs/SOA_Architecture_Design.md` §2 |
| Q2 – Service decomposition | `docs/SOA_Architecture_Design.md` §3 |
| Q3 – WSDL design | `CatalogService/src/main/webapp/WEB-INF/wsdl/catalog.wsdl` |
| Q4 – UDDI registry | `docs/UDDI_Registry_Metadata.xml` |
| Q5 – SOAP service config | `CatalogServiceImpl.java`, `sun-jaxws.xml`, `web.xml` |
| Q6 – SOAP operations | `CatalogServiceImpl.java` – getBookById, getBookPrice, searchBooks |
| Q7 – REST API design | `OrderController.java`, `docs/openapi.yaml` |
| Q8 – BPEL orchestration | `bpel/PlaceOrder.bpel`, `bpel/deploy.xml` |
| Q9 – Service testing | `testing/soap/`, `testing/rest/` |
| Q10 – Messaging integration | `OrderEventPublisher.java`, `PaymentsConsumer.java`, `ShippingConsumer.java` |
| Q11 – Error handling | `RabbitMQConfig.java` (DLQ), `application.yml` (retry) |
| Q12 – WS-Security | `WsSecurityHandler.java` |
| Q13 – OAuth2 | `OAuth2ResourceServerConfig.java`, Keycloak |
| Q14 – QoS | `RabbitMQConfig.java` (confirms, TTL), `application.yml` (prefetch) |
| Q15 – Governance | `docs/Governance_Policy.md` |
| ILO4 – Cloud deployment | `docker-compose.yml`, `Dockerfile` (×3) |
