# Financial Product API

## Overview
This project is a REST API for managing financial transactions, users, and cards. The API allows:
- User management (CRUD operations)
- Card management (CRUD operations)
- Transaction processing
- Authentication using JWT tokens

## Technologies Used
- Java 17
- Spring Boot
- PostgreSQL
- Docker
- JWT Authentication

## Setup Instructions
### Prerequisites
- Install **Docker** and **Docker Compose**
- Install **Java 17**
- Install **Maven**

### Running the Project
1. Clone the repository:
   ```sh
   git clone https://github.com/Suxrob-Jurakulov/financial-product.git
   cd financial-product-api
   ```
2. Build and run the project with Docker:
   ```sh
   docker-compose up --build
   ```
3. The application will be available at: `http://localhost:8080`


## ⚠️ Database Configuration
Before running the project, update **application.yml** and **docker-compose.yml** files with your database configuration.

### 📌 application.yml file:
```yaml
  # DataSource Configuration
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://postgres:5432/<database_name>
    username: <your_username>
    password: <your_password>
```

### 📌 docker-compose.yml file:
```yaml
version: '3.8'
services:
  app:
    build: .
    container_name: financial-product-app
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/<database_name>
      SPRING_DATASOURCE_USERNAME: <your_username>
      SPRING_DATASOURCE_PASSWORD: <your_password>

  postgres:
    image: postgres:15
    container_name: financial-product-db
    restart: on-failure
    environment:
      POSTGRES_DB: <database_name>
      POSTGRES_USER: <your_username>
      POSTGRES_PASSWORD: <your_password>
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```
📢 **Important!** After making changes, restart Docker containers:

## API Endpoints
### Authentication
- `POST /auth/register` - Register a new user
  ```json
  {
    "firstname": "John",
    "lastname": "Jonny",
    "email": "john@gmail.com",
    "phone": "998901234569",
    "password": "123"
  }
  ```
- `POST /auth/login` - Login and get JWT token
  ```json
  {
    "phone": "998901234567",
    "password": "123"
  }
  ```
- `POST /token/refresh` - Refresh JWT token
  ```json
  {
    "refreshToken": "your_refresh_token"
  }
  ```

### Profile Management
- `GET /profile` - Get current user profile (Requires Bearer Token)
- `PUT /profile` - Update profile
  ```json
  {
    "firstname": "Vali",
    "lastname": "Valiyev",
    "email": "vali@gmail.com"
  }
  ```
- `PUT /profile/change-password` - Change password
  ```json
  {
    "password": "123321"
  }
  ```

### Card Management
- `GET /cards/{id}` - Get card details by ID
- `GET /cards/number` - Get card details by number
  ```json
  {
    "number": "8600223344556677"
  }
  ```
- `GET /cards/get-all` - Get all cards
- `POST /cards` - Create a new card
  ```json
  {
    "number": "8600223344556688",
    "expiryDate": "2025/07",
    "name": "Kapital card"
  }
  ```
- `PUT /cards/change-status` - Change card status
  ```json
  {
    "number": "8600223344556677",
    "status": "ACTIVE"
  }
  ```

### Transaction Management
- `GET /transaction/paging` - Get paginated list of transactions
- `GET /transaction/debit` - Get debit transaction by ID
- `GET /transaction/credit` - Get credit transaction by ID
- `POST /transaction/create/{status}` - Create a transaction
  ```json
  {
    "amount": 200,
    "fromTransactionParam": {
        "cardNumber": "8600112233445577",
        "ccy": "860",
        "type": "DEBIT"
    },
    "toTransactionParam": {
        "cardNumber": "8600112233445588",
        "ccy": "860",
        "type": "CREDIT"
    }
  }
  ```

## Database Schema
### **Profile Table**
| Column Name | Type | Constraints |
|-------------|------|------------|
| `id` | `VARCHAR(255)` | `PRIMARY KEY` |
| `created_date` | `TIMESTAMP` |  |
| `deleted` | `BOOLEAN` |  |
| `email` | `VARCHAR(255)` |  |
| `firstname` | `VARCHAR(255)` | `NOT NULL` |
| `lastname` | `VARCHAR(255)` | `NOT NULL` |
| `modules` | `JSONB` |  |
| `password` | `VARCHAR(255)` | `NOT NULL` |
| `phone` | `VARCHAR(255)` | `NOT NULL`, `UNIQUE` |
| `role` | `VARCHAR(255)` | `CHECK` (Must be `ROLE_ADMIN` or `ROLE_USER`) |

### **Transaction Table**
| Column Name | Type | Constraints |
|-------------|------|------------|
| `id` | `VARCHAR(255)` | `PRIMARY KEY` |
| `commission_amount` | `BIGINT` |  |
| `commission_fee` | `DOUBLE PRECISION` |  |
| `created_date` | `TIMESTAMP` |  |
| `external_transaction_id` | `VARCHAR(255)` |  |
| `recipient_card` | `VARCHAR(255)` |  |
| `recipient_profile_id` | `VARCHAR(255)` |  |
| `request_amount` | `BIGINT` |  |
| `sender_card` | `VARCHAR(255)` |  |
| `sender_profile_id` | `VARCHAR(255)` |  |
| `success` | `BOOLEAN` |  |
| `transaction_amount` | `BIGINT` |  |
| `transaction_status` | `VARCHAR(255)` | `CHECK` (Must be `SUCCESS`, `FAILED`, or `TIMEOUT`) |

