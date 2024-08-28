### E-commerce Application

This console-based system is designed to manage various aspects of an e-commerce platform, including user authentication, product management, and order processing. Despite its simplicity, the project is a strong foundation built on Object-Oriented Programming (OOP) principles and a layered architecture to ensure modularity, maintainability, and scalability.

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Key Features](#key-features)
3. [Project Structure](#project-structure)
4. [Setup Instructions](#setup-instructions)
5. [Usage Guide](#usage-guide)
6. [Code Highlights](#code-highlights)
7. [Exception Handling](#exception-handling)
8. [Data Persistence](#data-persistence)

---

## Project Overview

The **E-commerce Application** offers a simple, text-based interface that allows users to perform essential e-commerce operations. The project is designed with a focus on clean code and strict adherence to OOP principles, making it a solid starting point for developing more complex e-commerce solutions.

### Core Concepts:

- **Layered Architecture**: The project is divided into distinct layers—Controllers, Services, Models, Exceptions, and Utilities—each handling specific responsibilities. This separation of concerns enhances the modularity and maintainability of the application.
- **OOP Principles**: Inheritance, polymorphism, and encapsulation are extensively used to create a modular and scalable codebase. Each class is designed with a single responsibility, following the SOLID principles.
- **Exception Handling**: Custom exceptions are implemented to manage specific error scenarios, ensuring the application provides meaningful and user-friendly feedback.

---

## Key Features

- **User Authentication**: Users can log in either as a regular user or an admin, each with different access levels and privileges.
- **Product Management**: Admins can add, update, delete, and list products in the catalog.
- **Order Management**: Users can place orders for products, and the system manages stock levels and order statuses.
- **Data Persistence**: The application saves user, product, and order data to text files, allowing data to persist across sessions.
- **Console-Based Interface**: The application runs entirely in the console, making it lightweight and straightforward to use.

---

## Project Structure

```plaintext
src/main/com/ecommerce
├── controllers
│   ├── AuthController.java
│   ├── LoginManager.java
│   ├── AdminController.java
│   ├── UserController.java
│   ├── OrderController.java
│   └── ProductController.java
├── exceptions
│   ├── OrderNotFoundException.java
│   ├── ProductNotFoundException.java
│   ├── AdminNotFoundException.java
│   └── UserNotFoundException.java
├── models
│   ├── Admin.java
│   ├── Order.java
│   ├── Product.java
│   └── User.java
├── services
│   ├── AuthenticationService.java
│   ├── OrderService.java
│   ├── ProductService.java
│   ├── AdminService.java
│   └── UserService.java
├── utils
│   ├── InputValidator.java
│   ├── DataInitializer.java
│   ├── DataParser.java
│   └── FileHandler.java
└── Main.java
```

### Key Components:

- **Controllers**: Manage the interaction between the user and the system, processing inputs and generating outputs.
- **Services**: Contain the business logic for authentication, order processing, and product management.
- **Models**: Represent entities within the system, such as User, Product, and Order, encapsulating their attributes and behaviors.
- **Exceptions**: Custom exceptions that handle specific error cases, ensuring that the application remains robust.
- **Utils**: Utility classes for common tasks such as input validation and data persistence through file handling.

---

## Setup Instructions

To get started with the E-commerce Application, follow these steps:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/f2rkan/commerce-core.git
   cd ecommerce-app
   ```

2. **Compile the Code**:
   Ensure you have Java installed. Compile the code using your preferred IDE or via the command line:
   ```bash
   javac -d bin src/main/com/ecommerce/**/*.java
   ```

3. **Run the Application**:
   Start the application using the following command:
   ```bash
   java -cp bin main.com.ecommerce.Main
   ```

---

## Usage Guide

### Login

Upon starting the application, you will be prompted to log in.

- **Admin Login**: Admins have full control over product management and can view all orders.
- **User Login**: Regular users can browse products, place orders, and view their own orders.

### Product Management (Admin Only)

- **Add Product**: Enter product details such as name, description, price, and stock.
- **Update Product**: Modify existing product details by providing the product ID.
- **Delete Product**: Remove a product from the catalog.
- **List Products**: View all products in the catalog.

### Order Management

- **Place Order**: Select a product and specify the quantity to create an order.
- **View Order**: Retrieve details of a specific order by providing the order ID.

---

## Code Highlights

### LoginManager.java

Handles user authentication by interacting with the `AuthenticationService`. Depending on the type of user (admin or regular), it provides appropriate access and feedback.

```java
public boolean login(Scanner scanner) {
    System.out.print("Enter username: ");
    String username = scanner.nextLine();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();

    Optional<Object> loggedIn = authenticationService.authenticate(username, password);
    if (loggedIn.isPresent()) {
        loggedInEntity = loggedIn.get();
        if (loggedInEntity instanceof Admin) {
            System.out.println("Logged in as admin.");
        } else if (loggedInEntity instanceof User) {
            System.out.println("Logged in as user.");
        }
        return true;
    } else {
        System.out.println("Invalid username or password.");
        return false;
    }
}
```

### ProductController.java

Manages product-related operations such as adding, updating, deleting, and listing products. It validates all inputs to ensure the data integrity and provides feedback on the operation's success.

```java
public void addProduct() {
    System.out.print("Enter product name: ");
    String name = InputValidator.validateStringInput();
    System.out.print("Enter product description: ");
    String description = InputValidator.validateStringInput();
    System.out.print("Enter product price: ");
    double price = InputValidator.validateDoubleInput();
    System.out.print("Enter product stock: ");
    int stock = InputValidator.validateIntInput();

    Product product = new Product(0, name, description, price, stock);
    productService.addProduct(product);
    System.out.println("Product added successfully!");
}
```

### OrderController.java

Handles order operations, ensuring that the selected product is available and that the stock is updated accordingly.

```java
public void addOrder() {
    listProducts();
    int productId = InputValidator.getValidInt("Enter Product ID: ");
    System.out.print("Enter quantity: ");
    int quantity = InputValidator.validateIntInput();

    try {
        Product product = productService.getProduct(productId);
        Order newOrder = new Order(0, product, quantity, "Pending");
        orderService.addOrder(newOrder);
        System.out.println("Order added successfully with ID: " + newOrder.getId());
    } catch (ProductNotFoundException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```

---

## Exception Handling

The application uses custom exception classes to handle errors gracefully, ensuring robustness and user-friendly error messages.

### Example:

- **ProductNotFoundException**: Thrown when an operation attempts to access a non-existent product.
- **OrderNotFoundException**: Raised when an order cannot be found by the provided ID.

```java
public Product getProduct(int id) throws ProductNotFoundException {
    Product product = productRepository.findById(id);
    if (product == null) {
        throw new ProductNotFoundException("Product with ID " + id + " not found.");
    }
    return product;
}
```

---

## Data Persistence

The application persists user, product, and order data using text files. This allows the application to load and save data across different sessions, ensuring continuity.

### FileHandler.java

This utility class handles all file-related operations, including reading from and writing to user, product, and order files.

```java
public static void loadUsersFromFile(UserService userService) {
    try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String username = parts[1];
            String password = parts[2];
            String email = parts[3];
            String role = parts[4];
            User user = new User(id, username, password, email, role);
            userService.createUser(user);
        }
    } catch (IOException e) {
        System.out.println("No previous users found.");
    }
}
```

The `FileHandler` class also includes methods for saving users, orders, and products to their respective files, ensuring that all changes are preserved between sessions.

--- 
