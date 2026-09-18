# Todo API

Spring Boot와 Spring Data JPA로 만든 할 일 관리 REST API입니다. 회원 기능 없이 할 일의 등록, 조회, 수정, 삭제와 완료 상태 변경을 제공합니다.

## 실행 방법

### 요구 환경

- Docker 및 Docker Compose
- 로컬에서 직접 실행할 경우 JDK 21

### Docker로 실행

```bash
docker compose up -d --build
```

컨테이너 상태를 확인합니다.

```bash
docker compose ps
```

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

종료할 때는 다음 명령을 사용합니다. MySQL 데이터는 Docker 볼륨에 유지됩니다.

```bash
docker compose down
```

데이터까지 삭제하려면 다음 명령을 사용합니다.

```bash
docker compose down -v
```

## API 명세

| 기능 | 메서드 | 주소 | 성공 상태 코드 |
| --- | --- | --- | --- |
| 할 일 등록 | `POST` | `/todos` | `201 Created` |
| 할 일 목록 조회 | `GET` | `/todos?page=0&size=10&completed=false` | `200 OK` |
| 할 일 상세 조회 | `GET` | `/todos/{todoId}` | `200 OK` |
| 할 일 제목 수정 | `PATCH` | `/todos/{todoId}` | `200 OK` |
| 완료 상태 변경 | `PATCH` | `/todos/{todoId}/completed` | `200 OK` |
| 할 일 삭제 | `DELETE` | `/todos/{todoId}` | `204 No Content` |

### 할 일 등록

```http
POST /todos
Content-Type: application/json
```

```json
{
  "name": "README 작성하기"
}
```

```json
{
  "todoId": 1,
  "name": "README 작성하기",
  "createdAt": "2026-09-18T06:40:26.189391708"
}
```

제목은 공백일 수 없으며 최대 100자입니다.

### 할 일 목록 조회

```http
GET /todos?page=0&size=10&completed=false
```

`page`는 0부터 시작하고, `completed`를 생략하면 완료 여부와 관계없이 조회합니다.

```json
{
  "todos": [
    {
      "todoId": 1,
      "name": "README 작성하기",
      "completed": false,
      "createdAt": "2026-09-18T06:40:26.189392"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

### 할 일 상세 조회

```http
GET /todos/1
```

```json
{
  "todoId": 1,
  "name": "README 작성하기",
  "completed": false,
  "createdAt": "2026-09-18T06:40:26.189392"
}
```

### 할 일 제목 수정

```http
PATCH /todos/1
Content-Type: application/json
```

```json
{
  "name": "README 제출하기"
}
```

```json
{
  "todoId": 1,
  "name": "README 제출하기",
  "updatedAt": "2026-09-18T06:45:00.000000"
}
```

### 완료 상태 변경

`completed`에 `true` 또는 `false`를 전달합니다.

```http
PATCH /todos/1/completed
Content-Type: application/json
```

```json
{
  "completed": true
}
```

```json
{
  "todoId": 1,
  "completed": true,
  "updatedAt": "2026-09-18T06:40:46.652853426"
}
```

### 할 일 삭제

```http
DELETE /todos/1
```

성공하면 응답 본문 없이 `204 No Content`를 반환합니다.

## 오류 응답

모든 오류는 같은 형식으로 반환합니다.

```json
{
  "status": 400,
  "message": "name: 과제명은 필수 입력 값입니다."
}
```

| 상황 | 상태 코드 |
| --- | --- |
| 제목이 비어 있거나 100자를 초과함 | `400 Bad Request` |
| JSON 또는 경로 변수의 형식이 잘못됨 | `400 Bad Request` |
| 요청한 할 일이 존재하지 않음 | `404 Not Found` |
| 처리하지 못한 서버 오류 | `500 Internal Server Error` |

## 설계 설명

- 복수형 명사 `/todos`를 리소스 주소로 사용했습니다.
- 생성에는 `POST`, 조회에는 `GET`, 일부 속성 변경에는 `PATCH`, 삭제에는 `DELETE`를 사용했습니다.
- 생성 성공은 새 리소스가 만들어졌음을 나타내는 `201 Created`, 삭제 성공은 반환할 본문이 없으므로 `204 No Content`를 사용했습니다.
- 완료 상태 변경은 제목 수정과 요청 형식이 달라 `/todos/{todoId}/completed`로 분리했습니다. 현재 상태를 명시적으로 전달하므로 같은 요청을 반복해도 결과가 같습니다.
- 요청과 응답은 DTO를 사용해 JPA 엔티티가 API에 직접 노출되지 않도록 했습니다.
- MySQL은 널리 사용되는 관계형 데이터베이스이고, Docker Compose로 동일한 실행 환경을 쉽게 재현할 수 있어 선택했습니다.

## 실행 결과

아래 요청은 Docker Compose 환경에서 순서대로 실행했습니다.

### 1. 할 일 등록

```bash
curl -i -X POST http://localhost:8080/todos \
  -H 'Content-Type: application/json' \
  -d '{"name":"README 작성하기"}'
```

```text
HTTP/1.1 201
```

```json
{"todoId":1,"name":"README 작성하기","createdAt":"2026-09-18T06:40:26.189391708"}
```

### 2. 목록 조회

```bash
curl -i 'http://localhost:8080/todos?page=0&size=10&completed=false'
```

```text
HTTP/1.1 200
```

```json
{"todos":[{"todoId":1,"name":"README 작성하기","completed":false,"createdAt":"2026-09-18T06:40:26.189392"}],"page":0,"size":10,"totalElements":1,"totalPages":1}
```

### 3. 완료 처리

```bash
curl -i -X PATCH http://localhost:8080/todos/1/completed \
  -H 'Content-Type: application/json' \
  -d '{"completed":true}'
```

```text
HTTP/1.1 200
```

```json
{"todoId":1,"completed":true,"updatedAt":"2026-09-18T06:40:46.652853426"}
```

### 4. 삭제

```bash
curl -i -X DELETE http://localhost:8080/todos/1
```

```text
HTTP/1.1 204
```

### 5. 잘못된 제목 요청

```bash
curl -i -X POST http://localhost:8080/todos \
  -H 'Content-Type: application/json' \
  -d '{"name":"   "}'
```

```text
HTTP/1.1 400
```

```json
{"status":400,"message":"name: 과제명은 필수 입력 값입니다."}
```

### 6. 존재하지 않는 할 일 조회

```bash
curl -i http://localhost:8080/todos/999999
```

```text
HTTP/1.1 404
```

```json
{"status":404,"message":"할 일을 찾을 수 없습니다."}
```
