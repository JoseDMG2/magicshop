package com.magicshop.ecommerce.service;

import com.magicshop.ecommerce.model.DetallePedido;
import com.magicshop.ecommerce.model.Pedido;
import java.util.List;

public interface DetallePedidoService {
    
    List<DetallePedido> listar();
    DetallePedido registrar(DetallePedido detallepedido);
    DetallePedido actualizar(DetallePedido deatllepedido);
    void eliminar(Integer id);
    DetallePedido ListarPorId(Integer id);
    List<DetallePedido> listarPorPedido(Pedido pedido);
    
}
