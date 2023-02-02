package com.mindhub.cerveceria.entidades;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
public class ResetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long idPassword;

    private String resetPasswordToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = Cliente.class, fetch = FetchType.EAGER)
    private Cliente cliente;

    public ResetPasswordToken() {
    }

    public ResetPasswordToken(Cliente cliente) {
       this.cliente = cliente;
       this.createdDate = new Date();
       this.resetPasswordToken = UUID.randomUUID().toString();
    }


    public long getIdPassword() {
        return idPassword;
    }

    public void setIdPassword(long idPassword) {
        this.idPassword = idPassword;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
