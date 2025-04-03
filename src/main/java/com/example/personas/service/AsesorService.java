package com.example.personas.service;

import com.example.personas.entity.Asesor;

import java.util.List;

public interface AsesorService {

    Asesor createAsesor(Asesor asesor);

    // Recuperar un asesor por su asesorId (no la PK real, sino la única de negocio)
    Asesor getAsesorById(String asesorId);

    List<Asesor> getAllAsesores();

    // Para actualizar, pasamos el asesorId y el objeto con los nuevos datos
    Asesor updateAsesor(String asesorId, Asesor asesor);

    void deleteAsesor(String asesorId);
}
