"# StayEase" 

## Setup

To run this application locally, follow these steps:

1. Clone this repository:

   ```bash
   git clone https://github.com/harsh8999/StayEase.git
   ```

2. Build the application using Maven:
    ```bash
   mvnw clean install
   ```

3. Run the application:
    ```bash
    java -jar target/StayEase 0.0.1-SNAPSHOT.jar
    ```
    
## Api Dcoumentations Endpoint

- GET /swagger-ui/index.html - Api Documentations

## User Endpoints

- GET /api/v1/ - Welcome To RentRead.

## User Auth Endpoints
### Public Endpoints
- POST /api/v1/auth/signup - Register a new user (Role can be ADMIN, CUSTOMER, HOTEL_MANAGER), Default Role: CUSTOMER.
    ### Request Body
    ```json
    {
    	"firstName": "Harsh",
        "lastName": "Nayak",
        "email" : "harsh@gmail.com",
        "password" : "1234",
        "role" : "CUSTOMER"
    }
    ```
    ### Request Body
    ```json
    {
    	"firstName": "Harsh",
        "lastName": "Nayak",
        "email" : "harsh@gmail.com",
        "password" : "1234"
    }
    ```
- GET /api/v1/auth/generatetoken - Get JSON Web Token (JWT)
    ### Request Body
    ```json
    {
        "email" : "harsh@gmail.com",
        "password" : "1234"
    }
    ```

## Postman Collection
https://elements.getpostman.com/redirect?entityId=7585977-11ac2d76-cdea-44a9-9884-22df4bdc0b21&entityType=collection

## License
Harsh Nayak