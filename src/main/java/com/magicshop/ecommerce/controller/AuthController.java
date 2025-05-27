
package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.Usuario;
import com.magicshop.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/login")
    public String login(@RequestParam String correo, @RequestParam String clave, HttpSession session, Model model) {
        Usuario usuario = usuarioService.listar().stream()
                .filter(u -> u.getCorreo().equals(correo) && u.getClave().equals(clave))
                .findFirst().orElse(null);
        if (usuario != null) {
            session.setAttribute("usuario", usuario);
            return "redirect:/";
        }
        model.addAttribute("error", "Correo o contraseña incorrectos.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
}
