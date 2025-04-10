# 📌My Spring Security Guide

## 🔐 What is Spring Security?
Spring Security is a powerful framework designed to secure Spring-based applications. It provides mechanisms to control access to app, ensuring that only authorized users can interact with it and perform specific actions. Imagine it as a security guard at the entrance of a building & verifying identities and deciding who can enter and what they can do inside.

# Spring Security focuses on two primary concepts: 
### 1. Authentication ("Who Are You?") 
Authentication is the process of verifying a user’s identity. It ensures that a user is who they claim to be. For example, when a user logs in with a username and password, the system checks if the credentials are correct—that’s authentication.

📍 What Happens During Authentication?

The user provides credentials (username/password).
The system compares these credentials against stored data (e.g., in a database).
If the credentials match → user is authenticated. Else → access is denied.

 🗯️ Authentication is the first line of defense for your app. Without it, anyone could impersonate legitimate users.

### 2. Authorization ("What Can You Do?") 🔒
Authorization happens after authentication. Once the system confirms the user’s identity, authorization determines what they are allowed to do. For example, can they access a specific page? Can they delete data?

📍 What Happens During Authorization?

The system checks the user’s role (e.g., “user” or “admin”).
It evaluates if the role allows the requested action.
If permitted → the action is allowed. Else → the user is blocked.

🗯️ Authorization ensures users can only perform actions they are allowed to. For instance, a regular user shouldn’t access an admin dashboard.
```
Authentication: Showing your ID to enter a concert.

Authorization: Checking if your ticket grants access to the VIP section or just general seating.
```
# 🛠️ Key Components of Spring Security
Spring Security relies on several components to handle authentication and authorization.

### 1. UserDetails: Representing the User 👤

UserDetails is an interface that represents a user in your app. It provides Spring Security with the user’s information for authentication and authorization.

📍 What Does It Contain?

Username (e.g., “john.doe”).
Password (hashed for security).
Authorities (roles like “ROLE_USER” or “ROLE_ADMIN”).
Account status (e.g., is the account active or locked?).

🗯️Role in the Process:

- Used during authentication to verify credentials.

- Used during authorization to check the user’s roles.

🗯️ Insight (Interview Question):   What if a user has multiple roles?

UserDetails supports multiple roles through its getAuthorities() method, which returns a list of roles (e.g., “ROLE_USER” and “ROLE_EDITOR”).

### 2. UserDetailsService: Fetching User Data 📚

UserDetailsService is an interface that retrieves user information from a data source (e.g., a database) and returns a UserDetails object.

📍 What Does It Do?

Finds a user by their username using the loadUserByUsername method.
Returns a UserDetails object with the user’s info.
Throws a UsernameNotFoundException if the user isn’t found.
Role in the Process:

-  During authentication, Spring Security uses UserDetailsService to fetch the user’s UserDetails for verification.

🗯️ Insight (Interview Question):   How does UserDetailsService connect to the database? 

It relies on your app’s data access layer (e.g., Spring Data JPA). For example, it might use a repository to query the database, with connection details set in application.properties.

### 3. AuthenticationManager: Managing Authentication 

AuthenticationManager is the central component that oversees the authentication process in Spring Security.

📍 What Does It Do?

Takes the user’s credentials (e.g., username and password).
Delegates verification to an AuthenticationProvider.
Returns an authenticated object if successful, or throws an error if it fails.

🗯️  Role in the Process:  Initiates authentication when a user tries to log in.

📍 How Does AuthenticationManager Select the Right AuthenticationProvider?

Authen,,ticationManager (via its default implementation, ProviderManager) maintains a list of AuthenticationProviders.
It selects the provider that supports the type of authentication request (e.g., username/password or token-based).
If multiple providers exist, it tries them in order until one succeeds or all fail.

🗯️ Insight:  This flexibility allows Spring Security to support various authentication methods (e.g., password login, OAuth, etc.) in the same app.

