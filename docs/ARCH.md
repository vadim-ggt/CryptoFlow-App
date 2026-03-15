# Исследование архитектурного решения

# CryptoFlow --- Социальная сеть для трейдеров

------------------------------------------------------------------------

# Часть 1. Проектирование архитектуры (To Be)

## 1. Тип приложения

**CryptoFlow** --- это высоконагруженное веб-приложение (социальная сеть
для трейдеров) со следующими характеристиками:

-   Тип: Web Application (SPA + REST API + WebSocket)
-   Архитектурный стиль: Client-Server
-   Архитектурный паттерн: Многослойная архитектура (Controller →
    Service → Repository → DB)
-   Real-Time: WebSocket
-   Масштабируемость: Горизонтальная (Docker + Kubernetes в будущем)

------------------------------------------------------------------------

## 2. Стратегия развёртывания

### Контейнерная архитектура

``` mermaid
flowchart LR
    A[Frontend - React SPA] -->|REST API| B[Spring Boot API]
    A -->|WebSocket| B
    B --> C[(PostgreSQL)]
    B --> D[(Redis Cache)]
    B --> E[External Crypto API]

    subgraph Docker Network
        A
        B
        C
        D
    end
```

### Компоненты:

-   Frontend (React)
-   Backend (Spring Boot)
-   PostgreSQL
-   Redis (кеширование)
-   WebSocket Server
-   Интеграция с внешними API бирж

------------------------------------------------------------------------

## 3. Архитектурная схема приложения (To Be)

``` mermaid
flowchart TD

    UI[React UI Layer]
    CTRL[Controllers]
    SEC[Security Layer - JWT]
    WS[WebSocket Layer]
    SRV[Service Layer]
    REP[Repository Layer]
    DB[(PostgreSQL)]
    CACHE[(Redis)]

    UI --> CTRL
    CTRL --> SEC
    SEC --> SRV
    UI --> WS
    WS --> SRV
    SRV --> REP
    REP --> DB
    SRV --> CACHE
```

------------------------------------------------------------------------

## 4. Сквозная функциональность

### 4.1 Аутентификация

``` mermaid
sequenceDiagram
    participant Client
    participant Controller
    participant Security
    participant Service
    participant DB

    Client->>Controller: Login Request
    Controller->>Security: Authenticate
    Security->>DB: Validate User
    DB-->>Security: User Data
    Security-->>Client: JWT Token
```

### 4.2 Обновление курса валют (Real-Time)

``` mermaid
sequenceDiagram
    participant ExternalAPI
    participant Backend
    participant Redis
    participant Client

    ExternalAPI->>Backend: Price Update
    Backend->>Redis: Cache Update
    Backend->>Client: WebSocket Broadcast
```

------------------------------------------------------------------------

# Часть 2. Анализ архитектуры (As Is)

## Реализовано к 1 спринту:

-   Спроектирована БД
-   Созданы Entity
-   Базовая структура Spring Boot
-   Настроено подключение к PostgreSQL

------------------------------------------------------------------------

## Диаграмма классов (As Is)

``` mermaid
classDiagram

    class User {
        +Long id
        +String username
        +String email
        +String password
        +LocalDateTime createdAt
    }

    class Post {
        +Long id
        +Long userId
        +String content
        +LocalDateTime createdAt
    }

    class Comment {
        +Long id
        +Long postId
        +Long userId
        +String content
        +LocalDateTime createdAt
    }

    class UserRepository
    class PostRepository
    class CommentRepository

    UserRepository --> User
    PostRepository --> Post
    CommentRepository --> Comment
```

------------------------------------------------------------------------

# Часть 3. Сравнение и рефакторинг

## 1. Сравнение To Be и As Is

### Реализовано:

-   Структура пакетов
-   Entity слой
-   Подключение к БД

### Не реализовано:

-   JWT безопасность
-   WebSocket
-   Redis кеширование
-   Интеграция с биржами
-   Привязка криптокошелька

------------------------------------------------------------------------

## 2. Основные проблемные зоны

### 1. Привязка криптокошелька

``` mermaid
flowchart LR
    User --> API
    API --> Validation
    Validation --> ExternalBlockchainAPI
    ExternalBlockchainAPI --> API
    API --> DB
```

Риски: - Валидация адресов - Безопасность хранения - Проверка владения
кошельком

------------------------------------------------------------------------

### 2. Обновление курса валют

``` mermaid
flowchart TD
    ExchangeAPI --> Backend
    Backend --> Cache
    Backend --> WebSocketClients
```

Риски: - Высокая нагрузка - Частота обновлений - Масштабируемость
WebSocket соединений

------------------------------------------------------------------------

## 3. Рекомендованный рефакторинг

### Архитектура Target (Production Ready)

``` mermaid
flowchart TD

    Gateway[API Gateway]
    Auth[Auth Service]
    Core[Core Service]
    Market[Market Data Service]
    Cache[(Redis)]
    DB[(PostgreSQL)]
    WS[WebSocket Cluster]

    Gateway --> Auth
    Gateway --> Core
    Gateway --> Market

    Core --> DB
    Market --> Cache
    Market --> WS
```

------------------------------------------------------------------------

# Вывод

CryptoFlow имеет хорошую базовую архитектурную основу (As Is), но для
выхода в production требуется:

-   Реализация JWT и Security Layer
-   Внедрение Redis
-   Настройка WebSocket инфраструктуры
-   Масштабирование через контейнеризацию
-   Интеграция с криптобиржами

Архитектура To Be ориентирована на real-time взаимодействие,
масштабируемость и безопасность.
