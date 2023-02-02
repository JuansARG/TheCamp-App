package com.mindhub.cerveceria;

import com.mindhub.cerveceria.entidades.Cerveza;
import com.mindhub.cerveceria.entidades.Cliente;
import com.mindhub.cerveceria.repositorios.RepositorioCliente;
import com.mindhub.cerveceria.servicios.ServicioCerveza;
import com.mindhub.cerveceria.servicios.ServicioCliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriosTest {

    @Autowired
    ServicioCliente servicioCliente;
    @Autowired
    ServicioCerveza servicioCerveza;

    @Test
    public void verificarQueExistanCervezas(){
        List<Cerveza> cervezas = servicioCerveza.listarCervezas();
        assertThat(cervezas, is(not(empty())));
    }

    @Test
    public void verificarQueExistanClientes(){
        List<Cliente> clientes =  servicioCliente.listarClientes();
        assertThat(clientes, is(not(empty())));
    }


}
