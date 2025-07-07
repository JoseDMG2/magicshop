
package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.Usuario;
import com.magicshop.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        
        if (usuarioService.listar().stream().anyMatch(u -> u.getCorreo().equals(usuario.getCorreo()))) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El correo ya está registrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        usuario.setRol("CLIENTE");
        usuarioService.registrar(usuario);
        session.setAttribute("usuario", usuario);
        return ResponseEntity.ok(usuario);
    }

    public static class LoginRequest {
        public String correo;
        public String clave;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Usuario usuario = usuarioService.listar().stream()
                .filter(u -> u.getCorreo().equals(loginRequest.correo))
                .findFirst().orElse(null);

        if (usuario != null && passwordEncoder.matches(loginRequest.clave, usuario.getClave())) {
            session.setAttribute("usuario", usuario); 
            return ResponseEntity.ok(usuario); 
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", "Correo o contraseña incorrectos.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario/actual")
    public ResponseEntity<?> usuarioActual(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay usuario autenticado.");
        }
    }


}
