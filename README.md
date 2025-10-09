# 🛒 SpringBootECom

Welcome to **SpringBootECom**, a modular e-commerce web application built with Java and Spring Boot. This project demonstrates secure, scalable design, product and category management, and role-based user access.

---

## 🚀 Features

- **Product Management**
  - Add, update, and delete products
  - Search, filter, sort, and paginate products
  - Upload product images

- **Category Management**
  - Create, update, and delete categories
  - Filter products by category

- **User Roles**
  - Role-based access: Admin, Seller, User

- **Secure Authentication**
  - JWT-based authentication and authorization (see `security/jwt/JwtUtils.java`)

---

## 🗂️ Main Modules

- **Controllers**
  - `ProductController`: APIs for managing products ([source](src/main/java/com/ecommerce/project/Controller/ProductController.java))
  - `CategoryController`: APIs for managing categories ([source](src/main/java/com/ecommerce/project/Controller/CategoryController.java))

- **Services**
  - `ProductService` & `ProductServiceImp`: Product business logic
  - `CategoryService` & `CategoryServiceImp`: Category business logic

- **Models**
  - `Product`, `Category`, `AppRole` (user roles)

- **Repositories**
  - `ProductRepository`, `CategoryRepository`

- **Security**
  - JWT support and configuration

- **Configuration**
  - App constants and global configuration (`Config/AppConstant.java`, `Config/AppConfig.java`)

---

## 🛠️ Technologies

- **Backend:** Java, Spring Boot, Spring Data JPA, Jakarta Validation, ModelMapper
- **Security:** Spring Security, JWT
- **Database:** (Add your DB engine, e.g., MySQL/PostgreSQL)
- **Build Tool:** Maven

---

## 🚀 Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/Akash-Singh-Rajput/SpringBootECom.git
   cd SpringBootECom
   ```

2. **Configure your database**
   - Edit `application.properties` with your DB credentials

3. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the API**
   - Product APIs: `/api/public/products`
   - Category APIs: `/api/public/categories`

---

## 📄 Data Models

- **Product**
  - `productId`, `productName`, `image`, `description`, `quantity`, `price`, `discount`, `specialPrice`, `category`
- **Category**
  - `categoryId`, `categoryName`, `productList`
- **AppRole**
  - `ROLE_ADMIN`, `ROLE_USER`, `ROLE_SELLER`

---

## 🤝 Contributing

Contributions are welcome! Fork the repo and submit a pull request.

---

## 👤 Author

Developed by [Akash Singh Rajput](https://github.com/Akash-Singh-Rajput)

---

*For further personalization, add database details, deployment info, screenshots, or API documentation as needed.*