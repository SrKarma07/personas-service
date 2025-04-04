package com.example.personas.cliente;

import com.example.personas.entity.Cliente;
import com.example.personas.repository.ClienteRepository;
import com.example.personas.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks y objeto con @InjectMocks
        MockitoAnnotations.openMocks(this);

        // Configuramos un Cliente de ejemplo
        cliente = new Cliente();
        // Perteneciente a la entidad Persona:
        cliente.setIdentificacion("12345");
        cliente.setNombre("Juan Pérez");
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono("555-1234");

        // Exclusivo de Cliente:
        cliente.setClienteId("CLI-001");
        cliente.setHistorialFinanciero("Buen historial");
        cliente.setFechaInicioHistorial(LocalDate.of(2020, 1, 1));
        cliente.setEstado("ACTIVO");
    }

    // 1. Creación exitosa de un Cliente
    @Test
    void testCreateCliente_Success() {
        // Simular que no existe un cliente con el mismo clienteId
        when(clienteRepository.existsByClienteId("CLI-001")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.createCliente(cliente);

        assertNotNull(result);
        assertEquals("CLI-001", result.getClienteId());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    // 2. Error al crear un Cliente que ya existe
    @Test
    void testCreateCliente_AlreadyExists() {
        // Simular que ya existe un cliente con ID "CLI-001"
        when(clienteRepository.existsByClienteId("CLI-001")).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> clienteService.createCliente(cliente));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Ya existe el cliente con ID: CLI-001"));
    }

    // 3. Obtener un Cliente existente
    @Test
    void testGetClienteById_Success() {
        // Simulamos que sí existe el cliente en la BD
        when(clienteRepository.findByClienteId("CLI-001")).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.getClienteById("CLI-001");

        assertNotNull(result);
        assertEquals("CLI-001", result.getClienteId());
        assertEquals("ACTIVO", result.getEstado());
        verify(clienteRepository, times(1)).findByClienteId("CLI-001");
    }

    // 4. Error al obtener un Cliente que no existe
    @Test
    void testGetClienteById_NotFound() {
        // Simular que no se encuentra el cliente
        when(clienteRepository.findByClienteId("CLI-999")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> clienteService.getClienteById("CLI-999"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("No existe el cliente con ID: CLI-999"));
    }

    // 5. Error al eliminar un Cliente que no existe
    @Test
    void testDeleteCliente_NotFound() {
        // Simular que no existe el cliente a eliminar
        when(clienteRepository.existsByClienteId("CLI-999")).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> clienteService.deleteCliente("CLI-999"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("No existe el cliente con ID: CLI-999"));
    }
}
