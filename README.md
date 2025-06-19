# Quiz Application

A simple Spring Boot REST API project for managing quizzes, featuring secure authentication and authorization using Spring Security and JWT.

## Features

- User registration and login with JWT-based authentication
- Secure endpoints for creating and retrieving quiz data
- Role-based access control
- PostgreSQL database integration
- Follows RESTful API best practices

## Technologies Used

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Hibernate/JPA
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL database

### Setup

1. **Clone the repository**
git clone https://github.com/nityasama/Quiz.git
cd Quiz
2. **Configure the database**

Update your `src/main/resources/application.yaml` with your PostgreSQL credentials:
spring:
datasource:
url: jdbc:postgresql://<HOST>:<PORT>/<DB_NAME>
username: <YOUR_DB_USERNAME>
password: <YOUR_DB_PASSWORD>

3. **Build and run the application**
mvn clean install
mvn spring-boot:run

4. **API Endpoints**

- `POST /auth/signup` — Register a new user
- `POST /auth/login` — Authenticate and receive a JWT
- `GET /quiz/category` — Retrieve quizzes (JWT required) based on quiz category(Java, Python etc)
- `POST /quiz` — Create a new quiz (JWT required)
- 'POST /quiz/submit/{quizId}' — Calculates the result for a submitted quiz
- 'POST /questions/add' — Create a question to add into Quiz
- 'GET /question' — Retrieve questions based on Category/Id or retrieves All questions(seperate endpoints for each requirement)
- 'GET /auth/loginAttempts' — Retrieves the information about the login attempts done by the user 

## Usage

- Use a tool like Postman to test the API endpoints.
- Register a user, log in to receive a JWT, and include the token in the `Authorization` header (`Bearer <token>`) for protected endpoints.

## License

This project is open source and available under the [MIT License](LICENSE).

---

**Project by [nityasama](https://github.com/nityasama/Quiz)**

