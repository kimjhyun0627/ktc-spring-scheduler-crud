## 📘 API 명세서

### 🔹 Base URL
`https://localhost:8080/api`
---
### ✅ [POST] /schedule
- 새 일정 생성

- request
```http
GET /users HTTP/1.1
Host: localhost:8080
```

- request body

```json
{
    "userId": "number",
    "password": "string",
    "title": "string",
    "task": "string"
}
```

- response body
```json
{
    "id": "number",
    "userId": "number",
    "userName": "string",
    "title": "string",
    "task": "string"
    "createdAt": "localDateTime",
    "updatedAt": "localDateTime"
}
```

- response code
```http
201 Created | 400 Bad Request
```
---
### ✅ [GET] /schedule
- 모든 일정 조회

- request
```http
GET /users HTTP/1.1
Host: localhost:8080
```

- request body

```json
none
```
```json
{
    "updatedAt": "string"
}
```
```json
{
    "userName": "string"
}
```
```json
{
    "updatedAt": "string",
    "userName": "string"
}
```

- response body
```json
[
    {
        "id": "number",
        "userId": "number",
        "userName": "string",
        "title": "string",
        "task": "string"
        "createdAt": "localDateTime",
        "updatedAt": "localDateTime"
    },
    {
        "id": "number",
        "userId": "number",
        "userName": "string",
        "title": "string",
        "task": "string"
        "createdAt": "localDateTime",
        "updatedAt": "localDateTime"
    },
]
```

- response code
```http
200 OK
```
---
### ✅ [GET] /schedule/{id}
- 특정 id를 가진 일정 조회

- request
```http
GET /users HTTP/1.1
Host: localhost:8080
```

- request body

```json
none
```

- response body
```json
{
    "id": "number",
    "userId": "number",
    "userName": "string",
    "title": "string",
    "task": "string"
    "createdAt": "localDateTime",
    "updatedAt": "localDateTime"
}
```

- response code
```http
200 OK | 404 Not Found
```
---
### ✅ [PATCH] /schedule/{id}
- 특정 id를 가진 일정 수정

- request
```http
GET /users HTTP/1.1
Host: localhost:8080
```

- request body

```json
{
    "password": "string",
    "title" : "string",
    "task": "string"
}
```

- response body
```json
{
    "id": "number",
    "userId": "number",
    "userName": "string",
    "title": "string",
    "task": "string"
    "createdAt": "localDateTime",
    "updatedAt": "localDateTime"
}
```

- response code
```http
200 OK | 400 Bad Request | 403 Unauthorized | 404 Not Found
```
---
### ✅ [DELETE] /schedule/{id}
- 특정 id를 가진 일정 삭제

- request
```http
GET /users HTTP/1.1
Host: localhost:8080
```

- request body

```json
{
    "password": "string",
}
```

- response body
```json
none
```

- response code
```http
204 No Content | 400 Bad Request | 403 Unauthorized | 404 Not Found
```

---
## ✅ E-R 다이어그램


### User
```markdown
- id: BIGINT PRIMARY KEY AUTO_INCREMENT
- name: VARCHAR(255) NOT NULL
- email: VARCHAR(255) NOT NULL
- registered_at: DATETIME NOT NULL
- updated_at: DATETIME NOT NULL
```

### Schedule
```markdown
- id: BIGINT AUTO_INCREMENT PRIMARY KEY
- title: VARCHAR(255) NOT NULL
- task: TEXT NOT NULL
- user_id: BIGINT NOT NULL FORIEGN KEY(User.id)
- created_at: DATETIME NOT NULL
- updated_at: DATETIME NOT NULL
- password: VARCHAR(255) NOT NULL
```

### 관계
```markdown
- User 1:N Schedule
```
