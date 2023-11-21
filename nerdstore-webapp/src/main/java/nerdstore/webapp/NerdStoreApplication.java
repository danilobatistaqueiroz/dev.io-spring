package nerdstore.webapp;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import nerdstore.webapp.mvc.setup.DependencyInjection;


@ComponentScan({
  "nerdstore.vendas.application.commands",
  "nerdstore.vendas.application.queries",
  "nerdstore.vendas.data.repository",
  "nerdstore.vendas.application.handlers",
  "nerdstore.core.communication",
  "nerdstore.core.messages.commonmessages.notifications",
  "nerdstore.catalogo.application.automapper",
  "nerdstore.catalogo.application.services",
  "nerdstore.catalogo.data.repository",
  "nerdstore.catalogo.domain",
  "nerdstore.webapp.mvc.controllers",
  "nerdstore.webapp.mvc.setup",
  "nerdstore.webapp"
})
@EnableMongoRepositories({"nerdstore.vendas.data.repository","nerdstore.catalogo.data.repository"})
@SpringBootApplication
public class NerdStoreApplication {

  @Autowired
  DependencyInjection di;

	public static void main(String[] args) {
		SpringApplication.run(NerdStoreApplication.class, args);
	}

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    di.registerServices();
    return args -> {

      System.out.println("Let's inspect the beans provided by Spring Boot:");

      String[] beanNames = ctx.getBeanDefinitionNames();
      Arrays.sort(beanNames);
      for (String beanName : beanNames) {
        System.out.println(beanName);
      }
      
    };
  }

}
