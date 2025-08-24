# Here is a sample README.md for your Spring Boot, Cassandra, Maven, REST API project.
# It explains setup, build, and run instructions using Docker Compose.

# Spring Boot Cassandra REST API

This project is a REST API built with Spring Boot, Maven, and Apache Cassandra.

## Features

- Java Spring Boot REST API
- Cassandra database integration
- Maven build system
- Docker Compose for local development

## Prerequisites

- Docker & Docker Compose
- Java 17+ (for local builds)
- Maven (for local builds)

## Getting Started

### Clone the repository

```sh
git clone git@github.com:Gopal-selvaraj/InvoiceManagementSystem.git
cd invoicemanagementsystem

# 2. Build and run with Docker Compose

docker-compose up --build

The Cassandra service runs on port 9042.
The Spring Boot app runs on port 8080.

# 3. API Usage
   Access the REST API at:
   http://localhost:8080/

Refer to your API documentation or source code for available endpoints.

# Project Structure

- src/ - Java source code
- pom.xml - Maven build file
- Dockerfile - Container build instructions
- docker-compose.yml - Multi-container orchestration

# Configuration
Cassandra connection properties are set via environment variables in docker-compose.yml.

# Logging
- Uses Logback for logging.
- Configure log level via `LOG_LEVEL` environment variable.

# Running Tests
- Run tests with Maven: `mvn test`
- Test results are shown in the console and `target/surefire-reports`.

# Docker
- Logging config is mounted from `src/main/resources/logback.xml`.
- Set log level in `docker-compose.yml` using `LOG_LEVEL`.
- To run tests in Docker: `docker-compose run app mvn test`

# Project Repository
Replace https://github.com/Gopal-selvaraj/InvoiceManagementSystem.git and add more details as needed for your specific API endpoints.
