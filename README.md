# Spring-Boot-Microservices-and-Spring-Cloud  
### Following Microservices created  
- API-GATEWAY (Using spring cloud gateway)
- DISCOVERY-SERVICE (Using Eureka)
- ACCOUNT-MANAGEMENT-SERVICE
- PHOTO-ALBUM-SERVICE
- USER-SERVICE
### Tech stack used
- JAVA 11
- Spring Boot 2.6.4
- Spring, Spring Security
- Spring Cloud
- MySQL, JPA
#### _Create User_:
  Method: POST  
  URI: http://localhost:8082/user-service/users  
  Payload:   
    {  
      "firstName":"Your First Name",  
      "lastName":"Your Last Name",  
      "password":"Your Password",  
      "email":"Your Email"  
    }
    
#### _Login_:
  Method: POST  
  URI: http://localhost:8082/user-service/users/login  
  Payload:   
    {  
      "email":"Your Email",  
      "password":"Your Password"  
    }  
    Go to Header section in Postman and copy the JWT and UserId
    
#### _Get Photo Album_:
- Using Feign  
  Method: GET  
  URI: http://localhost:8082/user-service/users/feign/{userId}  
- Using Resttemplate  
  Method: GET  
  URI: http://localhost:8082/user-service/users/{userId}  
  Provide JWT in header
