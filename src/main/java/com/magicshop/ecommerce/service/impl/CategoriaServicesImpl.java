
package com.magicshop.ecommerce.service.impl;

import com.magicshop.ecommerce.model.Categoria;
import com.magicshop.ecommerce.repository.CategoriaRepository;
import com.magicshop.ecommerce.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServicesImpl implements CategoriaService{
    
    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }
    
}
