package com.example.personas.controller;

import com.example.personas.entity.Asesor;
import com.example.personas.service.AsesorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asesores")
public class AsesorController {

    private final AsesorService asesorService;

    public AsesorController(AsesorService asesorService) {
        this.asesorService = asesorService;
    }

    // Crear Asesor (POST)
    @PostMapping
    public ResponseEntity<Asesor> create(@RequestBody Asesor asesor) {
        Asesor nuevoAsesor = asesorService.createAsesor(asesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAsesor);
    }

    // Obtener un asesor por asesorId (GET)
    @GetMapping("/{asesorId}")
    public ResponseEntity<Asesor> getById(@PathVariable String asesorId) {
        Asesor asesor = asesorService.getAsesorById(asesorId);
        return ResponseEntity.ok(asesor);
    }

    // Listar todos los asesores (GET)
    @GetMapping
    public ResponseEntity<List<Asesor>> getAll() {
        List<Asesor> lista = asesorService.getAllAsesores();
        return ResponseEntity.ok(lista);
    }

    // Actualizar un asesor (PUT)
    @PutMapping("/{asesorId}")
    public ResponseEntity<Asesor> update(@PathVariable String asesorId,
                                         @RequestBody Asesor asesor) {
        Asesor actualizado = asesorService.updateAsesor(asesorId, asesor);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un asesor (DELETE)
    @DeleteMapping("/{asesorId}")
    public ResponseEntity<Void> delete(@PathVariable String asesorId) {
        asesorService.deleteAsesor(asesorId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(
            ResponseStatusException ex,
            HttpServletRequest request
    ) {
        // Estructura de la respuesta
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatusCode().value());
        body.put("error", ex.getStatusCode().toString());
        body.put("message", ex.getReason());
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}
