package com.mindhub.cerveceria.servicios;

import com.mindhub.cerveceria.dtos.ClienteDTO;
import com.mindhub.cerveceria.entidades.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface ServicioCliente {

    List<Cliente> listarClientes();

    List<ClienteDTO> listarClientesDTO();

    void guardarCliente(Cliente cliente);

    Cliente buscarClientePorEmail(String email);

    ClienteDTO mostrarCliente(Cliente cliente);

}
