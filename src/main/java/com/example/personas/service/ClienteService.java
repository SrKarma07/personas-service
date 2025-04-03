package com.example.personas.service;

import com.example.personas.entity.Cliente;

import java.util.List;

public interface ClienteService {

    Cliente createCliente(Cliente cliente);

    // Recuperar un cliente por su clienteId (no la PK real, sino la única de negocio)
    Cliente getClienteById(String clienteId);

    List<Cliente> getAllClientes();

    // Para actualizar, pasamos el clienteId y el objeto con los nuevos datos
    Cliente updateCliente(String clienteId, Cliente cliente);

    void deleteCliente(String clienteId);

}
