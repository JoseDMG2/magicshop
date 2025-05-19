package com.magicshop.ecommerce.controller;

import com.magicshop.ecommerce.model.Producto;
import com.magicshop.ecommerce.service.ProductoService;
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

@RestController
@RequestMapping("/producto")
public class ProductoController {
    
    @Autowired
    private ProductoService service;
    
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> obj = service.listar();
        return new ResponseEntity<List<Producto>>(obj, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Producto> registrar(@RequestBody Producto producto){
        Producto obj = service.registrar(producto);
        return new ResponseEntity<Producto>(obj, HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<Producto> actualizar(@RequestBody Producto producto){
        Producto obj = service.registrar(producto);
        return new ResponseEntity<Producto>(obj, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
        Producto obj = service.ListarPorId(id);
        if(obj == null) {
            throw new Exception("No se encontró el ID");
        }
        service.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> listarPorId(@PathVariable("id") Integer id) throws Exception{
        Producto obj = service.ListarPorId(id);
        if(obj == null) {
            throw new Exception("No se encontró el ID");
        }
        service.eliminar(id);
        return new ResponseEntity<Producto>(obj, HttpStatus.OK);
    }
    
}
