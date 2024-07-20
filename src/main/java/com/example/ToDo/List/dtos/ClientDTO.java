package com.example.ToDo.List.dtos;

import com.example.ToDo.List.models.Client;
import com.example.ToDo.List.models.Tarea;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {

    private Long id;
    private String nombre, email;
    private String rol = "USER";
    private List<TareaDTO> tareas ;

    public ClientDTO(Client client) {
        id = client.getId();
        nombre = client.getNombre();
        email = client.getEmail();
        rol = client.getRol();
        this.tareas = client.getTareas().stream().map(TareaDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }

    public List<TareaDTO> getTareas() {
        return tareas;
    }
}
