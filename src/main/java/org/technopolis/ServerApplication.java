package org.technopolis;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@RestController
public class ServerApplication {

	@GetMapping("/")
	String home() {
		return "Spring is here!";
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}