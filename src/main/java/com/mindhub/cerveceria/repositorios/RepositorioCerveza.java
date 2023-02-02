package com.mindhub.cerveceria.repositorios;

import com.mindhub.cerveceria.entidades.Cerveza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioCerveza extends JpaRepository<Cerveza, Long> {
}
