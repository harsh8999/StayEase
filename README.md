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

## Hotel Endpoints
- GET /api/v1/hotels/getall - Get all hotels
- POST /api/v1/hotels/add - Add a new hotel. (Role: ADMIN)
    ### Request Body
    ```json
    {
        "name": "Taj Mahal Palace",
        "location": "Mumbai, Maharashtra",
        "description": "Iconic luxury hotel overlooking the Gateway of India.",
        "numberOfAvailableRooms": 3
    }
    ```
    
- PUT /api/v1/hotels/{hotel_id}/update - Update Hotel Details (Role: HOTEL_MANAGER)
    ### Request Body
    ```json
    {
        "name": "Taj Mahal Palace",
        "location": "Mumbai, Maharashtra",
        "description": "Iconic luxury hotel overlooking the Gateway of India.",
        "numberOfAvailableRooms": 3
    }
    ```

- DELETE /api/v1/hotels/{hotel_id}/delete - Delete Hotel (Role: ADMIN)


## Hotel Endpoints
- POST /api/v1/bookings/hotels/{hotel_id}/book - Book a hotel room (Date format: DD-MM-YYYY) 
    ### Request Body
    ```json
    {
        "checkInDate": "10-04-2024",
        "checkOutDate": "11-04-2024"
    }
    ```
    
- DELETE /api/v1/bookings/{booking_id}/cancel - Cancel Booking (Role: HOTEL_MANAGER)

## Testing
Run the JUnit test cases using:

```
mvnw test
```
The tests include Mockito for mocking dependencies and verifying interactions between components.

## Postman Collection
https://elements.getpostman.com/redirect?entityId=7585977-11ac2d76-cdea-44a9-9884-22df4bdc0b21&entityType=collection

## License
Harsh Nayak