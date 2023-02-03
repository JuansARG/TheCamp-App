package com.mindhub.cerveceria.entidades;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public final class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Double montoTotal;
    private LocalDate fechaDeCreacion;
    @Enumerated(EnumType.STRING)
    private EstadoCompra estado;
    private String numeroDeTarjeta;
    private Integer cvv;
    private LocalDate fechaDeVencimientoTarjeta;
    private EmpresaEnvio envio;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToMany(mappedBy = "compra", fetch = FetchType.EAGER)
    private Set<PedidoCerveza> pedidoCerveza = new HashSet<>();

    public Compra() {
    }

    public Compra(Cliente cliente) {
        this.cliente = cliente;
    }

    public void agregarPedidoCerveza(PedidoCerveza pedidoCerveza){
        this.pedidoCerveza.add(pedidoCerveza);
        pedidoCerveza.setCompra(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<PedidoCerveza> getPedidoCerveza() {
        return pedidoCerveza;
    }

    public void setPedidoCerveza(Set<PedidoCerveza> pedidoCerveza) {
        this.pedidoCerveza = pedidoCerveza;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDate fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
    }

    public String getNumeroDeTarjeta() {
        return numeroDeTarjeta;
    }

    public void setNumeroDeTarjeta(String numeroDeTarjeta) {
        this.numeroDeTarjeta = numeroDeTarjeta;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFechaDeVencimientoTarjeta() {
        return fechaDeVencimientoTarjeta;
    }

    public void setFechaDeVencimientoTarjeta(LocalDate fechaDeVencimientoTarjeta) {
        this.fechaDeVencimientoTarjeta = fechaDeVencimientoTarjeta;
    }

    public EmpresaEnvio getEnvio() {
        return envio;
    }

    public void setEnvio(EmpresaEnvio envio) {
        this.envio = envio;
    }
}
