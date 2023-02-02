package com.mindhub.cerveceria.dtos;

import com.mindhub.cerveceria.entidades.Cerveza;
import com.mindhub.cerveceria.entidades.PresentacionCerveza;
import com.mindhub.cerveceria.entidades.TipoCerveza;

public class CervezaDTO {

    private Long id;
    private String nombre;
    private Double abv;
    private Double ibu;
    private String descripcion;
    private Double precio;
    private TipoCerveza tipoCerveza;
    private PresentacionCerveza presentacion;
    private String fabricante;
    private String img;
    private Integer stock;

    public CervezaDTO(Cerveza cerveza){
        id = cerveza.getId();
        nombre = cerveza.getNombre();
        abv = cerveza.getAbv();
        ibu = cerveza.getIbu();
        descripcion = cerveza.getDescripcion();
        precio = cerveza.getPrecio();
        tipoCerveza = cerveza.getTipoCerveza();
        presentacion = cerveza.getPresentacion();
        fabricante = cerveza.getFabricante();
        img = cerveza.getImg();
        stock = cerveza.getStock();
    }

    public CervezaDTO(String nombre, Double abv, Double ibu, String descripcion, Double precio,
                      TipoCerveza tipoCerveza, PresentacionCerveza presentacion, String fabricante, String img,
                      Integer stock) {
        this.nombre = nombre;
        this.abv = abv;
        this.ibu = ibu;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipoCerveza = tipoCerveza;
        this.presentacion = presentacion;
        this.fabricante = fabricante;
        this.img = img;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getAbv() {
        return abv;
    }

    public Double getIbu() {
        return ibu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public TipoCerveza getTipoCerveza() {
        return tipoCerveza;
    }

    public PresentacionCerveza getPresentacion() {
        return presentacion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getImg() {
        return img;
    }

    public Integer getStock() {
        return stock;
    }

}
