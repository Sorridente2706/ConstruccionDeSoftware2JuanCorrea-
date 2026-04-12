# EVALUACION 2 - ConstruccionDeSoftware2JuanCorrea-

## Informacion general
- Estudiante(s): Juan Correa (usuario GitHub: Sorridente2706; carpeta proyecto: pablocorrea)
- Rama evaluada: **develop** (rama con el commit más reciente del estudiante; 2026-04-05 18:48)
- Commit evaluado: 348997a (2026-04-05)
- Fecha: 2026-04-11
- Nota: La rama `main` original estaba vacía (solo Initial commit). La evaluación se realizó sobre `develop` donde está todo el código del estudiante.

---

## Tabla de calificacion

| # | Criterio | Peso | Puntaje (1-5) | Parcial |
|---|---|---|---|---|
| 1 | Modelado de dominio | 20% | 5 | 1.00 |
| 2 | Modelado de puertos | 20% | 5 | 1.00 |
| 3 | Modelado de servicios de dominio | 20% | 4 | 0.80 |
| 4 | Enums y estados | 10% | 5 | 0.50 |
| 5 | Reglas de negocio criticas | 10% | 5 | 0.50 |
| 6 | Bitacora y trazabilidad | 5% | 3 | 0.15 |
| 7 | Estructura interna de dominio | 10% | 5 | 0.50 |
| 8 | Calidad tecnica base en domain | 5% | 5 | 0.25 |
| | **Total base** | | | **4.70** |

### Calculo
Nota base = (5*20 + 5*20 + 4*20 + 5*10 + 5*10 + 3*5 + 5*10 + 5*5) / 100 = 470 / 100 = **4.70**

---

## Penalizaciones aplicadas

No se aplican penalizaciones. El uso de `@Service` no se penaliza. El código está completamente en inglés.

## Bonus aplicados

| Bonus | Valor | Motivo |
|---|---|---|
| Buen diseno de puertos reutilizables | +0.2 | 6 puertos en `domain/repository/` con métodos semánticos y `Optional<>` |
| Servicios de alta cohesion | +0.1 | `LoanDomainService` y `TransferDomainService` son servicios pequeños, enfocados y sin dependencias externas al dominio |

Nota con bonus: 4.70 + 0.30 = **5.00**

---

## Nota final
**5.0 / 5.0**

---

## Hallazgos

### Criterio 1 - Modelado de dominio (5/5)
- Entidades: `BankAccount`, `CompanyClient`, `Loan`, `NaturalPersonClient`, `Transfer`, `User` (6 entidades en `domain/model/entity/`).
- Jerarquía de clientes: `CompanyClient` y `NaturalPersonClient` diferenciados.
- Objetos de valor: `Email`, `Money` en `domain/model/valueobject/`.
- Excepciones de dominio semánticas: `AccountOperationNotAllowedException`, `DomainException`, `DuplicateIdentificationException`, `InsufficientFundsException`, `InvalidLoanStateTransitionException`, `InvalidTransferStateException`, `ResourceNotFoundException`, `UnauthorizedOperationException`, `UserNotActiveException`.
- `BankAccount` implementa métodos de negocio (`isActive()`, `credit()`, `debit()`).

### Criterio 2 - Modelado de puertos (5/5)
- 6 puertos en `domain/repository/`: `BankAccountRepository`, `CompanyClientRepository`, `LoanRepository`, `NaturalPersonClientRepository`, `TransferRepository`, `UserRepository`.
- Todos son interfaces Java puras (sin acoplamiento a Spring o JPA).
- `LoanRepository.findByStatus(LoanStatus status)` — método semántico.
- Retornan entidades del dominio con `Optional<>`.

### Criterio 3 - Servicios de dominio (4/5)
- `LoanDomainService`: valida que la cuenta de desembolso esté activa y pertenezca al cliente del préstamo antes de ejecutar. Muy cohesivo.
- `TransferDomainService`: determina el estado inicial de una transferencia según el monto (umbral configurable via `@Value`), ejecuta el débito/crédito entre cuentas.
- Solo 2 servicios en `domain/service/`, el resto de la lógica de aplicación está en `application/usecase/` (AuthUseCase, BankAccountUseCase, LoanUseCase, TransferUseCase, UserUseCase).
- Falta: servicios de dominio para aprobación/rechazo de préstamos directamente en domain/service/.

