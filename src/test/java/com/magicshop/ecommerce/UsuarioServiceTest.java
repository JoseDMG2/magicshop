package com.magicshop.ecommerce;

import com.magicshop.ecommerce.model.Usuario;
import com.magicshop.ecommerce.repository.UsuarioRepository;
import com.magicshop.ecommerce.service.impl.UsuarioServicesImpl;
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

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    
    @InjectMocks
    private UsuarioServicesImpl usuarioServicesImpl;
    
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        
        usuario = new Usuario();
        usuario.setNombre("Erick");
        usuario.setApellido("Zevallos");
        usuario.setCorreo("erickzevallos@correo.com");
        usuario.setClave("clave321");
        usuario.setTelefono("123456789");
        usuario.setDireccion("av las palmeras 123");
        usuario.setRol("cliente");
        usuario.setId(9);
    }
        
    @Test
    public void listarTodos() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));
        assertNotNull(usuarioServicesImpl.listar(), "El método no funciona correctamente");
        System.out.println("Usuarios enlistados correctamente");
    }
    
    @Test
    public void listarPorId() {
        int id_existente = 9;
        when(usuarioRepository.findById(usuario.getId())).thenReturn(java.util.Optional.of(usuario));
        assertNotNull(usuarioServicesImpl.ListarPorId(id_existente), "El usuario con ID '"+id_existente+"' no existe o el método no funciona correctamente");
        System.out.println("El usuario con ID '"+id_existente+"' ha sido encontrado");
    }
    
    @Test
    public void registrar() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        assertNotNull(usuarioServicesImpl.registrar(usuario), "El método no funciona correctamente");
        System.out.println("El usuario ha sido registrado correctamente");
    }
    
    @Test
    public void actualizar() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        assertNotNull(usuarioServicesImpl.actualizar(usuario), "El método no funciona correctamente");
        System.out.println("El usuario ha sido actualizado correctamente");
    }
    
    @Test
    public void eliminar() {
        int id_existente = 9;
        usuarioServicesImpl.eliminar(usuario.getId());
        verify(usuarioRepository).deleteById(id_existente);
        System.out.println("El usuario ha sido eliminado correctamente");
    }
}
