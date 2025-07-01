package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.dto.AuthRequest;
import com.magicshop.ecommerce.dto.AuthResponse;
import com.magicshop.ecommerce.model.Usuario;
import com.magicshop.ecommerce.security.JwtService;
import com.magicshop.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") // si usas Angular localmente
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder; //  Se inyecta aquí

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getCorreo(),
                    request.getClave()
                )
            );

            Usuario usuario = usuarioService.buscarPorCorreo(request.getCorreo());
            String token = jwtService.generateToken(usuario.getCorreo(), usuario.getRol());

            return new AuthResponse(token, usuario.getNombre(), usuario.getRol());

        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        usuario.setRol("CLIENTE");
        usuario.setClave(passwordEncoder.encode(usuario.getClave())); //  Se encripta la clave aquí
        return usuarioService.registrar(usuario);
    }
}
