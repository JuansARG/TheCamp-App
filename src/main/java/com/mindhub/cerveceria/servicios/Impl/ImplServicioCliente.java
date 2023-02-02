package com.mindhub.cerveceria.servicios.Impl;

import com.mindhub.cerveceria.dtos.ClienteDTO;
import com.mindhub.cerveceria.entidades.Cliente;
import com.mindhub.cerveceria.repositorios.RepositorioCliente;
import com.mindhub.cerveceria.servicios.ServicioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ImplServicioCliente implements ServicioCliente {

    @Autowired
    RepositorioCliente repositorioCliente;
    @Override
    public List<Cliente> listarClientes() {
        return repositorioCliente.findAll();
    }

    @Override
    public List<ClienteDTO> listarClientesDTO() {
        return repositorioCliente.findAll().stream().map(ClienteDTO::new).toList();
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        repositorioCliente.save(cliente);
    }

    @Override
    public Cliente buscarClientePorEmail(String email) {
        return repositorioCliente.findByEmail(email);
    }

    @Override
    public ClienteDTO mostrarCliente(Cliente cliente) {
        return new ClienteDTO(cliente);
    }

}
