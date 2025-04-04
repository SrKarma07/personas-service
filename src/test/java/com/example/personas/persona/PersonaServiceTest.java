package com.example.personas.persona;

import com.example.personas.entity.Persona;
import com.example.personas.repository.PersonaRepository;
import com.example.personas.service.impl.PersonaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonaServiceTest {

    @Mock
    private PersonaRepository personaRepository;

    @InjectMocks
    private PersonaServiceImpl personaService;

    private Persona persona;

    @BeforeEach
    void setup() {
        // Inicializa mocks de Mockito
        MockitoAnnotations.openMocks(this);

        // Instancia base de prueba
        persona = new Persona();
        persona.setIdentificacion("1234567890");
        persona.setNombre("Juan Pérez");
        persona.setGenero("Masculino");
        persona.setEdad(30);
        persona.setDireccion("Calle Falsa 123");
        persona.setTelefono("555-1234");
    }

    /**
     * 1) Crear persona exitoso.
     */
    @Test
    void testCreatePersona_Success() {
        // Dado que la persona no existe en el repositorio
        when(personaRepository.existsById("1234567890")).thenReturn(false);
        when(personaRepository.save(persona)).thenReturn(persona);

        // Ejecutamos
        Persona resultado = personaService.createPersona(persona);

        // Verificamos
        assertNotNull(resultado);
        assertEquals("1234567890", resultado.getIdentificacion());
        verify(personaRepository, times(1)).save(persona);
    }

    /**
     * 2) Crear persona que ya existe -> BAD_REQUEST
     */
    @Test
    void testCreatePersona_AlreadyExists() {
        // Dado que la persona ya existe
        when(personaRepository.existsById("1234567890")).thenReturn(true);

        // Ejecutamos y esperamos una excepción
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> personaService.createPersona(persona));

        // Verificamos
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Ya existe la persona con ID"));
    }

    /**
     * 3) Obtener persona que SÍ existe
     */
    @Test
    void testGetPersonaById_Success() {
        // Dado que la persona SÍ existe
        when(personaRepository.findById("1234567890")).thenReturn(Optional.of(persona));

        // Ejecutamos
        Persona resultado = personaService.getPersonaById("1234567890");

        // Verificamos
        assertNotNull(resultado);
        assertEquals("1234567890", resultado.getIdentificacion());
    }

    /**
     * 4) Obtener persona que NO existe -> NOT_FOUND
     */
    @Test
    void testGetPersonaById_NotFound() {
        // Dado que la persona NO existe
        when(personaRepository.findById("1234567890")).thenReturn(Optional.empty());

        // Ejecutamos
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> personaService.getPersonaById("1234567890"));

        // Verificamos
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("Persona no encontrada"));
    }

    /**
     * 5) Eliminar persona que NO existe -> NOT_FOUND
     */
    @Test
    void testDeletePersona_NotFound() {
        // Dado que la persona NO existe
        when(personaRepository.existsById("1234567890")).thenReturn(false);

        // Ejecutamos
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> personaService.deletePersona("1234567890"));

        // Verificamos
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("No existe la persona con ID"));
    }


    // Ejemplo adicional: Actualizar persona no existente
    @Test
    void testUpdatePersona_NotFound() {
        // Dado que la persona NO existe
        when(personaRepository.existsById("1234567890")).thenReturn(false);

        // Ejecutamos
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> personaService.updatePersona(persona));

        // Verificamos
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getReason().contains("No existe la persona con ID"));
    }

    /**
     * Ejemplo de listar todas las personas
     */
    @Test
    void testGetAllPersonas() {
        // Dado que tenemos una lista con la persona
        when(personaRepository.findAll()).thenReturn(List.of(persona));

        // Ejecutamos
        List<Persona> resultado = personaService.getAllPersonas();

        // Verificamos
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("1234567890", resultado.get(0).getIdentificacion());
    }
}
