package com.mindhub.cerveceria.repositorios;

import com.mindhub.cerveceria.entidades.ConfirmacionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface RepositorioConfirmacionToken extends JpaRepository<ConfirmacionToken, Long> {

    ConfirmacionToken findByConfirmationToken(String confirmationToken);
}
