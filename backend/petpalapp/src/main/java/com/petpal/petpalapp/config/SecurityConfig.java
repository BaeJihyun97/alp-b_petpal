package com.petpal.petpalapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (테스트용)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // 특정 경로 허용
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                );
        return http.build();
    }
}