package com.magicshop.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="detallepedidos")
public class DetallePedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="cantidad", nullable = false)
    private int cantidad;
    
    @Column(name="precio_unitario", nullable = false)
    private double precio_unitario;
    
    @Column(name="subtotal", nullable = false)
    private double subtotal;
    
    @ManyToOne
    @JoinColumn(name="id_producto")
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name="id_pedido")
    private Pedido pedido;
    
    //Constructores

    public DetallePedido(int id, int cantidad, double precio_unitario, double subtotal, Producto producto, Pedido pedido) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
        this.producto = producto;
        this.pedido = pedido;
    }
    
    
    public DetallePedido() {
    }
    
    //Gets and Setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
}
