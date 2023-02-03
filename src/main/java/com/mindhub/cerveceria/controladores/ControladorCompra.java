package com.mindhub.cerveceria.controladores;

import com.mindhub.cerveceria.dtos.CompraDTO;
import com.mindhub.cerveceria.entidades.Cliente;
import com.mindhub.cerveceria.entidades.Compra;
import com.mindhub.cerveceria.entidades.EmpresaEnvio;
import com.mindhub.cerveceria.entidades.EstadoCompra;
import com.mindhub.cerveceria.servicios.ServicioCliente;
import com.mindhub.cerveceria.servicios.ServicioCompra;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.cerveceria.utilidades.Peticion.realizarPeticion;

@RestController
@RequestMapping("/api")
public class ControladorCompra {

    @Autowired
    ServicioCliente servicioCliente;

    @Autowired
    ServicioCompra servicioCompra;

    //TODO: AGREGAR URL AL AUTORIZATION
    @GetMapping("/compras")
    public List<CompraDTO> listarCompras(){
        return servicioCompra.listarCompras();
    }

    //AGREGAR URL AL AUTORIZATION
    @GetMapping("/compras/cliente")
    public List<CompraDTO> mostrarComprasHechasPorElCliente(Authentication auth){

        Cliente cliente = servicioCliente.buscarClientePorEmail(auth.getName());

        if(cliente == null){
            return null;
        }

        return servicioCompra.comprasAComprasDTO(cliente.getCompras()
                .stream()
                .filter(compra -> compra.getEstado().equals(EstadoCompra.CONFIRMADA))
                .toList());
    }

    //TODO: AGREGAR URL AL AUTORIZATION
    @PutMapping("/compras/cliente/cancelar")
    public ResponseEntity<?> cancelarCompra(Authentication auth){

        Cliente cliente = servicioCliente.buscarClientePorEmail(auth.getName());

        if(cliente == null){
            return new ResponseEntity<>("El cliente no esta autorizado.", HttpStatus.FORBIDDEN);
        }

        Compra compraActual =
                cliente.getCompras()
                        .stream()
                        .findFirst()
                        .filter(compra -> compra.getEstado().equals(EstadoCompra.PROGRESO))
                        .get();

        servicioCompra.borrarCompra(compraActual.getId());

        return new ResponseEntity<>("El carrito ha sido vaciado.", HttpStatus.OK);
    }


//    @PostMapping("/comprar/validacion")
//    public ResponseEntity<?> capturarDatosDeCompra(@RequestParam String numeroDeTarjeta,
//                                                   @RequestParam Integer cvv,
//                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDeVencimiento,
//                                                   @RequestParam EmpresaEnvio envio){
//
//        if(numeroDeTarjeta.isEmpty() || numeroDeTarjeta == null){
//            return new ResponseEntity<>("Numero de tarjeta invalido", HttpStatus.FORBIDDEN);
//        }
//
//        if(cvv < 100 || cvv > 999){
//            return new ResponseEntity<>("Numero de cvv invalido", HttpStatus.FORBIDDEN);
//        }
//
//        if(fechaDeVencimiento == null){
//            return new ResponseEntity<>("Fecha de vencimiento invalida", HttpStatus.FORBIDDEN);
//        }
//
//        if(     envio.toString().equalsIgnoreCase(EmpresaEnvio.ANDREANI.toString()) ||
//                envio.toString().equalsIgnoreCase(EmpresaEnvio.CORREOARGENTINO.toString()) ||
//                envio.toString().equalsIgnoreCase(EmpresaEnvio.MERCADOENVIO.toString())){
//            return new ResponseEntity<>("Metodo de envio invalido.", HttpStatus.FORBIDDEN);
//        }
//
//        return new ResponseEntity<>(true, HttpStatus.OK);
//
//    }


    //TODO: AGREGAR URL AL AUTORIZATION
    @PostMapping("/comprar")
    public ResponseEntity<?> pagar(Authentication auth,
                                   @RequestParam String numeroDeTarjeta,
                                   @RequestParam Integer cvv,
                                   @RequestParam EmpresaEnvio envio,
                                   @RequestParam Double montoTotal){

        Cliente cliente = servicioCliente.buscarClientePorEmail(auth.getName());

        if(cliente == null){
            return new ResponseEntity<>("El cliente no esta autorizado.", HttpStatus.FORBIDDEN);
        }

        Compra compraActual =
                cliente.getCompras().stream().filter(compra -> compra.getEstado().equals(EstadoCompra.PROGRESO)).findFirst().get();

        if(numeroDeTarjeta.isEmpty() || numeroDeTarjeta == null){
            return new ResponseEntity<>("Numero de tarjeta invalido", HttpStatus.FORBIDDEN);
        }

        if(cvv < 100 || cvv > 999){
            return new ResponseEntity<>("Numero de cvv invalido", HttpStatus.FORBIDDEN);
        }

//        if(fechaDeVencimiento == null){
//            return new ResponseEntity<>("Fecha de vencimiento invalida", HttpStatus.FORBIDDEN);
//        }

        if(envio == null){
            return new ResponseEntity<>("Metodo de envio invalido.", HttpStatus.FORBIDDEN);
        }

        if(montoTotal.isInfinite() || montoTotal.isNaN()){
            return new ResponseEntity<>("Monto invalido.", HttpStatus.FORBIDDEN);
        }

        //LLAMAR AL METODO DE LA PETICION
        Boolean result = realizarPeticion(montoTotal, numeroDeTarjeta, cvv);
        if(result){
            compraActual.setEstado(EstadoCompra.CONFIRMADA);
            compraActual.setFechaDeCreacion(LocalDate.now());
            compraActual.setNumeroDeTarjeta(numeroDeTarjeta);
            compraActual.setCvv(cvv);
            compraActual.setMontoTotal(montoTotal);
            compraActual.setEnvio(envio);
            servicioCompra.guardarCompra(compraActual);
            return new ResponseEntity<>("PAGO APROBADO", HttpStatus.OK);
        }
        compraActual.setEstado(EstadoCompra.RECHAZADA);
        servicioCompra.guardarCompra(compraActual);
        return new ResponseEntity<>("ALGO SALIO MAL", HttpStatus.CONFLICT);

    }
}
