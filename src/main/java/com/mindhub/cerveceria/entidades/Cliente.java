package com.mindhub.cerveceria.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public final class Cliente {

    @Id
    private String email;

    private String nombre;

    private String apellido;

    private String password;

    private LocalDate fNacimiento;

    private String ciudad;

    private String pais;

    private Boolean admin;

    private Boolean estaActivado;

    private Integer cp;
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private Set<Compra> compras = new HashSet<>();
    public Cliente() {
    }
    public Cliente(String email, String nombre, String apellido, String password, LocalDate fNacimiento, String ciudad, String pais, Integer cp) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.fNacimiento = fNacimiento;
        this.ciudad = ciudad;
        this.pais = pais;
        this.cp = cp;
        this.admin = false;
        this.estaActivado = false;
    }

    public void agregarCompra(Compra compra){
        this.compras.add(compra);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(LocalDate fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getEstaActivado() {
        return estaActivado;
    }

    public void setEstaActivado(Boolean estaActivado) {
        this.estaActivado = estaActivado;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }
}
