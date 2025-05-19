package com.magicshop.ecommerce.service.impl;

import com.magicshop.ecommerce.model.Usuario;
import com.magicshop.ecommerce.repository.UsuarioRepository;
import com.magicshop.ecommerce.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioServicesImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usurep;
    
    @Override
    public List<Usuario> listar() {
        return usurep.findAll();
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        return usurep.save(usuario);
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        return usurep.save(usuario);
    }

    @Override
    public void eliminar(Integer id) {
        usurep.deleteById(id);
    }

    @Override
    public Usuario ListarPorId(Integer id) {
        return usurep.findById(id).orElse(null);
    }
    
}
