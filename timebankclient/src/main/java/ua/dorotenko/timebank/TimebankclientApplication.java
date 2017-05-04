package ua.dorotenko.timebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("ua.dorotenko.timebank")
public class TimebankclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimebankclientApplication.class, args);
	}
}
