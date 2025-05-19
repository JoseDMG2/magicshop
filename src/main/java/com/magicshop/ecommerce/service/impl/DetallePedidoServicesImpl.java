package com.magicshop.ecommerce.service.impl;

import com.magicshop.ecommerce.model.DetallePedido;
import com.magicshop.ecommerce.repository.DetallePedidoRepository;
import com.magicshop.ecommerce.service.DetallePedidoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoServicesImpl implements DetallePedidoService {

    @Autowired
    DetallePedidoRepository detrep;
    
    @Override
    public List<DetallePedido> listar() {
        return detrep.findAll();
    }

    @Override
    public DetallePedido registrar(DetallePedido detallepedido) {
        return detrep.save(detallepedido);
    }

    @Override
    public DetallePedido actualizar(DetallePedido detallepedido) {
        return detrep.save(detallepedido);
    }

    @Override
    public void eliminar(Integer id) {
        detrep.deleteById(id);
    }

    @Override
    public DetallePedido ListarPorId(Integer id) {
        return detrep.findById(id).orElse(null);
    }
    
}
