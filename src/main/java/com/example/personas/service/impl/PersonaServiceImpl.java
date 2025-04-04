package com.example.personas.service.impl;

import com.example.personas.entity.Persona;
import com.example.personas.repository.PersonaRepository;
import com.example.personas.service.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Persona createPersona(Persona persona) {
        // Validar si ya existe la persona
        if (personaRepository.existsById(persona.getIdentificacion())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ya existe la persona con ID: " + persona.getIdentificacion()
            );
        }
        // Crear nueva persona
        return personaRepository.save(persona);
    }

    @Override
    public Persona getPersonaById(String identificacion) {
        return personaRepository.findById(identificacion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Persona no encontrada con ID: " + identificacion
                ));
    }

    @Override
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public Persona updatePersona(Persona persona) {
        // Verifica si existe la persona
        if (!personaRepository.existsById(persona.getIdentificacion())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No existe la persona con ID: " + persona.getIdentificacion()
            );
        }
        // Actualiza
        return personaRepository.save(persona);
    }

    @Override
    public void deletePersona(String identificacion) {
        if (!personaRepository.existsById(identificacion)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No existe la persona con ID: " + identificacion
            );
        }
        personaRepository.deleteById(identificacion);
    }
}
