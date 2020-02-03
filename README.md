# wallet-ms
A simple wallet microservice running on the JVM that manages credit/debit transactions on behalf of players.
## Overview
Wallet micro service is assumed to be run behind a system like API Gateway which handles user requests at first and obtains user information from another authentication micro service and passes parameters like user ID , locale etc. to the wallet micro service.<br><br>
![](https://github.com/kderer/wallet-ms/blob/master/overview.png?raw=true)
 
Repository consists of two projects:
1. **wallet-ms-core**<br>
This is the core service library where database interactions and business logic are implemented.<br>
Having a separated library project will provide code re-usability and easier maintenance.
2. **wallet-ms-rest**<br>
A spring boot web application which basically handles REST API requests utilizing the core service library.

## Requirements
 - Java SE Development Kit 8
 - Apache Maven

## Build
 1. Open a command terminal.
 2. Go to repository root folder.
 3. Execute **`mvn package`** command.
 4. This will run unit/integration tests and generate **`wallet-ms-rest-1.0.0.jar`** file under **`wallet-ms-rest/target`** folder.

## Run
- **`wallet-ms-rest-1.0.0.jar`** can be copied to another folder for having a clean run folder.<br>
- **8888** port mustn't be used by another process.<br>
1. In the command terminal, go to the  folder where  **`wallet-ms-rest-1.0.0.jar`** resides.<br>
If the jar file is copied to another folder, go to that folder.
2. Exceute **`java -jar wallet-ms-rest-1.0.0.jar`** command.
3. This will create **data** and **log** folders in the same folder.
### Database
The application uses H2 integrated database in file mode.<br>
So, there is no need of installing or connecting to 3rd party database systems.<br>
Since H2 is used in file mode, data will be persisted across restarts.<br>
## Test and Documentation
After the application successfully started, go to [http://localhost:8888/swagger-ui.html#/Wallet_Management_APIs](http://localhost:8888/swagger-ui.html#/Wallet_Management_APIs) on a browser.<br>
Detailed information about API parameters and response types can be seen here and APIs can be tested as well.<br><br>
![](https://github.com/kderer/wallet-ms/blob/master/swagger.png?raw=true)
### Internationalization
**`sr_locale`** header parameter can be set for testing i18n.<br>
Currently, only English(**en**) and Sweedish(**sv**) are supported.
