package com.mindhub.cerveceria.entidades;

import com.mindhub.cerveceria.dtos.CervezaDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public final class Cerveza {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private Double abv;
    private Double ibu;
    @Lob
    private String descripcion;
    private Double precio;
    private String fabricante;
    private String img;
    private Integer stock;
    private Boolean estado;
    @Enumerated(EnumType.STRING)
    private TipoCerveza tipoCerveza;
    @Enumerated(EnumType.STRING)
    private PresentacionCerveza presentacion;
    @OneToMany(mappedBy = "cerveza", fetch = FetchType.EAGER)
    private Set<PedidoCerveza> pedidoCerveza = new HashSet<>();

    public Cerveza() {
    }

    public Cerveza(String nombre,
                   Double abv,
                   Double ibu,
                   String descripcion,
                   Double precio,
                   String fabricante,
                   TipoCerveza tipoCerveza,
                   PresentacionCerveza presentacion,
                   String img) {
        this.nombre = nombre;
        this.abv = abv;
        this.ibu = ibu;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fabricante = fabricante;
        this.tipoCerveza = tipoCerveza;
        this.presentacion = presentacion;
        this.img = img;
        this.estado = true;
        this.stock = 50;
    }

    public Cerveza(CervezaDTO cervezaDTO){
        this.nombre = cervezaDTO.getNombre();
        this.abv = cervezaDTO.getAbv();
        this.ibu = cervezaDTO.getIbu();
        this.descripcion = cervezaDTO.getDescripcion();
        this.precio = cervezaDTO.getPrecio();
        this.fabricante = cervezaDTO.getFabricante();
        this.img = cervezaDTO.getImg();
        this.stock = cervezaDTO.getStock();
        this.tipoCerveza = cervezaDTO.getTipoCerveza();
        this.presentacion = cervezaDTO.getPresentacion();
        this.estado = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public Double getIbu() {
        return ibu;
    }

    public void setIbu(Double ibu) {
        this.ibu = ibu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public TipoCerveza getTipoCerveza() {
        return tipoCerveza;
    }

    public void setTipoCerveza(TipoCerveza tipoCerveza) {
        this.tipoCerveza = tipoCerveza;
    }

    public PresentacionCerveza getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(PresentacionCerveza presentacion) {
        this.presentacion = presentacion;
    }

    public Set<PedidoCerveza> getPedidoCerveza() {
        return pedidoCerveza;
    }

    public void setPedidoCerveza(Set<PedidoCerveza> pedidoCerveza) {
        this.pedidoCerveza = pedidoCerveza;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
