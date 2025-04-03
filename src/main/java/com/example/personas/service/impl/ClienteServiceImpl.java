package com.example.personas.service.impl;

import com.example.personas.entity.Cliente;
import com.example.personas.repository.ClienteRepository;
import com.example.personas.service.ClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    // Inyectamos el repositorio vía constructor
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente createCliente(Cliente cliente) {
        // Aquí podrías validar si existe ya un cliente con el mismo clienteId
        if (clienteRepository.existsByClienteId(cliente.getClienteId())) {
            throw new RuntimeException("Ya existe un cliente con el ID: " + cliente.getClienteId());
        }
        // Guardamos
        return clienteRepository.save(cliente);
    }

    public Cliente getClienteById(String clienteId) {
        return clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new RuntimeException("No existe el cliente con ID: " + clienteId));
    }


    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente updateCliente(String clienteId, Cliente cliente) {
        // Verificamos si existe el cliente a actualizar
        Cliente existente = getClienteById(clienteId);

        // Asignamos nuevos valores al que ya existe:
        // (puedes asignar todos o solo los que quieras permitir actualizar)
        existente.setIdentificacion(cliente.getIdentificacion());
        existente.setHistorialFinanciero(cliente.getHistorialFinanciero());
        existente.setFechaInicioHistorial(cliente.getFechaInicioHistorial());
        existente.setEstado(cliente.getEstado());
        existente.setNombre(cliente.getNombre());
        existente.setGenero(cliente.getGenero());
        existente.setEdad(cliente.getEdad());
        existente.setDireccion(cliente.getDireccion());
        existente.setTelefono(cliente.getTelefono());
        // Mantenemos el clienteId anterior (o sobrescribimos si quieres cambiarlo)
        existente.setClienteId(clienteId);

        // Guardamos
        return clienteRepository.save(existente);
    }

    @Override
    @Transactional
    public void deleteCliente(String clienteId) {
        if (!clienteRepository.existsByClienteId(clienteId)) {
            throw new RuntimeException("No existe el cliente con ID: " + clienteId);
        }
        clienteRepository.deleteByClienteId(clienteId);
    }
}