### **Card Table**
| Column Name | Type | Constraints |
|-------------|------|------------|
| `id` | `VARCHAR(255)` | `PRIMARY KEY` |
| `balance` | `BIGINT` |  |
| `bin` | `VARCHAR(255)` |  |
| `card_issuing_bank` | `VARCHAR(255)` |  |
| `created_date` | `TIMESTAMP` |  |
| `currency_code` | `INTEGER` |  |
| `deleted` | `BOOLEAN` |  |
| `expiry_date` | `BYTEA` | `NOT NULL` |
| `masked_pan` | `VARCHAR(255)` | `NOT NULL` |
| `name` | `VARCHAR(255)` |  |
| `phone` | `VARCHAR(255)` |  |
| `profile_id` | `VARCHAR(255)` | `NOT NULL`, `FOREIGN KEY` (References `profile.id`) |
| `real_pan` | `VARCHAR(255)` | `UNIQUE` |
| `sms` | `BOOLEAN` |  |
| `status` | `VARCHAR(255)` | `CHECK` (Must be `ACTIVE`, `BLOCKED`, `EXPIRED`, or `STOLEN`) |

### **Tokens Table**
| Column Name | Type | Constraints |
|-------------|------|------------|
| `id` | `VARCHAR(255)` | `PRIMARY KEY` |
| `access_expiration_date` | `TIMESTAMP WITH TIME ZONE` | `NOT NULL` |
| `access_token` | `VARCHAR(1000)` | `NOT NULL` |
| `created_date_time` | `TIMESTAMP WITH TIME ZONE` | `NOT NULL` |
| `deleted` | `BOOLEAN` | `NOT NULL` |
| `deleted_date_time` | `TIMESTAMP WITH TIME ZONE` |  |
| `refresh_expiration_date` | `TIMESTAMP WITH TIME ZONE` | `NOT NULL` |
| `refresh_token` | `VARCHAR(1000)` | `NOT NULL` |
| `uid` | `VARCHAR(255)` | `NOT NULL`, `FOREIGN KEY` (References `profile.id`) |

```
```

### Table Creation Queries
```sql
CREATE TABLE IF NOT EXISTS public.profile (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    created_date TIMESTAMP,
    deleted BOOLEAN,
    email VARCHAR(255),
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    modules JSONB,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(255) CHECK (role IN ('ROLE_ADMIN', 'ROLE_USER'))
);

CREATE TABLE IF NOT EXISTS public.transaction (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    commission_amount BIGINT,
    commission_fee DOUBLE PRECISION,
    created_date TIMESTAMP,
    external_transaction_id VARCHAR(255),
    recipient_card VARCHAR(255),
    recipient_profile_id VARCHAR(255),
    request_amount BIGINT,
    sender_card VARCHAR(255),
    sender_profile_id VARCHAR(255),
    success BOOLEAN,
    transaction_amount BIGINT,
    transaction_status VARCHAR(255) CHECK (transaction_status IN ('SUCCESS', 'FAILED', 'TIMEOUT'))
);

CREATE TABLE IF NOT EXISTS public.card (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    balance BIGINT,
    bin VARCHAR(255),
    card_issuing_bank VARCHAR(255),
    created_date TIMESTAMP,
    currency_code INTEGER,
    deleted BOOLEAN,
    expiry_date BYTEA NOT NULL,
    masked_pan VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    phone VARCHAR(255),
    profile_id VARCHAR(255) NOT NULL,
    real_pan VARCHAR(255) NOT NULL UNIQUE,
    sms BOOLEAN,
    status VARCHAR(255) CHECK (status IN ('ACTIVE', 'BLOCKED', 'EXPIRED', 'STOLEN'))
);

CREATE TABLE IF NOT EXISTS public.tokens (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    access_expiration_date TIMESTAMP WITH TIME ZONE NOT NULL,
    access_token VARCHAR(1000) NOT NULL,
    created_date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted BOOLEAN NOT NULL,
    deleted_date_time TIMESTAMP WITH TIME ZONE,
    refresh_expiration_date TIMESTAMP WITH TIME ZONE NOT NULL,
    refresh_token VARCHAR(1000) NOT NULL,
    uid VARCHAR(255) NOT NULL
);
```
### **Relationships:**
- `Transaction.sender_profile_id` → **References** `Profile.id`
- `Transaction.recipient_profile_id` → **References** `Profile.id`
- `Card.profile_id` → **References** `Profile.id`
- `Tokens.uid` → **References** `Profile.id`

## Authentication
- The API uses **JWT (JSON Web Token)** for authentication.
- To obtain a token, send credentials to `/auth/login`.
- Use the token in the `Authorization` header for secured endpoints.


