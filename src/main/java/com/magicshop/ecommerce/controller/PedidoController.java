package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.Pedido;
import com.magicshop.ecommerce.service.PedidoService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoService service;
    
    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> obj = service.listar();
        return new ResponseEntity<List<Pedido>>(obj, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody Pedido pedido){
        
        Pedido obj = service.registrar(pedido);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        
        return ResponseEntity.created(uri).build();
    }
    
    @PutMapping
    public ResponseEntity<Pedido> actualizar(@RequestBody Pedido pedido){
        Pedido obj = service.registrar(pedido);
        return new ResponseEntity<Pedido>(obj, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
        Pedido obj = service.ListarPorId(id);
        if(obj == null) {
            throw new Exception("No se encontró el ID");
        }
        service.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> listarPorId(@PathVariable("id") Integer id) throws Exception{
        Pedido obj = service.ListarPorId(id);
        if(obj == null) {
            throw new Exception("No se encontró el ID");
        }
        service.eliminar(id);
        return new ResponseEntity<Pedido>(obj, HttpStatus.OK);
    }
    
}
