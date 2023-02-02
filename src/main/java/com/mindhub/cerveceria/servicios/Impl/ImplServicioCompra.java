package com.mindhub.cerveceria.servicios.Impl;

import com.mindhub.cerveceria.dtos.CompraDTO;
import com.mindhub.cerveceria.entidades.Compra;
import com.mindhub.cerveceria.repositorios.RepositorioCompra;
import com.mindhub.cerveceria.servicios.ServicioCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplServicioCompra implements ServicioCompra {


    @Autowired
    RepositorioCompra repositorioCompra;

    @Override
    public List<CompraDTO> comprasAComprasDTO(List<Compra> compras) {
        return compras.stream().map(CompraDTO::new).toList();
    }

    @Override
    public List<CompraDTO> listarCompras() {
        return comprasAComprasDTO(repositorioCompra.findAll());
    }

    @Override
    public void borrarCompra(Long id) {
        repositorioCompra.deleteById(id);
    }

    @Override
    public void guardarCompra(Compra compra) {
        repositorioCompra.save(compra);
    }
}
