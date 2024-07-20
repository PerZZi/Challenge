package com.example.ToDo.List.models;

import com.example.ToDo.List.models.enums.Estado;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Date fechaDeVencimiento;
    private Estado estado = Estado.ACTIVA;
    @ManyToOne
    private Client client;

    public Tarea() {
    }

    public Tarea(String nombre, Date fechaDeVencimiento) {
        this.nombre = nombre;
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }

    public void setFechaDeVencimiento(Date fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaDeVencimiento=" + fechaDeVencimiento +
                ", estado=" + estado +
                '}';
    }
}
