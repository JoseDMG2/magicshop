package com.magicshop.ecommerce.repository;

import com.magicshop.ecommerce.model.DetallePedido;
import com.magicshop.ecommerce.model.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer>{
    List<DetallePedido> findByPedido(Pedido pedido);
}
