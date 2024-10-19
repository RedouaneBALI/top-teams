package io.github.redouanebali.topteams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"io.github.redouanebali.topteams"}) // ajustez selon votre package
public class TopTeamsApplication {

  public static void main(String[] args) {
    SpringApplication.run(TopTeamsApplication.class, args);
  }
}
