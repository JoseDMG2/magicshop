package com.magicshop.ecommerce.service.impl;

import com.magicshop.ecommerce.model.Pedido;
import com.magicshop.ecommerce.repository.PedidoRepository;
import com.magicshop.ecommerce.service.PedidoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServicesImpl implements PedidoService {

    @Autowired
    PedidoRepository pedrep;
    
    @Override
    public List<Pedido> listar() {
        return pedrep.findAll();
    }

    @Override
    public Pedido registrar(Pedido pedido) {
        return pedrep.save(pedido);
    }

    @Override
    public Pedido actualizar(Pedido pedido) {
        return pedrep.save(pedido);
    }

    @Override
    public void eliminar(Integer id) {
        pedrep.deleteById(id);
    }

    @Override
    public Pedido ListarPorId(Integer id) {
        return pedrep.findById(id).orElse(null);
    }
    
}
