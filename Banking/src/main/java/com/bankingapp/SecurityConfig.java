package com.bankingapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for development (enable in production)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() 
//                .requestMatchers("/**","/account/**").permitAll() // Public endpoints
//                .requestMatchers("/","/transaction/**").permitAll() 
                . requestMatchers("/h2-console/**").permitAll() // Allow H2 consolerequestMatchers("/h2-console/**").permitAll() // Allow H2 console
                .requestMatchers("/admin/**").hasRole("ADMIN")  // Admin access
                .requestMatchers("/dashboard").hasAnyRole("USER", "ADMIN") // Restricted access
                .anyRequest().authenticated() // All other requests require authentication
            )
            .formLogin(login -> login
                .loginPage("/login")  // Custom login page
                .defaultSuccessUrl("/dashboard", true) // Redirect after login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // Logout endpoint
                .logoutSuccessUrl("/login?logout=true")  // Redirect after logout
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .sessionManagement(session -> session
                .maximumSessions(1) // Allow only 1 active session per user
                .expiredUrl("/login?expired=true") // Redirect if session expires
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions().disable());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN")
            .build();

        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("user123"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