### 4. AuthenticationProvider: Verifying Credentials 
AuthenticationProvider performs the actual authentication logic by verifying the user’s credentials.

📍 What Does It Do?

Uses UserDetailsService to fetch the user’s UserDetails.
Compares the provided credentials with the stored ones.
Returns an authenticated object if successful, or throws an error if not.

Role in the Process: Handles the core verification step during authentication.

🗯️ Insight (Interview Question):  What if there are multiple AuthenticationProviders?

AuthenticationManager tries each provider in sequence until one authenticates the user or all fail.

### 5. PasswordEncoder: Securing Passwords 🔒
PasswordEncoder is an interface that handles password hashing and verification to ensure secure storage.

📍 What Does It Do?

Hashes passwords during registration (e.g., using BCrypt).
Verifies passwords during login by comparing the input with the stored hash.

Role in the Process: Ensures passwords are never stored in plain text, protecting them from unauthorized access.

🗯️ Insight (Interview Question):  Why use BCryptPasswordEncoder? 

It uses the BCrypt algorithm, which is slow (to deter brute-force attacks) and adds a random salt to each password for extra security.

### 6. JwtAuthenticationFilter: Validating Tokens 
JwtAuthenticationFilter is a custom filter that validates JSON Web Tokens (JWTs) for protected endpoints.

📍 What Does It Do?

Checks for a JWT in the Authorization header (e.g., Bearer <token>).
Validates the token (e.g., checks if it’s expired).
Sets up the security context if the token is valid, or blocks the request if it’s not.

Role in the Process:  Ensures only users with a valid JWT can access protected resources.

🗯️ Insight (Interview Question):  What happens if the JWT is invalid?

The filter rejects the request with a 403 Forbidden error, ideally with a message like “Token expired, please log in again.”

📍 How Does Spring Security Connect to the Database?

Spring Security doesn’t directly connect to the database—it relies on your app’s data access layer. Here’s how it works:

# 🔄 The Authentication Flow:
This section outlines a typical authentication flow using Spring Security and JWT.

### 1. Signing Up (Register) 

A user submits their info (e.g., username, email, password) to /api/auth/register.

📍 How Does It Work?
The password is hashed using PasswordEncoder.
The user’s data (with a default role like “ROLE_USER”) is saved to the database.

🗯️ Components Involved: PasswordEncoder for hashing the password.
 
### 2. Logging In (Authenticate) 🔑

The user submits their username and password to /api/auth/login.

📍How Does It Work?

AuthenticationManager receives the credentials and selects an AuthenticationProvider.
AuthenticationProvider uses UserDetailsService to fetch the user’s UserDetails.
PasswordEncoder verifies the password.
If successful, a JWT is generated.

🗯️ Components Involved: AuthenticationManager, AuthenticationProvider, UserDetailsService, UserDetails, PasswordEncoder.

### 3. Generating a JWT Token 

A JWT is created and sent to the user after successful login.

📍 How Does It Work?
The JWT includes the user’s username, roles, and an expiration time (e.g., 24 hours).

🗯️ Components Involved: UserDetails for roles.

### 4. Accessing Protected Resources (Authorization) 
The user requests a protected endpoint (e.g., /api/home) with their JWT.

📍 How Does It Work?

JwtAuthenticationFilter validates the JWT.
If valid, the security context is set up with the user’s roles.
Spring Security checks if the user’s roles allow access to the endpoint.
Access is granted or denied accordingly.

🗯️ Components Involved: JwtAuthenticationFilter, UserDetails.

# The Full Flow 

Signup: User submits data → Password hashed → Saved to database.

Login: Credentials submitted → AuthenticationManager verifies → JWT generated.

Access Resource: JWT sent → JwtAuthenticationFilter validates → Access granted/denied.

![Full Authentication Flow](https://github.com/hanin-mohamed/Spring-Security-Real-Steps/raw/main/screens/authentication-flow.png)