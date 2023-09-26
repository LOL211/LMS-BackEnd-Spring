# Project Overview

This project is a full-stack Learning Management System (LMS) application developed by Kush Banbah. It offers grade and file storage functionalities.

## Live Demo

The website is hosted [here](https://lms-front-end-next-js.vercel.app).

A total of **24 dummy accounts** have been created for testing purposes:

**Teacher Accounts**
Four teacher accounts have been created with the following email addresses: 
- `teacher1@gmail.com`
- `teacher2@gmail.com`
- `teacher3@gmail.com`
- `teacher4@gmail.com`

**Student Accounts**
Twenty student accounts have been created with email addresses in the format `studentX@gmail.com`, where X ranges from 1 to 20.

**Passwords**
The password for each account is `test` followed by the account number. For example, the password for both `student1@gmail.com` and `teacher1@gmail.com` is `test1`.
## Repositories

- [Front-end Repository](https://github.com/LOL211/LMS-FrontEnd-NextJS)
- [Back-end Repository](https://github.com/LOL211/LMS-BackEnd-Spring)

## Technology Stack

- Front-end and Middleware: NextJS
- Back-end: Spring
- Database: MySQL

## Notable Back-end Features

- **Full Stack Development**: The application is developed with a complete technology stack, including front-end, middleware, and back-end.
- **Security**: JWT tokens have been implemented for security, along with custom application config beans for JWT, authentication, and authority-based endpoints.
- **Database Management**: JPA and MySQL have been used to create a complex relational database with various types of keys and structures. This also includes the use of custom SQL queries.
- **Validation checking**: The Jakarta validation library has been used to ensure the validity of objects entering JPA, complete with error handling.
- **Exception Handling**: Hierarchical exception classes have been created to be thrown from requests, along with a global request handler to manage all exceptions in an extendable and consistent manner.

Please note that as these are hosted for free, resources and performances are limited.
