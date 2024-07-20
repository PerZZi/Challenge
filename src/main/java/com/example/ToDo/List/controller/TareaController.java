package com.example.ToDo.List.controller;

import com.example.ToDo.List.dtos.ClientDTO;
import com.example.ToDo.List.dtos.CrearTareaDTO;
import com.example.ToDo.List.dtos.TareaDTO;
import com.example.ToDo.List.models.enums.Estado;
import com.example.ToDo.List.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping("/cliente")
    public ResponseEntity<Object> obtenerCliente (Authentication authentication){
        ClientDTO clientDTO = tareaService.getClientDTO(authentication.getName());
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @GetMapping("/lista")
    public List<TareaDTO> listaDeTareas(){
        return tareaService.getAllTareasDTO();
    }

    @GetMapping("/filtrar/{estado}")
    public ResponseEntity<List<TareaDTO>> filtrarTareasPorEstado(@PathVariable Estado estado) {
        List<TareaDTO> tareasFiltradas = tareaService.filtrarTareasPorEstado(estado);
        return ResponseEntity.ok(tareasFiltradas);
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearTarea(@RequestBody CrearTareaDTO crearTareaDTO, Authentication authentication){
        return tareaService.crearTarea(crearTareaDTO,authentication.getName());
    }

    @PutMapping("/modificar/{idTarea}")
    public ResponseEntity<String> modificarTarea(@PathVariable Long idTarea, @RequestBody CrearTareaDTO tareaDTO) {
        return tareaService.modificarTarea(idTarea, tareaDTO);
    }

    @DeleteMapping("/eliminar/{idTarea}")
    public ResponseEntity<String> eliminarTarea(@PathVariable Long idTarea) {
        return tareaService.eliminarTarea(idTarea);
    }

    @PutMapping("/completar/{idTarea}")
    public ResponseEntity<String> marcarTareaComoCompletada(@PathVariable Long idTarea) {
        return tareaService.marcarTareaComoCompletada(idTarea);
    }

    @GetMapping("/detalle/{idTarea}")
    public TareaDTO obtenerTareaPorId(@PathVariable Long idTarea) {
        return tareaService.obtenerTareaPorId(idTarea);
    }

    @PutMapping("/eliminarFecha/{idTarea}")
    public ResponseEntity<String> eliminarFechaDeVencimiento(@PathVariable Long idTarea) {
        return tareaService.eliminarFechaDeVencimiento(idTarea);
    }
}
