
package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.Usuario;
import com.magicshop.ecommerce.security.JwtUtil;
import com.magicshop.ecommerce.service.impl.UsuarioServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioServicesImpl usuarioServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    // Clase interna para el request de login
    public static class LoginRequest {
        public String correo;
        public String clave;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioServiceImpl.listar().stream()
                .filter(u -> u.getCorreo().equals(loginRequest.correo) && u.getClave().equals(loginRequest.clave))
                .findFirst()
                .orElse(null);

        if (usuario == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Correo o contraseña incorrectos.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        // Genera el token JWT
        String token;
        token = jwtUtil.generateToken(usuario.getCorreo());

        // Devuelve el usuario y el token
        Map<String, Object> response = new HashMap<>();
        response.put("usuario", usuario);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioServiceImpl.listar().stream().anyMatch(u -> u.getCorreo().equals(usuario.getCorreo()))) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El correo ya está registrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        usuario.setRol("CLIENTE");
        usuarioServiceImpl.registrar(usuario);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/verificar")
    public String verificarToken() {
        return "Acceso autorizado con JWT";
    }
}