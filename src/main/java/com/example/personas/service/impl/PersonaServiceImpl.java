package com.example.personas.service.impl;

import com.example.personas.entity.Persona;
import com.example.personas.repository.PersonaRepository;
import com.example.personas.service.PersonaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;

    // Inyección de dependencias vía constructor
    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Persona createPersona(Persona persona) {
        // Aquí puedes meter validaciones, por ejemplo si ya existe la identificación
        return personaRepository.save(persona);
    }

    @Override
    public Persona getPersonaById(String identificacion) {
        Optional<Persona> optionalPersona = personaRepository.findById(identificacion);
        // Si no existe, lanza excepción o maneja el caso
        if (optionalPersona.isEmpty()) {
            throw new RuntimeException("Persona no encontrada con ID: " + identificacion);
        }
        return optionalPersona.get();
    }

    @Override
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public Persona updatePersona(Persona persona) {
        // Revisa si existe la persona
        if (!personaRepository.existsById(persona.getIdentificacion())) {
            throw new RuntimeException("No existe la persona con ID: " + persona.getIdentificacion());
        }
        // En caso afirmativo, la actualizamos
        return personaRepository.save(persona);
    }

    @Override
    public void deletePersona(String identificacion) {
        if (!personaRepository.existsById(identificacion)) {
            throw new RuntimeException("No existe la persona con ID: " + identificacion);
        }
        personaRepository.deleteById(identificacion);
    }
}
