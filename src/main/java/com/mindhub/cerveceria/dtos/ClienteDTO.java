package com.mindhub.cerveceria.dtos;

import com.mindhub.cerveceria.entidades.Cliente;
import com.mindhub.cerveceria.entidades.EstadoCompra;

import java.time.LocalDate;
import java.util.List;

public class ClienteDTO {

    private String email;
    private String nombre;
    private String apellido;
    private LocalDate fNacimiento;
    private String ciudad;
    private String pais;
    private Integer cp;

    private List<CompraDTO> compra;

    public ClienteDTO(Cliente cliente){
        this.email = cliente.getEmail();
        this.nombre = cliente.getNombre();
        this.apellido = cliente.getApellido();
        this.fNacimiento = cliente.getfNacimiento();
        this.ciudad = cliente.getCiudad();
        this.pais = cliente.getPais();
        this.cp = cliente.getCp();
        this.compra =
                cliente.getCompras().
                        stream()
                        .filter(compra -> compra.getEstado()
                                .equals(EstadoCompra.PROGRESO))
                        .map(CompraDTO::new)
                        .toList();
    }

    //SI LA COMPRA FUE CONFIRMADA, QUEDA COMO HISTORIAL, SE ARCHIVA
    //AHORA SI LA COMPRA ESTA EN PROGRESO SE DEVUELVE PARA RENDERIZAR COMO CARRO

    public String getEmail() {
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getfNacimiento() {
        return fNacimiento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public Integer getCp() {
        return cp;
    }

    public List<CompraDTO> getCompra() {
        return compra;
    }
}
