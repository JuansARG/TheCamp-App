package com.mindhub.cerveceria.repositorios;


import com.mindhub.cerveceria.entidades.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RepositorioResetPassword  extends JpaRepository<ResetPasswordToken, Long> {

    ResetPasswordToken findByresetPasswordToken(String resetPasswordToken);
}
