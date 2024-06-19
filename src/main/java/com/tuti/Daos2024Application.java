package com.tuti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class Daos2024Application {

	public static void main(String[] args) {
		SpringApplication.run(Daos2024Application.class, args);
	}

	  @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("Demo API")
	                        .version("1.0")
	                        .description("Api demo utilizada en clases")
	                        .termsOfService("http://tuti.com/terminosycondiciones")
	                        .license(new License().name("LICENSE").url("https://tuti.com")));
	  }
	        
	        
}
