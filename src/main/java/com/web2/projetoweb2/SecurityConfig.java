package com.web2.projetoweb2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Desabilita CSRF para facilitar o desenvolvimento
            .authorizeRequests()
                .requestMatchers("/api/usuarios", "/api/usuarios/login", "/api/usuarios/{id}").permitAll() // Permite acesso sem autenticação a esses endpoints
                .anyRequest().authenticated() // Requer autenticação para todos os outros endpoints
            .and()
            .httpBasic().disable() // Desabilita autenticação básica HTTP
            .formLogin().disable(); // Desabilita o formulário de login do Spring Security
        return http.build();
    }
}