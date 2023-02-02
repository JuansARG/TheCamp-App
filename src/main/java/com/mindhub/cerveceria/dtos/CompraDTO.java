package com.mindhub.cerveceria.dtos;

import com.mindhub.cerveceria.entidades.Compra;
import com.mindhub.cerveceria.entidades.EmpresaEnvio;
import com.mindhub.cerveceria.entidades.EstadoCompra;

import java.time.LocalDate;
import java.util.List;

public final class CompraDTO {

    private Double montoTotal;
    private LocalDate fechaDeCreacion;
    private EstadoCompra estado;
    private String numeroDeTarjeta;
    private Integer cvv;
    private LocalDate fechaDeVencimientoTarjeta;
    private EmpresaEnvio envio;
    private List<PedidoCervezaDTO> pedidoCerveza;
    public CompraDTO(Compra compra){
        fechaDeCreacion = compra.getFechaDeCreacion();
        estado = compra.getEstado();
        numeroDeTarjeta = compra.getNumeroDeTarjeta();
        cvv = compra.getCvv();
        fechaDeVencimientoTarjeta = compra.getFechaDeVencimientoTarjeta();
        envio = compra.getEnvio();
        pedidoCerveza = compra.getPedidoCerveza().stream().map(PedidoCervezaDTO::new).toList();
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public LocalDate getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public String getNumeroDeTarjeta() {
        return numeroDeTarjeta;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getFechaDeVencimientoTarjeta() {
        return fechaDeVencimientoTarjeta;
    }

    public EmpresaEnvio getEnvio() {
        return envio;
    }

    public List<PedidoCervezaDTO> getPedidoCerveza() {
        return pedidoCerveza;
    }

}
