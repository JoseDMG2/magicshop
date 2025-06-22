
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        if (usuarioService.listar().stream().anyMatch(u -> u.getCorreo().equals(usuario.getCorreo()))) {
            model.addAttribute("error", "El correo ya está registrado.");
            return "register";
        }
        usuario.setRol("CLIENTE");
        usuarioService.registrar(usuario);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    public static class LoginRequest {
        public String correo;
        public String clave;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Usuario usuario = usuarioService.listar().stream()
                .filter(u -> u.getCorreo().equals(loginRequest.correo) && u.getClave().equals(loginRequest.clave))
                .findFirst().orElse(null);

        if (usuario != null) {
            session.setAttribute("usuario", usuario); // Guarda al usuario en la sesión
            return ResponseEntity.ok(usuario); // Retorna el usuario como JSON
        }

        // Error de autenticación
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
