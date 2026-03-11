package com.johncnstn.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

@EnableConfigServer
@SpringBootApplication
class ConfigApplication {

    static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return _ -> {
            if (environment instanceof ConfigurableEnvironment configurableEnvironment) {
                configurableEnvironment.getPropertySources().stream().iterator().forEachRemaining(System.out::println);
            }
        };
    }

}
