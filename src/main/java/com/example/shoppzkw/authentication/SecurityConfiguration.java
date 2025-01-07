package com.example.shoppzkw.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/adm/**").hasRole("ADMIN")
                        .requestMatchers("/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(); // Default login form

        // You can customize logout, CSRF, etc. if needed.
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new MyUserDetailsService();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // In a production application, choose a secure password encoder like BCrypt
        return NoOpPasswordEncoder.getInstance();
    }
}