### Criterio 4 - Enums y estados (5/5)
- `AccountStatus`, `AccountType`, `LoanStatus (UNDER_REVIEW, APPROVED, REJECTED, DISBURSED)`, `TransferStatus`, `UserRole`, `UserStatus` en `domain/model/valueobject/`.
- Formato enum para todos los estados críticos. No hay strings mágicos para estados.

### Criterio 5 - Reglas de negocio criticas (5/5)
- `LoanDomainService.disburseLoanToAccount()`: valida cuenta activa, valida que el dueño sea el cliente del préstamo, ejecuta el crédito.
- `TransferDomainService.executeTransfer()`: ejecuta débito en cuenta origen y crédito en cuenta destino.
- `TransferDomainService.requiresApproval()` + `determineInitialStatus()`: regla de negocio para determinar si la transferencia requiere aprobación según monto umbral.
- Excepciones de dominio específicas para cada violación (InsufficientFundsException, AccountOperationNotAllowedException, UnauthorizedOperationException, etc.).

### Criterio 6 - Bitacora y trazabilidad (3/5)
- `AuditLogOutputPort` está en `application/port/output/` — no en el dominio.
- `InMemoryAuditLogAdapter` en `adapter/out/nosql/` — capa correcta.
- No existe entidad `AuditLog` dentro del paquete `domain/`.
- La bitácora está pensada pero implementada fuera del dominio puro.

### Criterio 7 - Estructura interna de dominio (5/5)
- `domain/exception/` — excepciones de dominio.
- `domain/model/entity/` — entidades.
- `domain/model/valueobject/` — objetos de valor y enums.
- `domain/repository/` — puertos de salida.
- `domain/service/` — servicios de dominio.
- Estructura limpia y coherente con hexagonal architecture.

### Criterio 8 - Calidad tecnica (5/5)
- Código completamente en inglés.
- `Money`: objeto de valor inmutable (`final`), con `equals`, `hashCode`, `toString`, operaciones aritméticas y validaciones.
- `equals`/`hashCode` implementados en value objects.
- Excepciones específicas por tipo de violación de negocio.
- Naming correcto y consistente.

---

## Resumen
Excelente implementación de arquitectura hexagonal. El dominio está limpio, con puertos definidos en el paquete domain, servicios de dominio con lógica real de negocio, y objetos de valor inmutables. La única debilidad relevante es que la entidad de bitácora no está en el dominio propio y el número de servicios de dominio es bajo (delegando más a la capa de aplicación).


## Informacion general
- Estudiante(s): Integrantes no informados en README.md (usuario GitHub: Sorridente2706, nombre inferido: Juan Correa)
- Rama evaluada: main
- Commit evaluado: d3e43452fe932133689b7a47cfbc5f0fedd94533
- Fecha: 2026-04-11
- Nota: Solo existe rama `main`; no hay rama `develop`. No existe `README.md`.

---

## Tabla de calificacion

| # | Criterio | Peso | Puntaje (1-5) | Parcial |
|---|---|---|---|---|
| 1 | Modelado de dominio | 20% | 0 | 0.00 |
| 2 | Modelado de puertos | 20% | 0 | 0.00 |
| 3 | Modelado de servicios de dominio | 20% | 0 | 0.00 |
| 4 | Enums y estados | 10% | 0 | 0.00 |
| 5 | Reglas de negocio criticas | 10% | 0 | 0.00 |
| 6 | Bitacora y trazabilidad | 5% | 0 | 0.00 |
| 7 | Estructura interna de dominio | 10% | 0 | 0.00 |
| 8 | Calidad tecnica base en domain | 5% | 0 | 0.00 |
| | **Total base** | | | **0.00** |

### Calculo
Nota base = 0 / 100 = **0.00**

---

## Penalizaciones aplicadas

No aplican (no hay codigo para penalizar).

---

## Nota final
**0.0 / 5.0**

---

## Hallazgos

### Repositorio sin codigo entregado
El repositorio contiene unicamente dos archivos:
- `EVALUACION.md`
- `LICENSE`

No existe ningun archivo fuente Java, ni estructura Maven/Gradle, ni ninguna implementacion de dominio.

El repositorio fue clonado correctamente desde `https://github.com/Sorridente2706/ConstruccionDeSoftware2JuanCorrea-.git` y el estado refleja el commit mas reciente de `main` (`d3e43452`).

---

## Recomendaciones
1. Entregar el proyecto con estructura Maven/Spring Boot y la capa de dominio implementada.
2. El `README.md` debe incluir los nombres de los integrantes del proyecto.
3. Implementar como minimo: entidades del dominio bancario, enums de estado, puertos de salida y servicios de dominio con casos de uso.
