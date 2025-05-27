package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.DetallePedido;
import com.magicshop.ecommerce.model.Pedido;
import com.magicshop.ecommerce.model.Producto;
import com.magicshop.ecommerce.model.Usuario;
import com.magicshop.ecommerce.service.CategoriaService;
import com.magicshop.ecommerce.service.DetallePedidoService;
import com.magicshop.ecommerce.service.PedidoService;
import com.magicshop.ecommerce.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class HomeController {
    
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private DetallePedidoService detallePedidoService;
    @Autowired
    private PedidoService pedidoService;
    
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("categorias", categoriaService.listar());        
        return "index";
    }
    
    @PostMapping("/AgregarCarrito")
    public String agregarAlCarrito(@RequestParam("id") Integer productoId,@RequestParam("cantidad") Integer cantidad,HttpSession session) {

        // Verificar si el usuario está autenticado 
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";

        // Buscar pedido existente o crea uno nuevo
        Pedido pedido = pedidoService.ListarPorIdYPorEstado(usuario);

        //Obtener producto
        Producto producto = productoService.ListarPorId(productoId);

        //Crear DetallePedido
        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecio_unitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio() * cantidad);

        detallePedidoService.registrar(detalle);
        

        return "redirect:/shop";
    }
    
}
