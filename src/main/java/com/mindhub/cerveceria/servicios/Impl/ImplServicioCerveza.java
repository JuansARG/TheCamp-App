package com.mindhub.cerveceria.servicios.Impl;

import com.mindhub.cerveceria.dtos.CervezaDTO;
import com.mindhub.cerveceria.entidades.Cerveza;
import com.mindhub.cerveceria.repositorios.RepositorioCerveza;
import com.mindhub.cerveceria.servicios.ServicioCerveza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImplServicioCerveza implements ServicioCerveza {

    @Autowired
    RepositorioCerveza repositorioCerveza;

    @Override
    public List<Cerveza> listarCervezas() {
        return repositorioCerveza.findAll().stream().filter(Cerveza::getEstado).toList();
    }

    @Override
    public List<CervezaDTO> listarCervezasDTO() {
        return repositorioCerveza.findAll().stream().filter(Cerveza::getEstado).map(CervezaDTO::new).toList();
    }

    @Override
    public void guardarCerveza(Cerveza cerveza) {
        repositorioCerveza.save(cerveza);
    }

    @Override
    public Cerveza buscarCervezaPorId(Long id) {
        return repositorioCerveza.findById(id).orElse(null);
    }

    @Override
    public Cerveza crearCerveza(CervezaDTO cerveza) {
        return new Cerveza(cerveza);
    }

    @Override
    public List<String> mostrarTipoDeCerveza() {
        return repositorioCerveza.findAll()
                .stream().
                map(cerveza -> cerveza.getTipoCerveza().toString())
                .collect(Collectors.toSet())
                .stream()
                .toList();
    }

    @Override
    public List<String> mostrarFabricas() {
        return repositorioCerveza.findAll()
                .stream()
                .map(cerveza -> cerveza.getFabricante().toString())
                .collect(Collectors.toSet())
                .stream()
                .toList();
    }

}
