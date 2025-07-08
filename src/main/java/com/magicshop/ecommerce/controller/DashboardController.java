package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.Producto;
import com.magicshop.ecommerce.model.Usuario;
import com.magicshop.ecommerce.model.Categoria;
import com.magicshop.ecommerce.service.CategoriaService;
import com.magicshop.ecommerce.service.ProductoService;
import com.magicshop.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/")
public class DashboardController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    // === PRODUCTOS ===

    @GetMapping("/productos")
    public List<Producto> listarProductos() {
        return productoService.listar();
    }

    @GetMapping("/productos/{id}")
    public Producto obtenerProducto(@PathVariable Integer id) {
        return productoService.ListarPorId(id);
    }

    @PostMapping("/productos")
    public Producto registrarProducto(@RequestBody Producto producto) {
        return productoService.registrar(producto);
    }

    @PutMapping("/productos/{id}")
    public Producto actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        producto.setId(id);
        return productoService.registrar(producto);
    }

    @DeleteMapping("/productos/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
        productoService.eliminar(id);
    }

    @GetMapping("/categorias")
    public List<Categoria> listarCategorias() {
        return categoriaService.listar();
    }

    // === USUARIOS ===

    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        return usuarioService.listar();
    }

    @GetMapping("/usuarios/{id}")
    public Usuario obtenerUsuario(@PathVariable Integer id) {
        return usuarioService.ListarPorId(id);
    }

    @PostMapping("/usuarios")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.registrar(usuario);
    }

    @PutMapping("/usuarios/{id}")
    public Usuario actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return usuarioService.registrar(usuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminar(id);
    }
}
