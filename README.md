# Online Shop

[中文说明请见这里 (Chinese README here)](./README_zh.md)

## Project Overview

This project is an online shopping system based on Spring Boot 3, supporting features such as user registration, login, shopping cart, order management, payment processing, product management, and data export. The backend uses PostgreSQL as the main database and Redis for caching and distributed scenarios. JWT is used for stateless authentication and role-based authorization.

## Tech Stack

- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- PostgreSQL 17.x
- Redis
- Spring Security
- EasyExcel (Alibaba Excel export)
- Lombok
- JJWT (JWT token)
- Maven 3.9.x

## Project Structure

```
src/
  main/
    java/com/bryan/system/
      config/         # Configuration classes (security, Redis, JPA, etc.)
      controller/     # RESTful controllers
      domain/         # Entities, request/response objects, VO
      filter/         # JWT authentication filter
      handler/        # Global exception handler
      repository/     # Spring Data JPA repository interfaces
      service/        # Service layer
      util/           # Utility classes (JWT, HTTP, etc.)
    resources/
      application.yaml
      application-dev.yaml
      sql/            # Database schema scripts
  test/
    java/com/bryan/system/
      OnlineShopApplicationTests.java
```

## Requirements

- JDK 17+
- Maven 3.9.9+
- PostgreSQL 17.x
- Redis 6.x or above

## Configuration

- Update database and Redis settings in `src/main/resources/application-dev.yaml`.
- General settings (logging etc.) are in `src/main/resources/application.yaml`.
- Inject JWT secret via configuration file for production environments.
- Database schema scripts are in [`src/main/resources/sql/create_table.sql`](src/main/resources/sql/create_table.sql).

## Getting Started

1. Initialize the PostgreSQL database by running the schema script:

   ```sh
   psql -U postgres -d postgres -f src/main/resources/sql/create_table.sql
   ```
2. Start the Redis service.
3. Build and run the project with Maven:

   ```sh
   ./mvnw spring-boot:run
   ```

   Or run the packaged jar:

   ```sh
   mvn clean package
   java -jar target/online-shop-0.0.1-SNAPSHOT.jar
   ```

## Main APIs

- User registration: `POST /api/auth/register`
- User login: `POST /api/auth/login`
- Shopping cart operations: `POST /api/cart/add`, `GET /api/cart/view`, `DELETE /api/cart/remove`
- Order management: `POST /api/order/create`, `GET /api/order/{id}`, `PUT /api/order/cancel`
- Payment processing: `POST /api/payment/pay`, `GET /api/payment/status`
- Product management: `POST /api/product/add`, `GET /api/product/{id}`, `PUT /api/product/update`
- Export user data: `GET /api/user/export/all`, `POST /api/user/export/field` (admin only)

## Notes

- For production, inject JWT secret via configuration file instead of hardcoding.
- Global exception handling is in [`GlobalExceptionHandler`](src/main/java/com/bryan/system/handler/GlobalExceptionHandler.java).
- Logical delete field is `deleted`: 0 means active, 1 means deleted.

## License

This project is licensed under the MIT License.
See [LICENSE](LICENSE) for details.