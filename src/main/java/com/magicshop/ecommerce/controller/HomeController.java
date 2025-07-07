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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/auth/")
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

    public static class CarritoRequest {
        private int productoId;
        private int cantidad;

        public int getProductoId() {
            return productoId;
        }
        public void setProductoId(int productoId) {
            this.productoId = productoId;
        }
        public int getCantidad() {
            return cantidad;
        }
        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }

    @PostMapping("/carrito")
    public ResponseEntity<?> agregarAlCarrito(@RequestBody CarritoRequest carritoRequest, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if(usuario == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "¡Debes iniciar sesión para procesar la compra!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        if (carrito == null) carrito = new ArrayList<>();
        boolean existe = carrito.stream().anyMatch(d -> d.getProducto().getId() == carritoRequest.productoId);
        if (!existe) {
            Producto producto = productoService.ListarPorId(carritoRequest.productoId);
            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(carritoRequest.cantidad);
            detalle.setPrecio_unitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio() * carritoRequest.cantidad);
            carrito.add(detalle);
        }
        session.setAttribute("carrito", carrito);

        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Producto agregado al carrito");
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/carrito")
    public List<DetallePedido> obtenerCarrito(HttpSession session) {
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if (carrito == null) carrito = new ArrayList<>();
        return carrito;
    }

    @PutMapping("/carrito/{productoId}")
    public ResponseEntity<?> actualizarCarrito(@PathVariable int productoId, @RequestBody CarritoRequest carritoRequest, HttpSession session) {
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if (carrito == null) carrito = new ArrayList<>();
        
        for (DetallePedido detalle : carrito) {
            if (detalle.getProducto().getId() == productoId) {
                detalle.setCantidad(carritoRequest.cantidad);
                detalle.setSubtotal(detalle.getPrecio_unitario() * carritoRequest.cantidad);
                session.setAttribute("carrito", carrito);
                Map<String, Object> mensaje = new HashMap<>();
                mensaje.put("mensaje", "Carrito actualizado");
                return ResponseEntity.ok(mensaje);
            }
        }
        
        Map<String, Object> error = new HashMap<>();
        error.put("mensaje", "Producto no encontrado en el carrito");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    

    @DeleteMapping("/carrito/{productoId}")
    public ResponseEntity<?> eliminarDelCarrito(@PathVariable int productoId, HttpSession session) {
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if (carrito == null) carrito = new ArrayList<>();
        boolean removed = carrito.removeIf(detalle -> detalle.getProducto().getId() == productoId);
        session.setAttribute("carrito", carrito);

        Map<String, Object> mensaje = new HashMap<>();
        if (removed) {
            mensaje.put("mensaje", "Producto eliminado del carrito");
            return ResponseEntity.ok(mensaje);
        } else {
            mensaje.put("mensaje", "Producto no encontrado en el carrito");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
        }
    }
    
    @PostMapping("/procesar-compra")
    public ResponseEntity<?> procesarCompra(HttpSession session) {
        List<DetallePedido> carrito = (List<DetallePedido>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "¡No hay productos en el carrito!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("mensaje", "¡Debes iniciar sesión para procesar la compra!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
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
        Map<String, Object> mensaje = new HashMap<>();
        mensaje.put("mensaje", "¡Compra realizada con éxito!");
        return ResponseEntity.ok(mensaje);
    }
}
