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
Access the endpoints on the URL: http://localhost:8080

### Endpoints
+ **/auth** -- POST - email and password for Authentication
+ **/api**
	+ **/create-shop** -- POST - create new shop
	* **/users/** - Access to user Controller
		+ /create -- POST
		+ /delete/{id} -- DELETE
		+ /list-all -- GET
		+ /list/{id}   -- GET
		+ /change-profile/{id} -- PUT
		+ /update/{id} -- PUT
	+ **/pictures**
		+ /uploadFile -- POST
		+ /downloadFile/{fileName} -- GET
