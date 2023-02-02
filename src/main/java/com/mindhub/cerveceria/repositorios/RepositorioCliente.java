package com.mindhub.cerveceria.repositorios;

import com.mindhub.cerveceria.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioCliente extends JpaRepository<Cliente, String> {

    Cliente findByEmail(String email);
}
