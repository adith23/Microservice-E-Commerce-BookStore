# GlobalBooks SOA Governance Policy

**Document ID:** GOV-001  
**Version:** 1.0  
**Author:** Solution Architect / Governance Consultant  
**Q15: SOA Governance – versioning, SLAs, lifecycle phases, compliance**

---

## 1. Purpose and Scope

This document defines the governance framework for all GlobalBooks SOA services (CatalogService, OrdersService, PaymentsService, ShippingService). It establishes rules for versioning, service lifecycle management, SLAs, and compliance.

---

## 2. Service Versioning Policy

### 2.1 SOAP Services (CatalogService)

| Rule | Detail |
|------|--------|
| **Version location** | Embedded in WSDL `targetNamespace` as `/v1`, `/v2`, etc. |
| **Backward compatibility** | At minimum, 2 major versions must be supported concurrently |
| **Deprecation notice** | 6 months written notice before retiring a WSDL version |
| **Breaking change** | Requires new `v{N+1}` namespace; never modify existing versioned WSDL |
| **Non-breaking change** | New optional elements with `minOccurs="0"` permitted in same version |

**Example:** `targetNamespace="http://catalog.globalbooks.com/v1"` → `v2` when breaking.

### 2.2 REST Services (OrdersService)

| Rule | Detail |
|------|--------|
| **Version location** | URL path segment: `/api/v1/`, `/api/v2/` |
| **Header version** | `API-Version: 1` header also accepted for compatibility |
| **Semantic versioning** | `MAJOR.MINOR.PATCH` for internal build tracking |
| **Deprecation** | `Deprecation: date` + `Sunset: date` response headers on deprecated endpoints |

---

## 3. Service Lifecycle Phases

```
Proposed → Development → Testing → Staging → Production → Deprecated → Retired
```

| Phase | Description | Governance Action Required |
|-------|------------|--------------------------|
| **Proposed** | Design document submitted to Architecture Board | Architecture review and approval |
| **Development** | Service under construction | Code review sign-off |
| **Testing** | Integration and QA | Test report review |
| **Staging** | Pre-production validation | Performance SLA validation |
| **Production** | Actively serving consumers | Monthly SLA review |
| **Deprecated** | Sunset notice issued | Consumer migration plan |
| **Retired** | Service decommissioned | UDDI entry removed |

---

## 4. Service Level Agreements (SLAs)

### 4.1 Service Availability Targets

| Service | Availability Target | Measurement Window |
|---------|--------------------|--------------------|
| CatalogService | 99.9% | Monthly |
| OrdersService | 99.95% | Monthly |
| PaymentsService | 99.99% | Monthly |
| ShippingService | 99.9% | Monthly |

### 4.2 Response Time Targets

| Service | p50 | p95 | p99 | Timeout |
|---------|-----|-----|-----|---------|
| CatalogService | < 50ms | < 200ms | < 500ms | 5s |
| OrdersService | < 100ms | < 300ms | < 1000ms | 10s |
| PaymentsService | async | N/A | N/A | 30s message TTL |
| ShippingService | async | N/A | N/A | 30s message TTL |

### 4.3 Throughput Targets

| Service | Requests/sec (peak) |
|---------|---------------------|
| CatalogService | 200 req/s |
| OrdersService | 50 req/s |
| PaymentsService | 50 messages/s |
| ShippingService | 50 messages/s |

---

## 5. Quality of Service (QoS) Mechanisms

| Mechanism | Implementation | Purpose |
|-----------|---------------|---------|
| **WS-Security (CatalogService)** | UsernameToken in SOAP header | Authentication & integrity |
| **OAuth2 JWT (OrdersService)** | Keycloak-issued Bearer tokens | Auth + authorisation |
| **RabbitMQ Publisher Confirms** | `publisher-confirm-type: correlated` | Guarantees message accepted by broker |
| **Consumer Manual ACK** | `acknowledge-mode: manual` | Message not lost if consumer crashes |
| **Dead Letter Queues** | `x-dead-letter-exchange: dlx.exchange` | Failed message recovery |
| **Message TTL** | `x-message-ttl: 30000` | Prevents stale message processing |
| **Prefetch Count** | `prefetch: 10` | Flow control / fair dispatch |
| **Retry with Backoff** | max 3 attempts; 1s, 2s, 4s | Transient fault tolerance |

---

## 6. Compliance and Audit

### 6.1 Standards Compliance

| Standard | Applied To | Compliance |
|----------|-----------|------------|
| WS-Security 1.1 | CatalogService | ✅ UsernameToken profile |
| WSDL 1.1 | CatalogService contract | ✅ Document/Literal style |
| UDDI v3 | Service registry | ✅ Apache jUDDI |
| BPEL 2.0 | PlaceOrder orchestration | ✅ Apache ODE |
| OpenAPI 3.0 | OrdersService contract | ✅ openapi.yaml |
| OAuth 2.0 | OrdersService security | ✅ Keycloak |
| AMQP 0-9-1 | Async messaging | ✅ RabbitMQ |

### 6.2 Audit Logging Requirements

All services must log:
- Request received (timestamp, caller identity, operation name)
- Response sent (status code / SOAP result, latency)
- Authentication events (success/failure with username)
- Fault/error events (exception type, message)
- Message queue events (published, consumed, dead-lettered)

### 6.3 Change Management

1. All service contract changes must be submitted as a **Change Request (CR)**
2. CRs require approval from the Architecture Board before implementation
3. Breaking changes automatically trigger a **new version** (see Section 2)
4. Emergency hotfixes (security patches) follow an expedited 24h review cycle

---

## 7. Service Registry Policy

- All services that expose a WSDL or OpenAPI contract **must** be registered in jUDDI
- UDDI entries must be updated within 5 business days of any endpoint or contract change
- Deprecated services must have their UDDI entry status updated to `deprecated`
- Retired services must have their UDDI entry deleted or marked `retired`

---

## 8. Governance Roles

| Role | Responsibility |
|------|---------------|
| **Solution Architect** | Architecture review, technology decisions, design oversight |
| **Service Developer** | Implementation, unit tests, code review participation |
| **Integration Specialist** | ESB config, BPEL processes, message routing |
| **Security Engineer** | WS-Security, OAuth2 config, key management |
| **Governance Consultant** | SLA monitoring, lifecycle tracking, compliance audits |
