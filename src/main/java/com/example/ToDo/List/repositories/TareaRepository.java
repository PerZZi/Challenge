package com.example.ToDo.List.repositories;

import com.example.ToDo.List.models.Tarea;
import com.example.ToDo.List.models.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    Tarea findByNombre(String nombre);
    List<Tarea> findAllByEstado(Estado estado);
}
