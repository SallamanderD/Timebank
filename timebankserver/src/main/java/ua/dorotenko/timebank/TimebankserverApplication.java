package ua.dorotenko.timebank;

import com.mongodb.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("ua.dorotenko.timebank")
public class TimebankserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimebankserverApplication.class, args);
	}
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(new MongoClient("127.0.0.1"),"TimeBankBase");
	}
}
