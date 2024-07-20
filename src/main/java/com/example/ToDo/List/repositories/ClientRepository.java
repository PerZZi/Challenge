package com.example.ToDo.List.repositories;

import com.example.ToDo.List.models.Client;
import com.example.ToDo.List.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String inputName);
}
