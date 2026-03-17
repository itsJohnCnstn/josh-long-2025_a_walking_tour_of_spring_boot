package com.johncnstn.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    PasswordEncoder myPasswordEncoder() {
        // "{bcrypt}asdjho1h2"
        // "{sha}adskak128"
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    InMemoryUserDetailsManager myInMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        var users = Set.of(User
                .withUsername("johncnstn")
                .password(passwordEncoder.encode("pw"))
                .roles("USER")
                .build());
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    SecurityFilterChain mySpringSecurityFilterChain(HttpSecurity http) {
        return http
                .oauth2AuthorizationServer(as -> as.oidc(Customizer.withDefaults()))
                .authorizeHttpRequests(ae -> ae.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
//                for passkeys (fingerprint/faceid in Apple ecosystem) biometrics in general
//                .webAuthn(Customizer.withDefaults())
//                email or sms token verification on login
//                .oneTimeTokenLogin(c -> c.tokenGenerationSuccessHandler())
                .build();
    }

}
