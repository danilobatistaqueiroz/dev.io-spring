# Criar Usuário e BD Mongodb 

Entre no shell no diretório mongo e digite:  
`docker-compose up`  
Pode ser necessário criar a pasta mongo/volume  

Entre em outro shell e digite:  

`mongosh --username root`  
Digite 123456

Entre no BD NerdStoreDb:  
`use NerdStoreDb`  

Crie o usuário nerd:  
`db.createUser( { user: "nerd", pwd: passwordPrompt(), roles: [ { role: "readWrite", db: "NerdStoreDb" }] } )`  
Digite nerd para senha  

# Criar a collection e popular as categorias:  

Entre no mongo-express digitando: `localhost:8081` no navegador

Entre com usuário e senha: `nerd` e `nerd`

Escolha: Create Database e digite NerdStoreDb

Escolha: Create Collection e digite categorias

Escolha: New Document e preencha com:  
`{ "_id": ObjectID(), "codigo":1, "nome":"Camisetas"}`  
repita com:  
`{ "_id": ObjectID(), "codigo":2, "nome":"Canecas"}`  

# Compilar os projetos e subir o Spring Web MVC

Entre no diretório principal e digite:  
`mvn clean install`

Entre no diretório nerdstore-webapp e digite:  
`./mvnw spring-boot:run`

Abra o navegador em `localhost:8080/vitrine/index`  

