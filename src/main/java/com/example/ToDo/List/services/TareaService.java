package com.example.ToDo.List.services;

import com.example.ToDo.List.dtos.ClientDTO;
import com.example.ToDo.List.dtos.CrearTareaDTO;
import com.example.ToDo.List.dtos.TareaDTO;
import com.example.ToDo.List.models.Client;
import com.example.ToDo.List.models.Tarea;
import com.example.ToDo.List.models.enums.Estado;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TareaService {

    List<Tarea> getAllTareas();
    List<TareaDTO> getAllTareasDTO();
    Client getClient (String email);
    ClientDTO getClientDTO (String email);
    ResponseEntity<String> crearTarea(CrearTareaDTO crearTareaDTO, String emial);
    ResponseEntity<String> modificarTarea(Long idTarea, CrearTareaDTO tareaDTO);
    ResponseEntity<String> eliminarTarea(Long idTarea);
    ResponseEntity<String> marcarTareaComoCompletada(Long idTarea);
    List<TareaDTO> filtrarTareasPorEstado(Estado estado);
    TareaDTO obtenerTareaPorId(Long idTarea);
    ResponseEntity<String> eliminarFechaDeVencimiento(Long idTarea);
}
