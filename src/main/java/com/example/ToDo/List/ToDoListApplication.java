package com.example.ToDo.List;

import com.example.ToDo.List.models.Client;
import com.example.ToDo.List.models.Tarea;
import com.example.ToDo.List.models.enums.Estado;
import com.example.ToDo.List.repositories.ClientRepository;
import com.example.ToDo.List.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class ToDoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	/*@Bean
	public CommandLineRunner initData(TareaRepository tareaRepository, ClientRepository clientRepository) {
		return args -> {
			// Creación de datos de prueba para Tarea
			Tarea tarea1 = new Tarea("Hacer compras", null);
			Tarea tarea2 = new Tarea("Preparar presentación", new Date());
			Tarea tarea3 = new Tarea("Revisar informe", new Date());

			// Guardar en la base de datos
			tareaRepository.save(tarea1);
			tareaRepository.save(tarea2);
			tareaRepository.save(tarea3);

			Client client = new Client("Juan","juan@gmail.com", passwordEncoder.encode("juan123"));

			clientRepository.save(client);
			System.out.println("Datos de prueba de Tareas creados en la base de datos.");
		};
	}*/
}

