# springboot-pingone-integration
This is spring boot pingone integration demo project to showcase  oauth2 authentication for existing users.

# Spring Boot PingOne Integration

## Overview
This Spring Boot application demonstrates integration with PingOne for authentication and identity services.

## Prerequisites
- Java 17+
- Maven 3.6+
- PingOne account with admin privileges
- Configured PingOne environment with OAuth application

## Setup

### PingOne Configuration
1. Log in to PingOne admin console
2. Create an OAuth application with:
    - Authorization Code grant
    - Redirect URI: `http://localhost:8080/login/oauth2/code/pingone`
    - Scopes: `openid`, `profile`, `email`

### Application Setup
1. Create `application.properties` with:

### properties
# PingOne Config
spring.security.oauth2.client.registration.pingone.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.pingone.client-secret=YOUR_CLIENT_SECRET
spring.security.oauth2.client.registration.pingone.scope=openid,profile,email
spring.security.oauth2.client.provider.pingone.issuer-uri=https://auth.pingone.com/YOUR_ENV_ID/as

### Running the Application

mvn clean install
mvn spring-boot:run
Access at: http://localhost:8080

### Features
OAuth 2.0 Login with PingOne

User profile display

Secure endpoints

Token management

Endpoints
/ - Home

/secured - Authenticated area

/login - OAuth2 login

/logout - Session termination

 ### Troubleshooting
Redirect URI mismatch: Verify exact match in PingOne config

SSL issues: Enable HTTPS in production

Token errors: Check issuer URI and environment sync

Support
PingOne Docs

Spring Security Docs


This version:
1. Uses consistent markdown formatting
2. Maintains all key information
3. Is more concise
4. Keeps proper code block formatting
5. Includes essential links



