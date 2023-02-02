package com.mindhub.cerveceria.servicios;

import com.mindhub.cerveceria.dtos.CervezaDTO;
import com.mindhub.cerveceria.entidades.Cerveza;

import java.util.List;

public interface ServicioCerveza {

    List<Cerveza> listarCervezas();

    List<CervezaDTO> listarCervezasDTO();

    void guardarCerveza(Cerveza cerveza);

    Cerveza buscarCervezaPorId(Long id);

    Cerveza crearCerveza(CervezaDTO cerveza);

    List<String> mostrarTipoDeCerveza();

    List<String> mostrarFabricas();

}
