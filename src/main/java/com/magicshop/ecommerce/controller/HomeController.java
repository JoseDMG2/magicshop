package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.Categoria;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
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

    @GetMapping("/categorias")
    @ResponseBody
    public List<Categoria> obtenerCategorias() {
        return categoriaService.listar();
    }

     @PostMapping("/carrito")
    public Map<String, Object> agregarAlCarrito(@RequestBody Map<String, Object> payload, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Integer productoId = (Integer) payload.get("productoId");
        Integer cantidad = (Integer) payload.get("cantidad");
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if(usuario == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("mensaje", "¡Debes iniciar sesión para procesar la compra!");
            return response;
        }
        if (carrito == null) carrito = new ArrayList<>();
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
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        return response;
    }

    @GetMapping("/carrito")
    public List<DetallePedido> obtenerCarrito(HttpSession session) {
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if (carrito == null) carrito = new ArrayList<>();
        return carrito;
    }
    
    @PostMapping("/procesar-compra")
    public Map<String, Object> procesarCompra(HttpSession session) {
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        Map<String, Object> response = new HashMap<>();
        if (carrito == null || carrito.isEmpty()) {
            response.put("status", "error");
            response.put("mensaje", "¡No hay productos en el carrito!");
            return response;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
         if (usuario == null) {
            response.put("status", "error");
            response.put("mensaje", "¡Debes iniciar sesión para procesar la compra!");
            return response;
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
        
        session.removeAttribute("carrito");
        response.put("status", "ok");
        response.put("mensaje", "¡Compra realizada con éxito!");
        return response;
    }
}
