package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.Categoria;
import com.magicshop.ecommerce.model.Producto;
import com.magicshop.ecommerce.service.CategoriaService;
import com.magicshop.ecommerce.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShopController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;
    
    @GetMapping("/shop")
    public String mostrarShop(@RequestParam(required = false) Integer categoriaId, Model model) {
        List<Categoria> categorias = categoriaService.listar();
        List<Producto> productos = (categoriaId != null)
                ? productoService.ListarPorCategoriaId(categoriaId)
                : productoService.listar();
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoriaID", categoriaId);
        return "shop";
    }
}
