# Children Details Pagination and Sorting REST API (Spring Boot)

A full-stack web application featuring pagination and multi-attribute sorting for children records using a **Spring Boot 4 / Java 21+ RESTful backend** and an interactive web UI.

---

## Architecture Overview

- **Backend**: Spring Boot (`@RestController`, `@Service`, Model, and DTOs)
- **Frontend**: Responsive HTML5 / CSS3 / Vanilla JavaScript (served directly from Spring Boot's `src/main/resources/static/index.html`)
- **Build Tool**: Maven Wrapper (`mvnw` / `mvnw.cmd`) — no separate Maven installation required.
- **Testing**: JUnit 5 test suite verifying pagination, sorting algorithms, creation, and input validations.
- **Port**: Default port `8080` (configurable in `src/main/resources/application.properties`).

---

## REST Endpoints

### 1. `GET /api/children`
Retrieves paginated and sorted list of children.

**Query Parameters:**
- `page` *(optional, default: `1`)*: 1-indexed page number.
- `sortBy` *(optional, default: `name`)*:
  - `name` or `name-asc`: Name ascending (A → Z)
  - `name-desc`: Name descending (Z → A)
  - `age` or `age-asc`: Age ascending (Youngest first)
  - `age-desc`: Age descending (Oldest first)
  - `id-asc`: ID ascending (1 → 40)
  - `id-desc`: ID descending (40 → 1)
  - `gender-female`: Female first, then sorted by name
  - `gender-male`: Male first, then sorted by name
- `limit` *(optional, default: `10`)*: Records per page.

**Response Structure (200 OK):**
```json
{
  "children": [
    { "id": 1, "name": "Alice Johnson", "age": 7, "gender": "Female" },
    ...
  ],
  "currentPage": 1,
  "totalPages": 4,
  "totalRecords": 40
}
```

---

### 2. `POST /api/children`
Adds a new child record.

**Request Body:**
```json
{
  "name": "Leo Parker",
  "age": 6,
  "gender": "Male"
}
```

**Response (201 Created):**
```json
{
  "message": "Child added successfully!",
  "child": {
    "id": 41,
    "name": "Leo Parker",
    "age": 6,
    "gender": "Male"
  }
}
```

---

## How to Run the Application

### Option A: Using Maven Wrapper (Recommended)
```powershell
.\mvnw.cmd spring-boot:run
```
*(On Linux/macOS, run `./mvnw spring-boot:run`)*

### Option B: Build and Run the Packaged JAR
1. Build the JAR:
```powershell
.\mvnw.cmd clean package
```
2. Run the JAR:
```powershell
java -jar target\children-details-api-1.0.0.jar
```

Once started, open your browser and navigate to:
```
http://localhost:8080/
```

---

## Running Automated Tests
Run all unit and integration tests with:
```powershell
.\mvnw.cmd test
```

