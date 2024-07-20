package com.example.ToDo.List.dtos;

import com.example.ToDo.List.models.Tarea;
import com.example.ToDo.List.models.enums.Estado;

import java.util.Date;

public class CrearTareaDTO {

    private String nombre;
    private Date fechaDeVencimiento;
    private Estado estado;

    public CrearTareaDTO() {
    }

    public CrearTareaDTO(Tarea tarea) {
        nombre = tarea.getNombre();
        fechaDeVencimiento = tarea.getFechaDeVencimiento();
        estado = tarea.getEstado();
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
