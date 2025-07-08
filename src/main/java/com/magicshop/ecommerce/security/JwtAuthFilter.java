package com.magicshop.ecommerce.security;

import com.magicshop.ecommerce.service.UsuarioService;
import com.magicshop.ecommerce.model.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public JwtAuthFilter(JwtUtil jwtUtil, UsuarioService usuarioService) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Elimina "Bearer "

            try {
                String correo = jwtUtil.getUsernameFromToken(token);
                String rol = jwtUtil.getRoleFromToken(token);

                Usuario usuario = usuarioService.listar().stream()
                        .filter(u -> u.getCorreo().equals(correo))
                        .findFirst()
                        .orElse(null);

                if (usuario != null && jwtUtil.validateToken(token, correo)) {

                    // 🔐 Convertir el rol a una autoridad de Spring con prefijo "ROLE_"
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    usuario,
                                    null,
                                    Collections.singletonList(authority)
                            );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception e) {
                System.out.println("Error al validar token JWT: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
