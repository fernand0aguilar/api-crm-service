[![Build Status](https://travis-ci.org/fernand0aguilar/api-crm-service.svg?branch=master)](https://travis-ci.org/fernand0aguilar/api-crm-service)

# API Test - The CRM service

REST API to manage customer data for a small shop.
Project using:
* Java 8
* Spring Boot
* Database MySql with JPA and Spring Data JPA
* Database Migrations with Flyway
* Authentication and Authorization with Spring Security and JWT Tokens (JSON Web Token)
* Tests with JUnity and Mockito
* Continuous Integration with Travis CI
* Virtualization with Vagrant and Puppet

## Getting started guide:

***
## Running Locally
Requirements:
* Maven
* Java jdk8
* mysql

### Commands:
```
Git clone https://github.com/fernand0aguilar/api-crm-service.git
cd api-crm-service
mvn spring-boot:run
```
***

## Running as Virtual Machine
Requirements:
* Vagrant
* virtualbox

### Commands:
```
Git clone https://github.com/fernand0aguilar/api-crm-service.git
cd api-crm-service
vagrant up
```
***

### Usage:
Check the endpoints on the URL: **/swagger-ui.html**
