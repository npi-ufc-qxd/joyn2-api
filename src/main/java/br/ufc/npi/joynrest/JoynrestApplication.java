package br.ufc.npi.joynrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class JoynrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoynrestApplication.class, args);
	}
}
