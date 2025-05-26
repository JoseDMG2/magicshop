package com.magicshop.ecommerce.service;

import com.magicshop.ecommerce.model.Producto;
import java.util.List;

public interface ProductoService {
    
    List<Producto> listar();
    Producto registrar(Producto producto);
    Producto actualizar(Producto producto);
    void eliminar(Integer id);
    Producto ListarPorId(Integer id);
    List<Producto> ListarPorCategoriaId(int categoriaId);

    
}
