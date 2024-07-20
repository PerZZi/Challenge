package com.example.ToDo.List.services.implement;

import com.example.ToDo.List.dtos.ClientDTO;
import com.example.ToDo.List.dtos.CrearTareaDTO;
import com.example.ToDo.List.dtos.TareaDTO;
import com.example.ToDo.List.models.Client;
import com.example.ToDo.List.models.Tarea;
import com.example.ToDo.List.models.enums.Estado;
import com.example.ToDo.List.repositories.ClientRepository;
import com.example.ToDo.List.repositories.TareaRepository;
import com.example.ToDo.List.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class TareaServiceImplement implements TareaService {

    @Autowired
    TareaRepository tareaRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    @Override
    public List<TareaDTO> getAllTareasDTO() {
        return getAllTareas().stream().map(TareaDTO::new).collect(Collectors.toList());
    }

    @Override
    public Client getClient(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public ClientDTO getClientDTO(String email) {
        return new ClientDTO(getClient(email));
    }

    @Override
    public ResponseEntity<String> crearTarea(CrearTareaDTO crearTareaDTO,String email) {

        Client client = clientRepository.findByEmail(email);

        if(client == null){
            return new ResponseEntity<>("El cliente no existe", HttpStatus.BAD_REQUEST);
        }

        if(crearTareaDTO.getNombre().isBlank() || crearTareaDTO.getNombre().length() < 3 || crearTareaDTO.getNombre().length() > 100){
            return new ResponseEntity<>("El nombre debe tener entre 3 y 100 caracteres.", HttpStatus.BAD_REQUEST);
        }
        if (crearTareaDTO.getFechaDeVencimiento() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(crearTareaDTO.getFechaDeVencimiento());
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                return new ResponseEntity<>("La fecha de vencimiento no puede ser un fin de semana.", HttpStatus.BAD_REQUEST);
            }

            Calendar today = Calendar.getInstance();
            today.add(Calendar.DAY_OF_MONTH, 30);
            if (crearTareaDTO.getFechaDeVencimiento().after(today.getTime())) {
                return new ResponseEntity<>("La fecha de vencimiento no puede ser más de 30 días a partir de la fecha actual.", HttpStatus.BAD_REQUEST);
            }
        }
        Tarea tarea = new Tarea(crearTareaDTO.getNombre(), crearTareaDTO.getFechaDeVencimiento());
        client.agregarTarea(tarea);
        tareaRepository.save(tarea);

        return new ResponseEntity<>("Tarea creada con éxito: " + tarea.getId(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> modificarTarea(Long idTarea, CrearTareaDTO tareaDTO) {

        Optional<Tarea> optionalTarea = tareaRepository.findById(idTarea);
        if (optionalTarea.isPresent()) {
            Tarea tarea = optionalTarea.get();

            // Validar y actualizar el nombre
            if (tareaDTO.getNombre() != null && !tareaDTO.getNombre().isBlank()) {
                if (tareaDTO.getNombre().length() < 3 || tareaDTO.getNombre().length() > 100) {
                    return new ResponseEntity<>("El nombre debe tener entre 3 y 100 caracteres.", HttpStatus.BAD_REQUEST);
                }
                if (!tareaDTO.getNombre().matches("^[a-zA-Z0-9$#%&()\\[\\]; ]+$")) {
                    return new ResponseEntity<>("El nombre solo puede contener letras, números y los símbolos $ # % & ( ) [ ] ;", HttpStatus.BAD_REQUEST);
                }
                tarea.setNombre(tareaDTO.getNombre());
            }

            // Actualizar la fecha de vencimiento si se proporciona
            if (tareaDTO.getFechaDeVencimiento() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(tareaDTO.getFechaDeVencimiento());
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                    return new ResponseEntity<>("La fecha de vencimiento no puede ser un fin de semana.", HttpStatus.BAD_REQUEST);
                }

                Calendar today = Calendar.getInstance();
                today.add(Calendar.DAY_OF_MONTH, 30);
                if (tareaDTO.getFechaDeVencimiento().after(today.getTime())) {
                    return new ResponseEntity<>("La fecha de vencimiento no puede ser más de 30 días a partir de la fecha actual.", HttpStatus.BAD_REQUEST);
                }
                tarea.setFechaDeVencimiento(tareaDTO.getFechaDeVencimiento());
            }

            // Actualizar el estado si se proporciona
            if (tareaDTO.getEstado() != null) {
                tarea.setEstado(tareaDTO.getEstado());
            }

            tareaRepository.save(tarea);
            return new ResponseEntity<>("Tarea modificada con éxito: " + tarea.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tarea no encontrada con ID: " + idTarea, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> eliminarTarea(Long idTarea) {

        Optional<Tarea> optionalTarea = tareaRepository.findById(idTarea);
        if (optionalTarea.isPresent()) {
            tareaRepository.deleteById(idTarea);
            return new ResponseEntity<>("Tarea eliminada con éxito: " + idTarea, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tarea no encontrada con ID: " + idTarea, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> marcarTareaComoCompletada(Long idTarea) {

        Optional<Tarea> optionalTarea = tareaRepository.findById(idTarea);
        if (optionalTarea.isPresent()) {
            Tarea tarea = optionalTarea.get();
            tarea.setEstado(Estado.COMPLETADA);
            tareaRepository.save(tarea);
            return new ResponseEntity<>("Tarea marcada como completada: " + idTarea, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tarea no encontrada con ID: " + idTarea, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<TareaDTO> filtrarTareasPorEstado(Estado estado) {

        List<Tarea> tareasActivas = tareaRepository.findAllByEstado(estado);

        return tareasActivas.stream()
                .map(TareaDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public TareaDTO obtenerTareaPorId(Long idTarea) {

        Optional<Tarea> optionalTarea = tareaRepository.findById(idTarea);
        return optionalTarea.map(TareaDTO::new).orElse(null);
    }

    @Override
    public ResponseEntity<String> eliminarFechaDeVencimiento(Long idTarea) {

        Optional<Tarea> optionalTarea = tareaRepository.findById(idTarea);
        if (optionalTarea.isPresent()) {
            Tarea tarea = optionalTarea.get();
            if (tarea.getEstado() == Estado.DIFERIDA && tarea.getFechaDeVencimiento() != null) {
                tarea.setFechaDeVencimiento(null);
                tareaRepository.save(tarea);
                return new ResponseEntity<>("Fecha de vencimiento eliminada para la tarea diferida: " + idTarea, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("La tarea no está en estado diferida o no tiene fecha de vencimiento.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Tarea no encontrada con ID: " + idTarea, HttpStatus.NOT_FOUND);
        }
    }
}
