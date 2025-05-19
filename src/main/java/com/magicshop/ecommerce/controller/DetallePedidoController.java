package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.DetallePedido;
import com.magicshop.ecommerce.service.DetallePedidoService;
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
@RequestMapping("/detallepedido")
public class DetallePedidoController {
    
    @Autowired
    private DetallePedidoService service;
    
    @GetMapping
    public ResponseEntity<List<DetallePedido>> listar() {
        List<DetallePedido> obj = service.listar();
        return new ResponseEntity<List<DetallePedido>>(obj, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody DetallePedido detallepedido){
        
        DetallePedido obj = service.registrar(detallepedido);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(obj.getId()).toUri();
        
        return ResponseEntity.created(uri).build();
        
    }
    
    @PutMapping
    public ResponseEntity<DetallePedido> actualizar(@RequestBody DetallePedido detallepedido){
        DetallePedido obj = service.registrar(detallepedido);
        return new ResponseEntity<DetallePedido>(obj, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
        DetallePedido obj = service.ListarPorId(id);
        if(obj == null) {
            throw new Exception("No se encontró el ID");
        }
        service.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> listarPorId(@PathVariable("id") Integer id) throws Exception{
        DetallePedido obj = service.ListarPorId(id);
        if(obj == null) {
            throw new Exception("No se encontró el ID");
        }
        service.eliminar(id);
        return new ResponseEntity<DetallePedido>(obj, HttpStatus.OK);
    }
    
}
