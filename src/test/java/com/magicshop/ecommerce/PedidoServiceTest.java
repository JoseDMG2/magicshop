package com.magicshop.ecommerce;

import com.magicshop.ecommerce.model.Pedido;
import com.magicshop.ecommerce.model.Usuario;
import com.magicshop.ecommerce.repository.PedidoRepository;
import com.magicshop.ecommerce.service.impl.PedidoServicesImpl;
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

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;
    
    @InjectMocks
    private PedidoServicesImpl pedidoServicesImpl;
    
    private Pedido pedido;
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        
        pedido = new Pedido();
        usuario = new Usuario();
        pedido.setFecha("2024-03-01 12:00");
        pedido.setTotal(500.0);
        pedido.setEstado("cancelado");
        pedido.setUsuario(usuario);
        pedido.setId(9);
    }
        
    @Test
    public void listarTodos() {
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido));
        assertNotNull(pedidoServicesImpl.listar(), "El método no funciona correctamente");
        System.out.println("Pedidos enlistados correctamente");
    }
    
    @Test
    public void listarPorId() {
        int id_existente = 9;
        when(pedidoRepository.findById(pedido.getId())).thenReturn(java.util.Optional.of(pedido));
        assertNotNull(pedidoServicesImpl.ListarPorId(id_existente), "El pedido con ID '"+id_existente+"' no existe o el método no funciona correctamente");
        System.out.println("El pedido con ID '"+id_existente+"' ha sido encontrado");
    }
    
    @Test
    public void registrar() {
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        assertNotNull(pedidoServicesImpl.registrar(pedido), "El método no funciona correctamente");
        System.out.println("El pedido ha sido registrado correctamente");
    }
    
    @Test
    public void actualizar() {
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        assertNotNull(pedidoServicesImpl.actualizar(pedido), "El método no funciona correctamente");
        System.out.println("El pedido ha sido actualizado correctamente");
    }
    
    @Test
    public void eliminar() {
        int id_existente = 9;
        pedidoServicesImpl.eliminar(pedido.getId());
        verify(pedidoRepository).deleteById(id_existente);
        System.out.println("El pedido ha sido eliminado correctamente");
    }
}
