package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.Producto;
import com.magicshop.ecommerce.model.Usuario;
import com.magicshop.ecommerce.service.CategoriaService;
import com.magicshop.ecommerce.service.ProductoService;
import com.magicshop.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/dashboard")
public class DashboardController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    // LISTAR SEGÚN LA VISTA
    @GetMapping
    public String mostrarDashboard(
            @RequestParam(value = "vista", required = false, defaultValue = "inicio") String vista,
            Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        if (vista.equals("productos")) {
            model.addAttribute("productos", productoService.listar());
            model.addAttribute("nuevoProducto", new Producto());
        } else if (vista.equals("usuarios")) {
            model.addAttribute("usuarios", usuarioService.listar());
            model.addAttribute("nuevoUsuario", new Usuario());
        }

        model.addAttribute("vista", vista);
        return "admin/dashboard";
    }

    // === CRUD PRODUCTOS ===

    // REGISTRAR PRODUCTO
    @PostMapping("/productos/registrar")
    public String registrarProducto(@ModelAttribute Producto producto) {
        productoService.registrar(producto);
        return "redirect:/admin/dashboard?vista=productos";
    }

    // EDITAR PRODUCTO
    @GetMapping("/productos/editar/{id}")
    public String editarProducto(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("productoEditar", productoService.ListarPorId(id));
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("vista", "productos");
        return "admin/dashboard";
    }

    // ACTUALIZAR PRODUCTO
    @PostMapping("/productos/actualizar")
    public String actualizarProducto(@ModelAttribute Producto producto) {
        productoService.registrar(producto); // puede ser registrar o actualizar
        return "redirect:/admin/dashboard?vista=productos";
    }

    // ELIMINAR PRODUCTO
    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Integer id) {
        productoService.eliminar(id);
        return "redirect:/admin/dashboard?vista=productos";
    }

    // === CRUD usuarios ===

    // REGISTRAR USUARIO
    @PostMapping("/usuarios/registrar")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.registrar(usuario);
        return "redirect:/admin/dashboard?vista=usuarios";
    }

    // EDITAR USUARIO
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("usuarioEditar", usuarioService.ListarPorId(id));
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("vista", "usuarios");
        return "admin/dashboard";
    }

    // ACTUALIZAR USUARIO
    @PostMapping("/usuarios/actualizar")
    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.registrar(usuario);
        return "redirect:/admin/dashboard?vista=usuarios";
    }

    // ELIMINAR USUARIO
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id) {
        usuarioService.eliminar(id);
        return "redirect:/admin/dashboard?vista=usuarios";
    }
}
