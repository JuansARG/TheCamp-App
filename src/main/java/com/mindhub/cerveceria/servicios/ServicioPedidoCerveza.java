package com.mindhub.cerveceria.servicios;

import com.mindhub.cerveceria.dtos.PedidoCervezaDTO;
import com.mindhub.cerveceria.entidades.PedidoCerveza;

import java.util.List;

public interface ServicioPedidoCerveza {

    void guardarPedidoCerveza(PedidoCerveza pedidoCerveza);

    PedidoCerveza buscarPedidoCervezaPorId(Long id);

    void borrarPedidoCerveza(PedidoCerveza cerveza);

    List<PedidoCervezaDTO> listarPedidosCerveza();
}
