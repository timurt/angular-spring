# Apartment Rentals Management

Backend part

## Technologies
- Spring Boot 2.0.4
- PostgreSQL
- Java 8
- Jacoco 0.7.9
- Checkstyle 8.11

## Installation

Generate jacoco test coverage report
> $ mvn clean test jacoco:report

Run checkstyle verification
> $ mvn checkstyle:check

> $ firefox target/site/checkstyle.html


Run application with H2 database
> $ mvn spring-boot:run -Dspring.profiles.active=h2


