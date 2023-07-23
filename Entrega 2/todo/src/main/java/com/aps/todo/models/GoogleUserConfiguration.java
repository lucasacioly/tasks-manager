/*package com.aps.todo.models;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class GoogleUserConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/users/googleSignUp").authenticated();
                    auth.requestMatchers("/users/googleSignIn").authenticated();
                    auth.anyRequest().permitAll();
                })
                .oauth2Login(Customizer.withDefaults())
                .build();
    }
}
*/