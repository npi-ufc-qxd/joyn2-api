package br.ufc.npi.joynrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class JoynRestApplication extends SpringBootServletInitializer{
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JoynRestApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(JoynRestApplication.class, args);
    }
}