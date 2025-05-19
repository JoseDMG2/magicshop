package com.magicshop.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column(name="apellido", length = 100, nullable = false)
    private String apellido;
    
    @Column(name="correo", length = 100, nullable = false)
    private String correo;
    
    @Column(name="clave", length = 100, nullable = false)
    private String clave;
    
    @Column(name="telefono", length = 20, nullable = false)
    private String telefono;
    
    @Column(name="direccion", length = 100, nullable = false)
    private String direccion;
    
    @Column(name="rol", length = 100, nullable = false)
    private String rol;
    
    //Constructores
    public Usuario(String nombre, String apellido, String correo, String clave, String telefono, String direccion, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.clave = clave;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
    }

    public Usuario() {
    }
    
    //Gets and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
