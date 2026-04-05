package com.bank.app.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/users/register/**").permitAll()
                // Teller + Commercial can open accounts
                .requestMatchers("/api/accounts/**").hasAnyRole(
                    "TELLER_EMPLOYEE", "COMMERCIAL_EMPLOYEE", "NATURAL_PERSON_CLIENT",
                    "COMPANY_CLIENT", "INTERNAL_ANALYST")
                // Only Internal Analyst approves/rejects/disburses loans
                .requestMatchers("/api/loans/*/approve", "/api/loans/*/reject", "/api/loans/*/disburse")
                    .hasRole("INTERNAL_ANALYST")
                // Clients and Commercial can request loans
                .requestMatchers("/api/loans").hasAnyRole(
                    "NATURAL_PERSON_CLIENT", "COMPANY_CLIENT", "COMMERCIAL_EMPLOYEE", "INTERNAL_ANALYST")
                // Only Company Supervisor can approve/reject transfers
                .requestMatchers("/api/transfers/*/approve", "/api/transfers/*/reject")
                    .hasRole("COMPANY_SUPERVISOR")
                // Company Employee can create transfers
                .requestMatchers("/api/transfers").hasAnyRole(
                    "COMPANY_EMPLOYEE", "COMPANY_SUPERVISOR", "NATURAL_PERSON_CLIENT", "INTERNAL_ANALYST")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
