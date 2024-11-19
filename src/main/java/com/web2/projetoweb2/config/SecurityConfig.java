package com.web2.projetoweb2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar o desenvolvimento
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/usuarios", "/api/usuarios/login", "/api/usuarios/{id}").permitAll() // Permite acesso a esses endpoints sem autenticação
                .requestMatchers(HttpMethod.POST, "/api/orcamentos").hasRole("FUNCIONARIO") // Apenas funcionários podem criar orçamentos
                .anyRequest().authenticated() // Requer autenticação para todos os outros endpoints
            )
            .httpBasic(httpBasic -> httpBasic.disable()) // Desabilita autenticação básica HTTP
            .formLogin(formLogin -> formLogin.disable()); // Desabilita o formulário de login do Spring Security

        return http.build();
    }
}