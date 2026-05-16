# 🌿 Briefmint

REST API backend для приложения заметок с иерархической структурой. Папки → заметки → блоки контента (текст, изображения по ссылке) — всё в виде дерева. Проект в активной разработке.

## Стек

- **Kotlin** + **Spring Boot 3**
- **Spring Security** + **JWT** (аутентификация)
- **PostgreSQL** + **Spring Data JPA** / Hibernate
- **Swagger / OpenAPI** — документация API
- **Docker Compose** — локальная база данных

## Структура данных

```
Пользователь
└── Папка (Folder)
    └── Заметка (Note)
        └── Узел (Node) — текст или изображение
            └── Дочерние узлы (иерархия)
```

Заметки и узлы поддерживают вложенность произвольной глубины.

## Запуск локально

### 1. Предварительные требования

- JDK 17+
- Maven
- Docker + Docker Compose

### 2. Настройка окружения

Скопируй пример конфигурации и заполни своими значениями:

```bash
cp application.properties.example src/main/resources/application.properties
```

Минимальная конфигурация:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your_base64_encoded_secret_key
```

> JWT secret должен быть строкой в Base64, минимум 256 бит (32 байта).

### 3. Запуск базы данных

```bash
docker compose up -d
```

### 4. Запуск приложения

```bash
./mvnw spring-boot:run
```

### 5. Swagger UI

После запуска документация доступна по адресу:

```
http://localhost:8080/swagger-ui/index.html
```

## Основные эндпоинты

| Метод | Путь | Описание |
|-------|------|----------|
| `POST` | `/api/v1/auth/sign-up` | Регистрация |
| `POST` | `/api/v1/auth/sign-in` | Вход |
| `POST` | `/api/v1/jwt/refresh` | Обновление токенов |
| `GET` | `/api/v1/folders` | Получить папки |
| `POST` | `/api/v1/folders` | Создать папку |
| `GET` | `/api/v1/notes/folder/{id}` | Заметки в папке |
| `POST` | `/api/v1/notes/folder/{id}` | Создать заметку |
| `POST` | `/api/v1/text/note/{id}` | Добавить текстовый блок |
| `POST` | `/api/v1/image/note/{id}` | Добавить изображение по ссылке |

Полный список с описанием параметров — в Swagger UI.

## Планы

- [ ] Деплой (coming soon)
- [ ] Поддержка дополнительных типов блоков
- [ ] Поиск по заметкам
- [ ] Экспорт

## Лицензия

BSD-3
