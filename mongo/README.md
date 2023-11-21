# MONGO DB

#### Subir o servidor Mongodb usando Docker
entre na pasta mongo:  
`docker-compose up`  

#### Acessar o shell do Mongodb
`mongosh "mongodb://localhost:27017" --username root`  
- or -  
`mongosh --username root`  
- or -  
`mongosh --host mongodb0.example.com --port 27017`  

#### Criação do BD e do Usuário
`use NerdStoreDb`  

`db.createUser( { user: "nerd", pwd: passwordPrompt(), roles: [ { role: "readWrite", db: "NerdStoreDb" }] } )`

`show users;`  

`show dbs;`  


# Compilar e subir o projeto SpringBoot MVC

entre na pasta principal:  
`mvn clean install`  

para rodar o projeto web, entre na pasta nerdstore.web:  
`./mvnw spring-boot:run`  


# Editar dados usando Mongo-express
localhost:8081

entrar com usuario nerd e senha nerd

