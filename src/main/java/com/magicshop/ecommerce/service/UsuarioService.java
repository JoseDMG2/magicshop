package com.magicshop.ecommerce.service;

import com.magicshop.ecommerce.model.Usuario;
import java.util.List;

public interface UsuarioService {

    List<Usuario> listar();
    Usuario registrar(Usuario usuario);
    Usuario actualizar(Usuario usuario);
    void eliminar(Integer id);
    Usuario ListarPorId(Integer id);
    
}
