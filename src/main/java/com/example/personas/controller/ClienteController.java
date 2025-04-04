package com.example.personas.controller;

import com.example.personas.entity.Cliente;
import com.example.personas.service.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    // Inyección de dependencias
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Crear Cliente
    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.createCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Obtener un Cliente por su clienteId
    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> getById(@PathVariable String clienteId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        return ResponseEntity.ok(cliente);
    }

    // Listar todos los Clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        List<Cliente> lista = clienteService.getAllClientes();
        return ResponseEntity.ok(lista);
    }

    // Actualizar un Cliente
    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> update(@PathVariable String clienteId,
                                          @RequestBody Cliente cliente) {
        Cliente actualizado = clienteService.updateCliente(clienteId, cliente);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un Cliente
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> delete(@PathVariable String clienteId) {
        clienteService.deleteCliente(clienteId);
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
