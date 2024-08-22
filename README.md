# 🛒 E-Commerce Backend

This **E-Commerce Backend Application** is built using **Spring Boot** and designed for seamless scalability and performance. It provides robust APIs for managing products, categories, users, and orders, with **JWT-based authentication** for secure access. The project integrates **Spring Security** for role-based access control and **Spring Data JPA** for efficient database operations.

## ✨ Key Features

### 🛍️ User Features

- **Cart Management:** Add, update, and remove products from the cart.
- **Order Processing:** Handle order creation, payment, and tracking.
- **User Authentication:** Secure login and registration using JWT.
- **Product Browsing:** View and search for products by categories and keywords.
- **Order History:** View past orders and track their status.

### ⚙️ Admin Features

- **Product Management:** Add, update, and remove products from the catalog.
- **Category Management:** Create, update, and delete product categories.
- **User Management:** View and manage user accounts.
- **Order Management:** Monitor and manage all customer orders.

## 🛠️ Technologies & Tools

- **[Spring Boot](https://spring.io/projects/spring-boot)** - A framework to simplify the development of REST APIs
- **[Spring Security](https://spring.io/projects/spring-security)** - Ensures secure access to the application
- **[JWT (JSON Web Token)](https://jwt.io/)** - For secure authentication and authorization
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)** - Simplifies database operations
- **[Hibernate](https://hibernate.org/)** - ORM for database management
- **[Postgresql](https://www.postgresql.org/)** - Relational database management system
- **[Swagger](https://swagger.io/)** - For API documentation and testing
- **[Maven](https://maven.apache.org/)** - Dependency management and build tool

## 🚀 Getting Started

To get started with the E-Commerce Backend Application, follow these steps:

### Prerequisites

- **Java 17** or above
- **Maven** 3.8+
- **PostgreSql** Database

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/vasanthmax/SpringEcommerce.git

2. **Navigate to the project directory:**

   ```bash
   cd SpringEcommerce

3. **Configure the Application Properties:**

    ## ⚙️ Configuration

    ### Application Properties

    Configure the application settings in `src/main/resources/application.properties`:

    - **Database Configuration:**
      
       ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/yourdbname
      spring.datasource.username=yourusername
      spring.datasource.password=yourpassword

4. **Build the Project:**

   ```bash
   mvn clean install

5. **Run the Project:**

   ```bash
   mvn spring-boot:run

5. **Access the Application:**

   ```bash
   http://localhost:8080/

## 📚 API Documentation

Explore and test the API endpoints using the Swagger UI:

- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

For detailed API documentation, check the Swagger UI after starting the application.
  
