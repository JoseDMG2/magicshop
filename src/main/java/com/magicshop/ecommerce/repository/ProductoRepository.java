 package com.magicshop.ecommerce.repository;

import com.magicshop.ecommerce.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Método para buscar productos por categoría
    List<Producto> findByCategoriaId(int categoriaId);
}
