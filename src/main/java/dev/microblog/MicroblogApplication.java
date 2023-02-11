package dev.microblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:database.properties")
public class MicroblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroblogApplication.class, args);
	}

}
