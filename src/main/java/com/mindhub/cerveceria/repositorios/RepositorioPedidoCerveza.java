package com.mindhub.cerveceria.repositorios;

import com.mindhub.cerveceria.entidades.PedidoCerveza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioPedidoCerveza extends JpaRepository<PedidoCerveza, Long> {

}
