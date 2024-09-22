# Java Spring Web Angular Shop

This is a full-stack web application built with Java Spring Boot for the backend and Angular for the frontend. The project is an online shopping platform that allows users to browse products, place orders, manage profiles, and view purchase history. Admins can manage users and orders.

## Features

- **User Authentication**: Implemented using JWT tokens to secure user sessions.
- **User Registration & Login**: New users can sign up, and existing users can log in to their accounts.
- **Product Management**: Users can browse, search, and filter products.
- **Order Management**: Users can create, update, and delete orders.
- **Invoice Management**: Users can view detailed invoices of their orders.
- **Profile Management**: Users can update personal information and check their purchase history.
- **Admin Panel**: Admins can manage user accounts, view orders, and control platform activities.
- **Secure Routing**: Spring Security is used to manage role-based access control (RBAC).
- **Shopping Cart**: Integrated shopping cart and checkout process.
- **Token-Based Authentication**: Using JWT for secure user sessions.

## Technology Stack

- **Frontend**: Angular
- **Backend**: Java Spring Boot
- **Security**: Spring Security, JWT for authentication
- **Database**: MySQL
- **Hosting**: [Your hosting platform, e.g., Heroku, AWS, etc.]

## Installation and Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/ToanNguyen9602/Java-Spring_Web_Angular_Shop.git
    ```

2. Backend (Spring Boot):

    - Navigate to the backend directory and install dependencies:

      ```bash
      cd backend
      mvn clean install
      ```

    - Start the Spring Boot application:

      ```bash
      mvn spring-boot:run
      ```

3. Frontend (Angular):

    - Navigate to the frontend directory and install dependencies:

      ```bash
      cd frontend
      npm install
      ```

    - Run the Angular application:

      ```bash
      ng serve
      ```

4. Open the application in your browser:

    ```
    http://localhost:4200
    ```

## Environment Variables

To run this project, you will need to set up the following environment variables:

- `JWT_SECRET`: Secret key for signing JWT tokens.
- `DATABASE_URL`: MySQL database connection string.
- `SPRING_SECURITY_ENABLED`: Enable or disable security features (set to `true` or `false`).

## Usage

1. **Users**:
    - Register and log in to the platform.
    - Browse products, add to cart, and complete purchases.
    - View order details and manage profile information.

2. **Admins**:
    - Manage users and monitor orders through the admin panel.
    - Control platform operations such as user access and product management.

## Future Enhancements

- Implementing payment gateway integration.
- Enhancing the user interface with more interactive elements.
- Adding product reviews and ratings.

## License

This project is licensed under the MIT License.

