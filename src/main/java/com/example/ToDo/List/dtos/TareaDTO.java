package com.example.ToDo.List.dtos;

import com.example.ToDo.List.models.Tarea;
import com.example.ToDo.List.models.enums.Estado;

import java.util.Date;

public class TareaDTO {

    private Long id;
    private String nombre;
    private Date fechaDeVencimiento;
    private Estado estado;

    public TareaDTO() {
    }

    public TareaDTO(Tarea tarea) {
        id = tarea.getId();
        nombre = tarea.getNombre();
        fechaDeVencimiento = tarea.getFechaDeVencimiento();
        estado = tarea.getEstado();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }

    public Estado getEstado() {
        return estado;
    }
}
