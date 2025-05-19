package com.magicshop.ecommerce.repository;

import com.magicshop.ecommerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    
}
