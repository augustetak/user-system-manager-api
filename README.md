This api is wrote in java version 8 to manage user stored on Nosql Mongodb.

How to use this project

Clone
git clone https://github.com/augustetak/user-system-manager-api.git
cd user-system-manager-api
git checkout master

Configuration:
Modify the file .env providing value for:
Mongodb:

MONGO_CONTAINER_NAME=<<Container's name for mongodd>>

MONGO_ROOT_USERNAME=<<Your root username>>
MONGO_ROOT_PASSWORD=<<Your password >>

Once these parameters have been modified run this command :
  
docker-compose -f docker-compose.mongo.yml up -d
  
to build and run the api image and mongo image.

Swagger

To see all rest method interfaces and test them:

http://localhost:8080/swagger-ui/#/user-controller


