## 📘 API 명세서

### 🔹 Base URL
`https://localhost:8080/api`
---
### ✅ [POST] /schedule
- 새 일정 생성

- request
```http
POST /schedule HTTP/1.1
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
    "createdAt": "string(localDateTime)",
    "updatedAt": "string(localDateTime)"
}
```

- response code
```http
201 Created | 400 Bad Request
```
---
### ✅ [GET] /schedule
- 모든 일정 조회
- 옵션 추가

- request
```http
GET /schedule?userId={number}&updatedStart={date}&updatedEnd={date}&page={number}&size={number} HTTP/1.1
Host: localhost:8080
```

- request body

```json
none
```

- response body
```json
{
    "content": [
        {
            "id": "number",
            "userId": "number",
            "userName": "string",
            "title": "string",
            "task": "string",
            "createdAt": "string(localDateTime)",
            "updatedAt": "string(localDateTime)"
        }
    ],
    "pageable": {
        "pageNumber": "number",
        "pageSize": "number",
        "sort": {
            "empty": "boolean",
            "sorted": "boolean",
            "unsorted": "boolean"
        },
        "offset": "number",
        "paged": "boolean",
        "unpaged": "boolean"
    },
    "last": "boolean",
    "totalPages": "number",
    "totalElements": "number",
    "size": "number",
    "number": "number",
    "sort": {
        "empty": "boolean",
        "sorted": "boolean",
        "unsorted": "boolean"
    },
    "first": "boolean",
    "numberOfElements": "number",
    "empty": "boolean"
}
```

- response code
```http
200 OK | 400 Bad Request
```
---
### ✅ [GET] /schedule/{id}
- 특정 id를 가진 일정 조회

- request
```http
GET /schedule/{id} HTTP/1.1
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
    "createdAt": "string(localDateTime)",
    "updatedAt": "string(localDateTime)"
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
PATCH /schedule/{id} HTTP/1.1
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
    "createdAt": "string(localDateTime)",
    "updatedAt": "string(localDateTime)"
}
```

- response code
```http
200 OK | 400 Bad Request | 403 Forbidden | 404 Not Found
```
---
### ✅ [DELETE] /schedule/{id}
- 특정 id를 가진 일정 삭제

- request
```http
DELETE /schedule/{id} HTTP/1.1
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
204 No Content | 400 Bad Request | 403 Forbidden | 404 Not Found
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
