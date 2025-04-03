package com.example.personas.service;

import com.example.personas.entity.Persona;

import java.util.List;

// Define las operaciones que quieres exponer
public interface PersonaService {

    Persona createPersona(Persona persona);

    Persona getPersonaById(String identificacion);

    List<Persona> getAllPersonas();

    Persona updatePersona(Persona persona);

    void deletePersona(String identificacion);

}