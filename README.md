# feat-user-backend
## Description
This project is used to gathering the data from users and processing the image, and the foot components coordinate. In additional, this project contains a image processing process and foot component coordinate transformation to the number data process.

## Project structure

* [main/](./src/main)
    * [java/](./src/main/java)
        * [com/](./src/main/java/com)
            * [example/](./src/main/java/com/example)
                * [feat_user/](./src/main/java/com/example/feat_user)
                    * [controllers/](./src/main/java/com/example/feat_user/controllers)
                        * [UsersController.java](./src/main/java/com/example/feat_user/controllers/UsersController.java)
                    * [datasource/](./src/main/java/com/example/feat_user/datasource)
                        * [Datasource.java](./src/main/java/com/example/feat_user/datasource/Datasource.java)
                    * [models/](./src/main/java/com/example/feat_user/models)
                        * [reponse/](./src/main/java/com/example/feat_user/models/reponse)
                            * [GetEstimateResponse.java](./src/main/java/com/example/feat_user/models/reponse/GetEstimateResponse.java)
                        * [request/](./src/main/java/com/example/feat_user/models/request)
                            * [GetEstimateRequest.java](./src/main/java/com/example/feat_user/models/request/GetEstimateRequest.java)
                        * [ComponentPoints.java](./src/main/java/com/example/feat_user/models/ComponentPoints.java)
                        * [Feet.java](./src/main/java/com/example/feat_user/models/Feet.java)
                        * [Point.java](./src/main/java/com/example/feat_user/models/Point.java)
                        * [Users.java](./src/main/java/com/example/feat_user/models/Users.java)
                    * [repositories/](./src/main/java/com/example/feat_user/repositories)
                        * [UsersRepository.java](./src/main/java/com/example/feat_user/repositories/UsersRepository.java)
                    * [resources/](./src/main/java/com/example/feat_user/resources)
                    * [services/](./src/main/java/com/example/feat_user/services)
                        * [estimationService/](./src/main/java/com/example/feat_user/services/estimationService)
                            * [EstimateService.java](./src/main/java/com/example/feat_user/services/estimationService/EstimateService.java)
                        * [UserServiceImpl.java](./src/main/java/com/example/feat_user/services/UserServiceImpl.java)
                        * [UsersService.java](./src/main/java/com/example/feat_user/services/UsersService.java)
                    * [utils/](./src/main/java/com/example/feat_user/utils)
                        * [ComputeFootData.java](./src/main/java/com/example/feat_user/utils/ComputeFootData.java)
                        * [ComputeFootParameters.java](./src/main/java/com/example/feat_user/utils/ComputeFootParameters.java)
                        * [FileUtilities.java](./src/main/java/com/example/feat_user/utils/FileUtilities.java)
                        * [ImageType.java](./src/main/java/com/example/feat_user/utils/ImageType.java)
                        * [InputOutput.java](./src/main/java/com/example/feat_user/utils/InputOutput.java)
                    * [FeatUserApplication.java](./src/main/java/com/example/feat_user/FeatUserApplication.java)
    * [resources/](./src/main/resources)
        * [static/](./src/main/resources/static)
        * [templates/](./src/main/resources/templates)
        * [application.properties](./src/main/resources/application.properties)



### Controller
The controller directory, it contains controller file, which used to specify the endpoints and communicate to the outside, such as website. In additional, there is only one controller, which use to secify these endpoints.
- **GET /users** , it used to get data from the database.
- **POST /users** , it used to generate foot parameters data and save to database.
- **POST /users/update** , it used to update foot parameters for the existing users.

### Datasource
The datasource directory, it contains datasource file, which used to connect to the mongo db database.  
To connect to the database you have to create environment variables, if you use intellij you can follow [this tutorials](https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html#add-environment-variables).  
**Environment variables**

| Variable      | Value         |
| ------------- |:-------------:|
| DATABASE_URL      | mongodb://localhost:27017 |
| DATABASE      | users      |

### Models
The models directory, it contains response directory which contain GetEstimationResponse model and use to mapping data from the estimation service, 
request directory which contain GetEstimationRequest model and use to mapping data and send data to the estimation service, and Users model 
which contain Feet model and ComponentPoint model, that use Point model to specify the component point.

### Repositories
The repositories directory, it used to define query command, which is more advance than the basic query, such as search by id.

### Services
The services directory, it contains internal services and external sservice. For the internal service, it will be called by the controller. For the external services, it will call the external service endpoint.

- EstimationService : It used to call estimation service, which is the external microservice.
- UsersService: It used to perform a creating user, updating user, and searching users.

### Utils
The utils directory, it contains additional necessary java file, which used to perform actions in the internal services.

- [ComputeFootData.java](./src/main/java/com/example/feat_user/utils/ComputeFootData.java) : It used to transform data from the web application to the data that usable for estimation arch height and generate 3D feet.
- [ComputeFootParameters.java](./src/main/java/com/example/feat_user/utils/ComputeFootParameters.java) : It used to compute foot's parameters value by using foot image and component points. In this file, it contains the image processing.
- [FileUtilities.java](./src/main/java/com/example/feat_user/utils/FileUtilities.java) : It used to perform any action that is related to file such as read file, create file from base64, and convert base64 to stream.
- [ImageType.java](./src/main/java/com/example/feat_user/utils/ImageType.java) : It used to identify file type, which is jpg or png.
- [InputOutput.java](./src/main/java/com/example/feat_user/utils/InputOutput.java) : It used to write the image output.

## How to start this project
- Option 1 : Run it by using intellij
- Option 2 : Run it by using docker-compose

### Option 1
1. Prepared mongo db by following this [instruction](https://hub.docker.com/_/mongo).
2. Import this project as maven project into intellij.
3. Set up environment variable. You can do it by following [Datasource](#Datasource).
4. Run it

### Option 2
1. Run this command.
```
docker-compose up -d
```
