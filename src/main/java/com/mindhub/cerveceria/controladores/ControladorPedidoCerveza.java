package com.mindhub.cerveceria.controladores;

import com.mindhub.cerveceria.dtos.PedidoCervezaDTO;
import com.mindhub.cerveceria.entidades.Cliente;
import com.mindhub.cerveceria.entidades.Compra;
import com.mindhub.cerveceria.entidades.EstadoCompra;
import com.mindhub.cerveceria.entidades.PedidoCerveza;
import com.mindhub.cerveceria.servicios.ServicioCerveza;
import com.mindhub.cerveceria.servicios.ServicioCliente;
import com.mindhub.cerveceria.servicios.ServicioCompra;
import com.mindhub.cerveceria.servicios.ServicioPedidoCerveza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ControladorPedidoCerveza {

    @Autowired
    ServicioCliente servicioCliente;

    @Autowired
    ServicioCerveza servicioCerveza;

    @Autowired
    ServicioCompra servicioCompra;

    @Autowired
    ServicioPedidoCerveza servicioPedidoCerveza;

    @GetMapping("/pedidoCerveza/listar")
    public List<PedidoCervezaDTO> listarPedidosCerveza() {
        return servicioPedidoCerveza.listarPedidosCerveza();
    }

    //TODO: agregar url al autorization
    @Transactional
    @PostMapping("/nuevo-pedido")
    public ResponseEntity<?> crearPedido(Authentication auth, @RequestParam Long id, @RequestParam Integer cantidad){

        Cliente clienteActual = servicioCliente.buscarClientePorEmail(auth.getName());

        if(clienteActual == null){
            return new ResponseEntity<>("Debe estar autenticado para realizar esta accion.", HttpStatus.FORBIDDEN);
        }

        Optional<Compra> compraEnProgreso =
                clienteActual
                        .getCompras()
                        .stream()
                        .filter(compra -> compra.getEstado().toString().equals(EstadoCompra.PROGRESO.toString()))
                        .findFirst();

        if(compraEnProgreso.isEmpty()){

            Compra compraNueva = new Compra();
            compraNueva.setEstado(EstadoCompra.PROGRESO);
            compraNueva.setCliente(clienteActual);
            servicioCompra.guardarCompra(compraNueva);

            PedidoCerveza nuevoPedido = new PedidoCerveza();
            nuevoPedido.setCerveza(servicioCerveza.buscarCervezaPorId(id));
            nuevoPedido.setCantidad(cantidad);
            nuevoPedido.setCompra(compraNueva);
            servicioPedidoCerveza.guardarPedidoCerveza(nuevoPedido);

            return new ResponseEntity<>("Se ha creado una nueva orden de compra y agregado un nuevo pedido.",
                    HttpStatus.OK);

        }else{

            PedidoCerveza nuevoPedido = new PedidoCerveza();
            nuevoPedido.setCerveza(servicioCerveza.buscarCervezaPorId(id));
            nuevoPedido.setCantidad(cantidad);
            nuevoPedido.setCompra(compraEnProgreso.get());

            return new ResponseEntity<>("Se ha agregado un nuevo pedido a la orden de compra.",
                    HttpStatus.OK);

        }
    }

    //TODO: agregar url al autorization
    @DeleteMapping("/pedido/cancelar/{id}")
    public ResponseEntity<?> borrarPedidoDeLaCompra(Authentication auth, @PathVariable Long id){

        Cliente clienteActual = servicioCliente.buscarClientePorEmail(auth.getName());

        if(clienteActual == null){
            return new ResponseEntity<>("Debe estar autenticado para realizar esta accion.", HttpStatus.FORBIDDEN);
        }

        Optional<Compra> compraActual = clienteActual.getCompras()
                .stream()
                .filter(compra -> compra.getEstado().toString().equals(EstadoCompra.PROGRESO.toString())).findFirst();

        if(compraActual.isEmpty()){
            return new ResponseEntity<>("El item que desea borrar no pertenece a una orden de compra.",
                    HttpStatus.FORBIDDEN);
        }

        PedidoCerveza pedidoObjetivo = servicioPedidoCerveza.buscarPedidoCervezaPorId(id);

        if(pedidoObjetivo == null){
            return new ResponseEntity<>("El pedido que desea cancelar no existe.", HttpStatus.FORBIDDEN);
        }

        PedidoCerveza pedidoCervezaActual = compraActual.get()
                .getPedidoCerveza()
                .stream()
                .filter(pedidoCerveza -> pedidoCerveza.getId().equals(id)).findFirst().get();

        servicioPedidoCerveza.borrarPedidoCerveza(pedidoCervezaActual);

        if(compraActual.get().getPedidoCerveza().size() == 0){
            servicioCompra.borrarCompra(compraActual.get().getId());
        }

        return new ResponseEntity<>("El item ha sido borrado de la orden de compra.", HttpStatus.OK);

    }

    //TODO: agregar url al autorization
    @Transactional
    @PatchMapping("/pedido/aumentar")
    public ResponseEntity<?> aumentarCantidadPedidoCerveza(Authentication auth, @RequestParam Long id){

        Cliente clienteActual = servicioCliente.buscarClientePorEmail(auth.getName());

        if(clienteActual == null){
            return new ResponseEntity<>("Debe estar autenticado para realizar esta accion.", HttpStatus.FORBIDDEN);
        }

        PedidoCerveza pedidoObjetivo = servicioPedidoCerveza.buscarPedidoCervezaPorId(id);

        if(pedidoObjetivo == null){
            return new ResponseEntity<>("El pedido sobre el cual desea modificar la cantidad no existe.",
                    HttpStatus.FORBIDDEN);
        }

        pedidoObjetivo.setCantidad(pedidoObjetivo.getCantidad() + 1);

        servicioPedidoCerveza.guardarPedidoCerveza(pedidoObjetivo);

        return new ResponseEntity<>("Se ha modificado la cantidad del item.", HttpStatus.OK);
    }

    //TODO: agregar url al autorization
    @Transactional
    @PatchMapping("/pedido/decrementar")
    public ResponseEntity<?> decrementarCantidadPedidoCerveza(Authentication auth, @RequestParam Long id){

        Cliente clienteActual = servicioCliente.buscarClientePorEmail(auth.getName());

        if(clienteActual == null){
            return new ResponseEntity<>("Debe estar autenticado para realizar esta accion.", HttpStatus.FORBIDDEN);
        }

        PedidoCerveza pedidoObjetivo = servicioPedidoCerveza.buscarPedidoCervezaPorId(id);

        if(pedidoObjetivo == null){
            return new ResponseEntity<>("El pedido sobre el cual desea modificar la cantidad no existe.",
                    HttpStatus.FORBIDDEN);
        }

        pedidoObjetivo.setCantidad(pedidoObjetivo.getCantidad() - 1);

        servicioPedidoCerveza.guardarPedidoCerveza(pedidoObjetivo);

        return new ResponseEntity<>("Se ha modificado la cantidad del item.", HttpStatus.OK);
    }

    //TODO: agregar url al autorization
    @Transactional
    @PatchMapping("/pedido/cantidad")
    public ResponseEntity<?> modificarCantidadPedidoCerveza(Authentication auth,
                                                            @RequestParam Long id,
                                                            @RequestParam Integer cantidad){

        Cliente clienteActual = servicioCliente.buscarClientePorEmail(auth.getName());

        if(clienteActual == null){
            return new ResponseEntity<>("Debe estar autenticado para realizar esta accion.", HttpStatus.FORBIDDEN);
        }

        PedidoCerveza pedidoObjetivo = servicioPedidoCerveza.buscarPedidoCervezaPorId(id);

        if(pedidoObjetivo == null){
            return new ResponseEntity<>("El pedido sobre el cual desea modificar la cantidad no existe.",
                    HttpStatus.FORBIDDEN);
        }

        pedidoObjetivo.setCantidad(cantidad);

        servicioPedidoCerveza.guardarPedidoCerveza(pedidoObjetivo);

        return new ResponseEntity<>("Se ha modificado la cantidad del item.", HttpStatus.OK);
    }

}
