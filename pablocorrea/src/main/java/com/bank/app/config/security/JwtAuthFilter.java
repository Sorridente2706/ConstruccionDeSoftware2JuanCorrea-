package com.bank.app.config.security;

import com.bank.app.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        try {
            final String username = jwtService.extractUsername(jwt);
            System.out.println(">>> USERNAME EXTRAIDO: " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                userRepository.findByUsername(username).ifPresent(user -> {
                    boolean valid = jwtService.isTokenValid(jwt, username);
                    boolean active = user.isActive();
                    System.out.println(">>> isTokenValid: " + valid + " | isActive: " + active + " | user: " + username);
                    if (valid && active) {
                        String role = "ROLE_" + user.getRole().name();
                        System.out.println(">>> ROL ASIGNADO: " + role + " para usuario: " + username);
                        var authToken = new UsernamePasswordAuthenticationToken(
                                username, null, List.of(new SimpleGrantedAuthority(role)));
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                });
            }
        } catch (Exception e) {
            System.out.println(">>> ERROR EN FILTRO: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}