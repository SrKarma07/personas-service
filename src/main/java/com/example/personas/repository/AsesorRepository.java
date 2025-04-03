package com.example.personas.repository;

import com.example.personas.entity.Asesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AsesorRepository extends JpaRepository<Asesor, String> {

    // Buscar un asesor usando el asesorId como identificador de negocio
    Optional<Asesor> findByAsesorId(String asesorId);

    // Verificar si existe un asesor con determinado asesorId
    boolean existsByAsesorId(String asesorId);

    // Eliminar por asesorId (requiere @Modifying y @Transactional)
    @Transactional
    @Modifying
    void deleteByAsesorId(String asesorId);
}
