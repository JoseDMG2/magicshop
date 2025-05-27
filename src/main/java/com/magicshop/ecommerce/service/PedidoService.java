package com.magicshop.ecommerce.service;

import com.magicshop.ecommerce.model.Pedido;
import com.magicshop.ecommerce.model.Usuario;
import java.util.List;


public interface PedidoService {
    
    List<Pedido> listar();
    Pedido registrar(Pedido pedido);
    Pedido actualizar(Pedido pedido);
    void eliminar(Integer id);
    Pedido ListarPorId(Integer id);
    Pedido ListarPorIdYPorEstado(Usuario usuario);
    
}
