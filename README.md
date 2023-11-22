# Spring-Boot - MVC - Domain Driven Design - CQRS - Event Driven Architecture

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


# Sobre o Projeto


O projeto original é feito em .NET Core com C# com Postgres.  

Esse projeto é uma migração do projeto do curso para Java utilizando Spring Boot MVC com Thymeleaf e MongoDB como base.  

O curso encontra-se em: https://desenvolvedor.io/curso/modelagem-de-dominios-ricos  

Me deem uma estrelinha, isso irá me ajudar muito. No canto direito superior tem em inglês um botão "star".  

Quem deseja contribuir, dar sugestões, fazer correções, eu agradecerei imensamente, pode abrir issues, pull-requests a vontade.  

Não está 100% migrado ainda, mas já funciona a parte administrativa, a vitrine e parte do carrinho.  

Já publica e captura eventos, notificações, e comandos.  
Já está separado em módulos: vendas, core, catalogo, web.  
Irei migrar para Java Modules 9.  

Preferi usar o MongoDB, mas aceito pull-requests, ou mesmo pedidos, caso alguém se interesse, pode ser que eu adapte o código para uso de Hibernate/JPA com Postgres.  

Utilizei uma ferramenta muito simples como Mediator, um pub/sub bem simples, irei migrar para Axon Framework.  

Ainda falta terminar o Error Handling do Spring para as notificações de DomainExceptions para apresentar ao usuário.  
Porém, erro de formulário, e quando há o tratamento na controller, já aparece erro formatado ao usuário.  

Falta a parte de pagamento e corrigir alguns detalhes que preciso caçar que sempre sobram.  

Não utilizei nenhum framework de mapeamento, são lentos demais, e muito rígidos com muitas regras, achei muito melhor fazer uma classe simples que mapeia cada caso.  

Sofri bastante com o Thymeleaf, pois sou especialista em Angular, então quem quiser contribuir com melhorias e entende de Thymeleaf irá me ajudar demais.  
