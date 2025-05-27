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
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String agregarAlCarrito(@RequestParam("id") Integer productoId, @RequestParam("cantidad") Integer cantidad, HttpSession session, RedirectAttributes redirectAttributes) {
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if (carrito == null) carrito = new ArrayList<>();

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("mensaje", "¡Debes iniciar sesión para procesar la compra!");
            return "redirect:/login";
        }

        boolean existe = carrito.stream().anyMatch(d -> d.getProducto().getId() == productoId);
        if (!existe) {
            Producto producto = productoService.ListarPorId(productoId);
            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecio_unitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio() * cantidad);
            carrito.add(detalle);
        }
        session.setAttribute("carrito", carrito);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String verCarrito(HttpSession session, Model model) {
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if (carrito == null) carrito = new ArrayList<>();
        double total = carrito.stream().mapToDouble(DetallePedido::getSubtotal).sum();
        model.addAttribute("detalles", carrito);
        model.addAttribute("total", total);
        return "carrito";
    }


    @PostMapping("/ProcesarCompra")
    public String procesarCompra(HttpSession session, RedirectAttributes redirectAttributes) {
        
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensaje", "¡No hay productos en el carrito para procesar la compra!");
            return "redirect:/carrito";
        }
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("mensaje", "¡Debes iniciar sesión para procesar la compra!");
            return "redirect:/login";
        }
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEstado("comprado");
        pedido.setFecha(java.time.LocalDate.now().toString());
        pedidoService.registrar(pedido);
        for (DetallePedido detalle : carrito) {
            detalle.setPedido(pedido);
            detallePedidoService.registrar(detalle);
        }

        // Actualizar el total
        double total = carrito.stream().mapToDouble(DetallePedido::getSubtotal).sum();
        pedido.setTotal(total);
        pedidoService.actualizar(pedido);

        // Limpiar el carrito de la sesión  
        session.removeAttribute("carrito");
        redirectAttributes.addFlashAttribute("mensaje", "¡Compra realizada con éxito!");
        return "redirect:/carrito";
        
    }
}
