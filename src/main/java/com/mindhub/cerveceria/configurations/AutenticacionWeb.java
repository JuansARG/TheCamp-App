package com.mindhub.cerveceria.configurations;


import com.mindhub.cerveceria.entidades.Cliente;
import com.mindhub.cerveceria.repositorios.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AutenticacionWeb  extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    RepositorioCliente repositorioCliente;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputEmail-> {
            Cliente cliente = repositorioCliente.findByEmail(inputEmail);
            if (cliente != null) {
                if (cliente.getAdmin()){
                    return new User(cliente.getEmail(), cliente.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN","CLIENTE"));
                }else{
                    return new User(cliente.getEmail(), cliente.getPassword(),
                            AuthorityUtils.createAuthorityList("CLIENTE"));
                }

            } else {
                throw new UsernameNotFoundException("Usuario Desconocido: " + inputEmail);
            }
        });
    }


    //AGREGAR PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
