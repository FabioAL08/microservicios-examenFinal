package com.example.ms_tareas.filter;

import com.example.ms_tareas.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String path = request.getServletPath();

        log.info("=== JWT Filter ===");
        log.info("Path: {}", path);
        log.info("Method: {}", request.getMethod());

        // Rutas públicas
        if (path.contains("/swagger-ui") || path.contains("/v3/api-docs") || path.contains("/api-docs")) {
            log.info("Ruta pública - permitiendo acceso");
            filterChain.doFilter(request, response);
            return;
        }

        // Verificar token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Token no proporcionado o formato incorrecto");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token no proporcionado\"}");
            return;
        }

        String token = authHeader.substring(7);
        log.info("Token recibido: {}...", token.substring(0, Math.min(50, token.length())));

        try {
            // Validar token
            if (!jwtService.validateToken(token)) {
                log.error("Token inválido o expirado");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token inválido o expirado\"}");
                return;
            }

            String username = jwtService.extractUsername(token);
            List<String> roles = jwtService.extractRoles(token);

            log.info("Username: {}", username);
            log.info("Roles: {}", roles);

            if (username != null) {
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("✅ Usuario autenticado: {}", username);
            }

        } catch (Exception e) {
            log.error("Error procesando token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}