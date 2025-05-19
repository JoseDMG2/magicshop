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
@Table(name="pedidos")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="fecha", nullable = false)
    private String fecha;
    
    @Column(name="total", nullable = false)
    private double total;
    
    @Column(name="estado", length = 100, nullable = false)
    private String estado;
    
    @ManyToOne
    @JoinColumn(name="id_detalleventa")
    private DetallePedido detalleventa;
    
    //Constructor
    public Pedido(String fecha, double total, String estado, DetallePedido detalleventa) {
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.detalleventa = detalleventa;
    }
    
    //Gets and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DetallePedido getDetalleventa() {
        return detalleventa;
    }

    public void setDetalleventa(DetallePedido detalleventa) {
        this.detalleventa = detalleventa;
    }

}
