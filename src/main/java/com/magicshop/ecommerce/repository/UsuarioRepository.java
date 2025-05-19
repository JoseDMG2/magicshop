package com.magicshop.ecommerce.repository;

import com.magicshop.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
}
