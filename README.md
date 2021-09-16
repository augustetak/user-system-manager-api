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

Once those parameters are setting run this command :
  
docker-compose -f docker-compose.mongo.yml up -d
  
to build and run the api image and mongo image.

TEST API:
  
get all users:
  
http://localhost:8080/api/v1/users
  
or
  
curl -X GET \
  http://localhost:8080/api/v1/users
