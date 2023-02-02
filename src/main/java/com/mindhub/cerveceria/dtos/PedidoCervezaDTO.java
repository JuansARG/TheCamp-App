package com.mindhub.cerveceria.dtos;

import com.mindhub.cerveceria.entidades.PedidoCerveza;

public final class PedidoCervezaDTO {

    private Long id;
    private Integer cantidad;
    private CervezaDTO cerveza;

    public PedidoCervezaDTO(PedidoCerveza pedidoCerveza) {
        this.id = pedidoCerveza.getId();
        this.cantidad = pedidoCerveza.getCantidad();
        this.cerveza = new CervezaDTO(pedidoCerveza.getCerveza());
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public CervezaDTO getCerveza() {
        return cerveza;
    }

    public Long getId() {
        return id;
    }
}
