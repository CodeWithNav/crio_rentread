# RentRead API

## Overview
RentRead is an online book rental system allowing users to manage book rentals efficiently.

## Features
- User Registration and Login
- Book Management
- Rental Management

## API Endpoints
- **POST /register**: Register a new user.
- **GET /books**: Retrieve all available books.
- **POST /books/{bookId}/rent**: Rent a book.
- **POST /books/{bookId}/return**: Return a rented book.
- **Admin endpoints**: 
- **POST /books**: Add a new book.
- **PUT /books/{bookId}**: Update a book.
- **DELETE /books/{bookId}**: Delete a book.
- **GET /my-books**: Retrieve all books rented by the user.


## Running the Application
1. Build the project using Maven.
2. Run Docker Compose to start the MySQL database.
3. Run the application using the following command:
4. Access the application using the following URL: http://localhost:8080
5. Use the API endpoints to interact with the application.

## Running the Application from JAR
1. Locate the JAR file in the target directory.
2. Run the JAR file using the following command:
```shell
java -jar rentread-0.0.1-SNAPSHOT.jar
```
3. Access the application using the following URL: http://localhost:8080
4. Use the API endpoints to interact with the application.
5. Make sure to have the MySQL database running.

## Postman Collection
The Postman collection for the API endpoints is available [here]

