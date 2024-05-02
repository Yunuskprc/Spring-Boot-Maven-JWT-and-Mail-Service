# Spring Boot Maven JWT and Mail Service
Using Spring Security with JWT for authentication - authorization  and  Email verification and email sending services

This project has been created using Spring Boot Maven with MySQL. The purpose of the project is to provide users with the simplest form of authentication, authorization, email sending, and email verification processes.



## Contents

- [Requirements]
- [Setup]
- [Usage]
- [Projt Structure]
- [Example Usage]

## Requirements

- **Java** 17
- **Spring Boot** 3.2.5
- **MySQL**

## Setup

To install and run the project, follow the steps below:

1. **Clone the repository**
    ```bash
    git clone [https://github.com/Yunuskprc/Full_Stack_Web_Project.git](https://github.com/Yunuskprc/Spring-Boot-Maven-JWT-and-Mail-Service)
    cd Spring-Boot-Maven-JWT-and-Mail-Service
    ```

2. **Install dependencies for the backend**
    ```bash
    cd Spring-Boot-Maven-JWT-and-Mail-Service
    mvn install
    ```
3. **Create a database named 'auth' in MySQL**
   ```bash
   create database auth
   ```
4. **Update your MySQL username and password in the application.properties file.**
   
5. **Run backend**
    ```bash
    cd Spring-Boot-Maven-JWT-and-Mail-Service
    mvn spring-boot:run
    ```


## Usage

- When the backend is running, you can access the APIs using the address http://localhost:8080.
  

## Projt Structure

- `auth/src/main/java/com/jwt/auth/Entity/Modal`: The entity classes are located here. These are MailVerify, User, UserInfo.
- `auth/src/main/java/com/jwt/auth/Entity/dto`: The dto classes are located here. These are AuthRequest and MailStructure.
  **AuthRequest** This is the request class for the login process.
  **MailStructure** This is the class used to create a template when sending an email.
  
- `auth/src/main/java/com/jwt/auth/Repository` : The repository interfaces for the entity classes are found here. These are MailVerifyRepo, UserInfoRepo, UserRepository.
- `auth/src/main/java/com/jwt/auth/Controller` : The controller endpoints are located here. These are MailController and UserController.  
- `auth/src/main/java/com/jwt/auth/Service` : The Service file are located here. These are auth, mail and Token.
- `auth/src/main/java/com/jwt/auth/Service/Token` : The Json Web Token Service are located here. These are JwtService and UserInfoDeteails
  **JwtService** "This service generates tokens, can verify their validity, and extract information from the tokens.".
  **UserInfoDetails**  "This service implements the UserDetails interface to provide customized usage for users.".

- `auth/src/main/java/com/jwt/auth/Service/auth` : The User Service are located here.
  **UserService** This service implements the UserDetailsService interface and provides methods for user login and user registration
- `auth/src/main/java/com/jwt/auth/Service/mail` : The Mail Service are located here.
  **MailService** This service generates random codes for mail sending and mail verification processes, and contains methods for verification.
- `auth/src/main/java/com/jwt/auth/Security` : The Security Classes are located here. These are JwtAuthFilter and SecurityConfig.
  **JwtAuthFilter** This filter class is where authorization processes for incoming requests are handled. It extends the OncePerRequestFilter class. It extracts the username from the JWT token in incoming requests from the client and checks whether the user has permission for the request.
  **SecurityConfig** This Config class configures the URLs to be authorized, along with their corresponding permissions. It also sets up CORS configurations and manages session management.




## Example Usage

The user first registers in the system with the data format shown in the first image. After registering, to log in to the system, the user sends a request to the server in the format shown in the second image. After authentication is successful, the server sends a token. We will use this token for subsequent requests. After the user logs in, to verify the email, the user selects the "Bearer Token" type from the "Authorization" section in Postman, pastes the token given during login, and makes a request as shown in the third image. As a result of the request, a verification code is sent to the user's email address as shown in the fifth image. The user copies this code, pastes the token into the relevant field as in the code generation request, and makes a request as shown in the fifth image to verify the email.

  ![register](https://github.com/Yunuskprc/Spring-Boot-Maven-JWT-and-Mail-Service/assets/91240806/aac85d5e-5879-4cfd-99a2-2c907e6d15f6)
  ![login](https://github.com/Yunuskprc/Spring-Boot-Maven-JWT-and-Mail-Service/assets/91240806/f4e092fd-94da-4172-b994-a22401872e8f)
  ![verifycodeGenerate](https://github.com/Yunuskprc/Spring-Boot-Maven-JWT-and-Mail-Service/assets/91240806/d5e2093d-aded-4abd-87eb-454cd482def9)
  ![verifycodeIsVerify](https://github.com/Yunuskprc/Spring-Boot-Maven-JWT-and-Mail-Service/assets/91240806/b4f601f3-b40d-4b9b-8c89-1370d6f17262)
  ![mail](https://github.com/Yunuskprc/Spring-Boot-Maven-JWT-and-Mail-Service/assets/91240806/d775d6d6-0e38-4bfc-a54a-43fe5b08e634)



