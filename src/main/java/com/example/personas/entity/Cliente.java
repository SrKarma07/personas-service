package com.example.personas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "persona_id") // Si quieres enlazar la PK a la de la tabla persona
public class Cliente extends Persona {

    @Column(name = "cliente_id", unique = true, nullable = false)
    private String clienteId;

    private String historialFinanciero;
    private LocalDate fechaInicioHistorial;
    private String estado;

    // Getters y Setters


    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getHistorialFinanciero() {
        return historialFinanciero;
    }

    public void setHistorialFinanciero(String historialFinanciero) {
        this.historialFinanciero = historialFinanciero;
    }

    public LocalDate getFechaInicioHistorial() {
        return fechaInicioHistorial;
    }

    public void setFechaInicioHistorial(LocalDate fechaInicioHistorial) {
        this.fechaInicioHistorial = fechaInicioHistorial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
