package com.mindhub.cerveceria.servicios.Impl;

import com.mindhub.cerveceria.dtos.PedidoCervezaDTO;
import com.mindhub.cerveceria.entidades.PedidoCerveza;
import com.mindhub.cerveceria.repositorios.RepositorioPedidoCerveza;
import com.mindhub.cerveceria.servicios.ServicioPedidoCerveza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplServicioPedidoCerveza implements ServicioPedidoCerveza {

    @Autowired
    RepositorioPedidoCerveza repositorioPedidoCerveza;

    @Override
    public void guardarPedidoCerveza(PedidoCerveza pedidoCerveza) {
        repositorioPedidoCerveza.save(pedidoCerveza);
    }

    @Override
    public PedidoCerveza buscarPedidoCervezaPorId(Long id) {
        return repositorioPedidoCerveza.findById(id).orElse(null);
    }

    @Override
    public void borrarPedidoCerveza(PedidoCerveza cerveza) {
        repositorioPedidoCerveza.delete(cerveza);
    }

    @Override
    public List<PedidoCervezaDTO> listarPedidosCerveza() {
        return repositorioPedidoCerveza.findAll().stream().map(PedidoCervezaDTO::new).toList();
    }
}
