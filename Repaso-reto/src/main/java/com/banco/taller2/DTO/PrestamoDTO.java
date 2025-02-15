package com.banco.taller2.DTO;

import com.banco.taller2.Model.Prestamo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PrestamoDTO {
    private BigDecimal monto;
    private BigDecimal interes;
    private int duracionMeses;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String clienteId;
    private Prestamo prestamo;


    public PrestamoDTO(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public PrestamoDTO(BigDecimal monto, BigDecimal interes, int duracionMeses, String estado, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion, String clienteId) {
        this.monto = monto;
        this.interes = interes;
        this.duracionMeses = duracionMeses;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.clienteId = clienteId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getClienteId() {return clienteId;}
    public void setClienteId(ClienteDTO clienteDTO) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getFecha_creacion() {
        return fechaCreacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fechaCreacion = fecha_creacion;
    }

    public LocalDateTime getFecha_actualizacion() {
        return fechaActualizacion;
    }

    public void setFecha_actualizacion(LocalDateTime fecha_actualizacion) {
        this.fechaActualizacion = fecha_actualizacion;
    }
}
