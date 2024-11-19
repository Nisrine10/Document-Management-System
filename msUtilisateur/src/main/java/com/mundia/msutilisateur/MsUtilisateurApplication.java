package com.mundia.msutilisateur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication

public class MsUtilisateurApplication {
    @Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }
    public static void main(String[] args) {
        SpringApplication.run(MsUtilisateurApplication.class, args);
    }

}
