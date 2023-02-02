package com.mindhub.cerveceria.entidades;

import jakarta.persistence.*;

@Entity
public final class PedidoCerveza {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer cantidad;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cerveza_id")
    private Cerveza cerveza;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compra_id")
    private Compra compra;

    public PedidoCerveza() {
    }

    public PedidoCerveza(Integer cantidad, Cerveza cerveza) {
        this.cantidad = cantidad;
        this.cerveza = cerveza;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Cerveza getCerveza() {
        return cerveza;
    }

    public void setCerveza(Cerveza cerveza) {
        this.cerveza = cerveza;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
