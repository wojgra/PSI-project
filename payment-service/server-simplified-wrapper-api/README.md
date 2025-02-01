# Stripe API Integration Payment Service

## Requirements
- Java 11+
- Maven 3.6+
- Stripe account
- VS Code (recommended)

## Setup & Configuration

### 1. Clone and Install
```bash
git clone https://github.com/wojgra/PSI-project/payments-service
cd payments-service
mvn clean install
```

### 2. Configure `.env`
Create a `.env` file and add your Stripe secret key:
```
STRIPE_SECRET_KEY=sk_test_your_key_here
```

### 3. Run Server
Start the server with:
```bash
mvn spring-boot:run
```

---

## API Endpoints

### Products
- **GET** `/api/products` - List all products
- **GET** `/api/products/:id` - Get a single product
- **POST** `/api/products` - Create a new product

#### Example: Create Product
```json
{
    "name": "Product Name",
    "description": "Description"
}
```

### Prices
- **GET** `/api/prices` - List all prices
- **GET** `/api/prices/:id` - Get a single price
- **POST** `/api/prices` - Create a new price

#### Example: Create Price
```json
{
    "unit_amount": 2000,
    "currency": "usd",
    "product": "prod_xxx"
}
```

### Customers
- **GET** `/api/customers` - List all customers
- **GET** `/api/customers/:id` - Get a single customer
- **POST** `/api/customers` - Create a new customer

#### Example: Create Customer
```json
{
    "email": "customer@example.com",
    "name": "John Doe"
}
```

### Coupons
- **GET** `/api/coupons` - List all coupons
- **GET** `/api/coupons/:id` - Get a single coupon
- **POST** `/api/coupons` - Create a new coupon

#### Example: Create Coupon
```json
{
    "percent_off": 10,
    "duration": "once"
}
```

---

## Error Responses
Standard error response format:
```json
{
    "error": "error_code",
    "message": "Error description",
    "status": 400
}
```

### Status Codes
- **200**: Success
- **400**: Bad Request
- **404**: Not Found
- **422**: Invalid Input
- **500**: Server Error