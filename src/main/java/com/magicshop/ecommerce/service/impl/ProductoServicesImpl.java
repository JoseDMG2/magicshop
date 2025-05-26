package com.magicshop.ecommerce.service.impl;

import com.magicshop.ecommerce.model.Producto;
import com.magicshop.ecommerce.repository.ProductoRepository;
import com.magicshop.ecommerce.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServicesImpl implements ProductoService {

    @Autowired
    ProductoRepository prorep;
    
    @Override
    public List<Producto> listar() {
        return prorep.findAll();
    }

    @Override
    public Producto registrar(Producto producto) {
        return prorep.save(producto);
    }

    @Override
    public Producto actualizar(Producto producto) {
        return prorep.save(producto);
    }

    @Override
    public void eliminar(Integer id) {
        prorep.deleteById(id);
    }

    @Override
    public Producto ListarPorId(Integer id) {
        return prorep.findById(id).orElse(null);
    }
    
    @Override
    public List<Producto> ListarPorCategoriaId(int categoriaId) {
        return prorep.findByCategoriaId(categoriaId);
    }
    
    
}
