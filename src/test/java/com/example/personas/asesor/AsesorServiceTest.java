package com.example.personas.asesor;

import com.example.personas.entity.Asesor;
import com.example.personas.repository.AsesorRepository;
import com.example.personas.service.impl.AsesorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsesorServiceTest {

    @Mock
    private AsesorRepository asesorRepository;

    @InjectMocks
    private AsesorServiceImpl asesorService;

    private Asesor asesor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Configuramos un asesor de ejemplo
        asesor = new Asesor();
        asesor.setAsesorId("ASE-001");
        asesor.setIdentificacion("1234567890");
        asesor.setNombre("Juan Pérez");
        asesor.setGenero("Masculino");
        asesor.setEdad(35);
        asesor.setDireccion("Calle Falsa 123");
        asesor.setTelefono("555-1234");
        asesor.setEmail("juan.perez@example.com");
        asesor.setEstado("ACTIVO");
    }

    @Test
    void testCreateAsesor_Success() {
        // Simula que no existe ya un asesor con el mismo ID
        when(asesorRepository.existsByAsesorId("ASE-001")).thenReturn(false);
        when(asesorRepository.save(any(Asesor.class))).thenReturn(asesor);

        Asesor result = asesorService.createAsesor(asesor);

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getNombre());
        verify(asesorRepository, times(1)).save(any(Asesor.class));
    }

    @Test
    void testCreateAsesor_AlreadyExists() {
        // Simula que ya existe un asesor con el mismo asesorId
        when(asesorRepository.existsByAsesorId("ASE-001")).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> asesorService.createAsesor(asesor));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Ya existe un asesor con el ID: ASE-001"));
    }

    @Test
    void testGetAsesorById_Success() {
        // Simula la existencia del asesor en el repositorio
        when(asesorRepository.findByAsesorId("ASE-001")).thenReturn(Optional.of(asesor));

        Asesor result = asesorService.getAsesorById("ASE-001");

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getNombre());
        verify(asesorRepository, times(1)).findByAsesorId("ASE-001");
    }

    @Test
    void testUpdateAsesor_Success() {
        // Simula que el asesor existe
        when(asesorRepository.findByAsesorId("ASE-001")).thenReturn(Optional.of(asesor));
        when(asesorRepository.save(any(Asesor.class))).thenAnswer(i -> i.getArguments()[0]);

        // Nuevo asesor con datos actualizados (excepto el asesorId que se mantiene)
        Asesor updatedAsesor = new Asesor();
        updatedAsesor.setIdentificacion("1234567890");
        updatedAsesor.setNombre("Juan Actualizado");
        updatedAsesor.setGenero("Masculino");
        updatedAsesor.setEdad(36);
        updatedAsesor.setDireccion("Avenida Siempre Viva");
        updatedAsesor.setTelefono("555-5678");
        updatedAsesor.setEmail("juan.actualizado@example.com");
        updatedAsesor.setEstado("ACTIVO");

        Asesor result = asesorService.updateAsesor("ASE-001", updatedAsesor);

        assertNotNull(result);
        assertEquals("Juan Actualizado", result.getNombre());
        assertEquals("Avenida Siempre Viva", result.getDireccion());
        verify(asesorRepository, times(1)).save(any(Asesor.class));
    }

    @Test
    void testDeleteAsesor_Success() {
        // Simula que el asesor existe
        when(asesorRepository.existsByAsesorId("ASE-001")).thenReturn(true);

        // No se simula nada en delete, pero verificamos que se llame al método deleteByAsesorId
        asesorService.deleteAsesor("ASE-001");

        verify(asesorRepository, times(1)).deleteByAsesorId("ASE-001");
    }

    @Test
    void testGetAsesorById_NotFound() {
        // Simula que el asesor no existe
        when(asesorRepository.findByAsesorId("ASE-002")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> asesorService.getAsesorById("ASE-002"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("No existe el asesor con ID: ASE-002"));
    }
}
