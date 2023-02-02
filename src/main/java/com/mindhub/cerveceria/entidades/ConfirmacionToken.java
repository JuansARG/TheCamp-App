package com.mindhub.cerveceria.entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ConfirmacionToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long tokenId;

    private String confirmationToken;

    private LocalDateTime fechaDeCreacion;

    @OneToOne(targetEntity = Cliente.class, fetch = FetchType.EAGER)
    private Cliente cliente;

    public ConfirmacionToken() {
    }

    public ConfirmacionToken(Cliente cliente) {
        this.cliente = cliente;
        this.fechaDeCreacion = LocalDateTime.now();
        this.confirmationToken = UUID.randomUUID().toString();
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public LocalDateTime getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
