package com.magicshop.ecommerce;

import com.magicshop.ecommerce.model.Producto;
import com.magicshop.ecommerce.repository.ProductoRepository;
import com.magicshop.ecommerce.service.impl.ProductoServicesImpl;
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

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;
    
    @InjectMocks
    private ProductoServicesImpl productoServicesImpl;
    
    private Producto producto;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        
        producto = new Producto();
        producto.setNombre("ElMachoAlfa");
        producto.setPrecio(40.0);
        producto.setStock(20);
        //producto.setCategoria("Perfumes");
        producto.setDescripcion("Perfume para la más princesa");
        producto.setId(9);
    }
        
    @Test
    public void listarTodos() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));
        assertNotNull(productoServicesImpl.listar(), "El método no funciona correctamente");
        System.out.println("Productos enlistados correctamente");
    }
    
    @Test
    public void listarPorId() {
        int id_existente = 9;
        when(productoRepository.findById(producto.getId())).thenReturn(java.util.Optional.of(producto));
        assertNotNull(productoServicesImpl.ListarPorId(id_existente), "El producto con ID '"+id_existente+"' no existe o el método no funciona correctamente");
        System.out.println("El producto con ID '"+id_existente+"' ha sido encontrado");
    }
    
    @Test
    public void registrar() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        assertNotNull(productoServicesImpl.registrar(producto), "El método no funciona correctamente");
        System.out.println("El producto ha sido registrado correctamente");
    }
    
    @Test
    public void actualizar() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        assertNotNull(productoServicesImpl.actualizar(producto), "El método no funciona correctamente");
        System.out.println("El producto ha sido actualizado correctamente");
    }
    
    @Test
    public void eliminar() {
        int id_existente = 9;
        productoServicesImpl.eliminar(producto.getId());
        verify(productoRepository).deleteById(id_existente);
        System.out.println("El producto ha sido eliminado correctamente");
    }
}
