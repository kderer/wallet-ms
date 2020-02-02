# wallet-ms
A simple wallet microservice running on the JVM that manages credit/debit transactions on behalf of players.
# Overview
Wallet micro service is assumed to be run behind a system like API Gateway which handles user requests at first and obtains user information from another authentication micro service and passes parameters like user ID , locale etc. to the wallet micro service.
(https://github.com/kderer/wallet-ms/blob/master/overview.png?raw=true)
 
Repository is consist of two projects.
1. **wallet-ms-core**
This is the core service library where database interactions and business logic are implemented.
Having a separated library project will provide code re-usability and easier maintenance.
2. **wallet-ms-rest**
A spring boot web application which basically handles REST API request utilizing the core service library.


# Requirements
 - Java SE Development Kit 8
 - Apache Maven

# Build
 1. Open a command terminal.
 2. Go to repository root folder.
 3. Execute **`mvn package`** command.
 4. This will run the unit/integration tests, and generate **`wallet-ms-rest-1.0.0.jar`** file under **`wallet-ms-rest/target`** folder.

# Run

**`wallet-ms-rest-1.0.0.jar`** can be copied to another folder for having a clean run folder.
**8888** port mustn't be used by another process.
In the command terminal, go to the  folder where  **`wallet-ms-rest-1.0.0.jar`** resides.
Exceute **`java -jar wallet-ms-rest-1.0.0.jar`** command.
This will create **data** and **log** folder in the same folder.
## Database
The application uses H2 integrated database in file mode.
So, there is no need of installing or connecting to 3rd party database systems.
Since H2 is used in file mode data will be persisted across restarts.
# Test and Documentation
After the application successfully started go to [http://localhost:8888/swagger-ui.html#/Wallet_Operation_APIs](http://localhost:8888/swagger-ui.html#/Wallet_Operation_APIs) on a browser.
Detailed information about APIs can be seen here and APIs can be tested.
