package com.mindhub.cerveceria.servicios;

import com.mindhub.cerveceria.dtos.CompraDTO;
import com.mindhub.cerveceria.entidades.Compra;

import java.util.List;
import java.util.Set;

public interface ServicioCompra {

    List<CompraDTO> comprasAComprasDTO(List<Compra> compras);

    List<CompraDTO> listarCompras();

    void borrarCompra(Long id);

    void guardarCompra(Compra compra);

}
