# Phonebook

### **Pre-requisites**
You need installed/to install: 
1. Java 1.8
2. Maven 3+

### **Local environment setup**
1. Clone the project from (https://github.com/ztaowns/phone-book) and import it in your favorite IDE.
2. Execute "mvn install" in the project root

### **Running the application**
In order to start the app run the "application" module using the Maven spring-boot plugin. Navigate in directory "phone-book/application" and run command ```mvn spring-boot:run```
The application will be listening on port:8080


### **Architecture**
Layered architecture was used to structure the code.
1. The domain" module represents the persistence layer.
2. The "business" module represents the business logic layer.
3. The "web-ui" module represents the presentation layer.

The integration tests are placed inside the "application" module and are run when building the application via maven.