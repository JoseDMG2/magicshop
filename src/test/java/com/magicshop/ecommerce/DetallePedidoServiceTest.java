package com.magicshop.ecommerce;

import com.magicshop.ecommerce.model.DetallePedido;
import com.magicshop.ecommerce.model.Pedido;
import com.magicshop.ecommerce.model.Producto;
import com.magicshop.ecommerce.repository.DetallePedidoRepository;
import com.magicshop.ecommerce.service.impl.DetallePedidoServicesImpl;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class DetallePedidoServiceTest {

    @Mock
    private DetallePedidoRepository detallepedidoRepository;
    
    @InjectMocks
    private DetallePedidoServicesImpl detallepedidoServicesImpl;
    
    private DetallePedido detallepedido;
    private Pedido pedido;
    private Producto producto;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        
        detallepedido = new DetallePedido();
        pedido = new Pedido();
        producto = new Producto();
        detallepedido.setCantidad(3);
        detallepedido.setPrecio_unitario(12.0);
        detallepedido.setSubtotal(36.0);
        detallepedido.setPedido(pedido);
        detallepedido.setProducto(producto);
        detallepedido.setId(9);
    }
        
    @Test
    public void listarTodos() {
        when(detallepedidoRepository.findAll()).thenReturn(Arrays.asList(detallepedido));
        assertNotNull(detallepedidoServicesImpl.listar(), "El método no funciona correctamente");
        System.out.println("DetallePedidos enlistados correctamente");
    }
    
    @Test
    public void listarPorId() {
        int id_existente = 9;
        when(detallepedidoRepository.findById(detallepedido.getId())).thenReturn(java.util.Optional.of(detallepedido));
        assertNotNull(detallepedidoServicesImpl.ListarPorId(id_existente), "El detallepedido con ID '"+id_existente+"' no existe o el método no funciona correctamente");
        System.out.println("El detallepedido con ID '"+id_existente+"' ha sido encontrado");
    }
    
    @Test
    public void registrar() {
        when(detallepedidoRepository.save(any(DetallePedido.class))).thenReturn(detallepedido);
        assertNotNull(detallepedidoServicesImpl.registrar(detallepedido), "El método no funciona correctamente");
        System.out.println("El detallepedido ha sido registrado correctamente");
    }
    
    @Test
    public void actualizar() {
        when(detallepedidoRepository.save(any(DetallePedido.class))).thenReturn(detallepedido);
        assertNotNull(detallepedidoServicesImpl.actualizar(detallepedido), "El método no funciona correctamente");
        System.out.println("El detallepedido ha sido actualizado correctamente");
    }
    
    @Test
    public void eliminar() {
        int id_existente = 9;
        detallepedidoServicesImpl.eliminar(detallepedido.getId());
        verify(detallepedidoRepository).deleteById(id_existente);
        System.out.println("El detallepedido ha sido eliminado correctamente");
    }
}
