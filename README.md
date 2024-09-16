

# Payment Service

## Overview

The **Payment Service** is a microservice responsible for handling payment processing within the overall system. It listens to payment requests from other services via Kafka, processes payments, and emits payment status events. It is designed to integrate smoothly into a microservice architecture where Kafka is used for event-driven communication.

## Key Features

- Listens to the `process_payment` Kafka topic for incoming payment requests.
- Simulates payment processing and generates random payment success or failure statuses.
- Sends the payment status back to the `payment_status` Kafka topic for further processing by other microservices, such as the order service.
- Uses **Kafka** for communication and **Spring Boot** for service orchestration.
- Saves payment details and statuses in the database using **Spring Data JPA**.


## How It Works

### PaymentEventListener

The `PaymentEventListener` listens for incoming payment processing requests on the Kafka topic `process_payment`. Once an event is received, it extracts the necessary order details (order ID, amount) and forwards them to the `PaymentService` for processing.

### PaymentService

- **processPayment(Long orderId, Double amount):** This method simulates payment processing. It randomly decides whether a payment succeeds or fails. The payment is then stored in the database, and the status is published back to Kafka on the `payment_status` topic.

### Kafka Topics

1. **process_payment:** 
   - Used to listen for incoming payment requests from other services (e.g., Order Service).
   - The payload includes order details that need to be processed.

2. **payment_status:** 
   - After processing the payment, the service publishes the payment status (SUCCESS or FAILED) to this topic.
   - Other services can subscribe to this topic to receive updates on the payment status for an order.

### Example Flow

1. The `OrderService` sends a Kafka message to the `process_payment` topic, requesting payment processing for a specific order.
2. The `PaymentEventListener` listens for this event and forwards the details to the `PaymentService`.
3. The `PaymentService` processes the payment (randomly succeeding or failing) and stores the result in the database.
4. The `PaymentService` sends a response to the `payment_status` topic with the payment result.
5. Other services (such as the `OrderService`) can listen to the `payment_status` topic and update their respective records based on the result.

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd payment-service
   ```
3. Configure the `application.properties` with your database and Kafka settings:
   ```properties
   # Kafka configuration
   spring.kafka.bootstrap-servers=localhost:9092
   spring.kafka.consumer.group-id=payment_group

   # Database configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/paymentdb
   spring.datasource.username=root
   spring.datasource.password=root
   spring.jpa.hibernate.ddl-auto=update
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Technologies Used

- **Java**: Programming language.
- **Spring Boot**: Framework used for building microservices.
- **Spring Data JPA**: For database interaction and persistence.
- **Apache Kafka**: Message broker for event-driven communication.
- **MySQL**: Relational database for storing payment records.
- **Jackson**: For JSON serialization and deserialization.

## Future Enhancements

- **Real Payment Gateway Integration**: Replace the random payment success/failure logic with real payment gateway integrations such as Stripe or PayPal.
- **Improved Error Handling**: Implement retry mechanisms and more robust error-handling strategies.
- **Idempotency**: Ensure idempotency for payment processing to avoid issues such as double payments.

