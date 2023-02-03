package com.mindhub.cerveceria.controladores;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    @PostMapping("/pedido/agregar")
    public ResponseEntity<?> crearPedido(Authentication auth, @RequestParam Long id){

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
            nuevoPedido.setCantidad(1);
            nuevoPedido.setCompra(compraNueva);
            servicioPedidoCerveza.guardarPedidoCerveza(nuevoPedido);

            return new ResponseEntity<>("Se ha creado una nueva orden de compra y agregado un nuevo pedido.",
                    HttpStatus.OK);

        }

        // COMPROBAR QUE EL ID DE LA CERVEZA, NO PERTENEZA A UN PEDIDO EXISTENTE
        if(compraEnProgreso.get().getPedidoCerveza().stream().noneMatch(pedidoCerveza -> pedidoCerveza.getCerveza().getId().equals(id))){

            PedidoCerveza nuevoPedido = new PedidoCerveza();
            nuevoPedido.setCerveza(servicioCerveza.buscarCervezaPorId(id));
            nuevoPedido.setCantidad(1);
            nuevoPedido.setCompra(compraEnProgreso.get());
            servicioPedidoCerveza.guardarPedidoCerveza(nuevoPedido);

            return new ResponseEntity<>("Se ha agregado un nuevo pedido a la orden de compra.", HttpStatus.OK);

        }

            PedidoCerveza pedidoActual = compraEnProgreso.get()
                    .getPedidoCerveza()
                    .stream().
                    filter(pedidoCerveza -> pedidoCerveza.getCerveza().getId().equals(id))
                    .findFirst()
                    .get();

            pedidoActual.setCantidad(pedidoActual.getCantidad() + 1);
            servicioPedidoCerveza.guardarPedidoCerveza(pedidoActual);


            return new ResponseEntity<>("Se ha incrementado la cantidad del producto.",
                    HttpStatus.OK);


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
        System.out.println(compraActual.get().getPedidoCerveza().size());

        if(compraActual.get().getPedidoCerveza().size() == 1){
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

        if(pedidoObjetivo.getCantidad().equals(pedidoObjetivo.getCerveza().getStock())){
            return new ResponseEntity<>("Usted dispone en su carrito todas las unidades de esta cerveza.",
                    HttpStatus.CONFLICT);
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

        if(pedidoObjetivo.getCantidad() == 1){
            servicioPedidoCerveza.borrarPedidoCerveza(pedidoObjetivo);
            return new ResponseEntity<>("El item ha sido borrado de la orden de compra.", HttpStatus.OK);
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

        if(cantidad > pedidoObjetivo.getCerveza().getStock()){
            return new ResponseEntity<>("No puede agregar mas unidades de las que tenemos de stock.",
                    HttpStatus.CONFLICT);
        }

        pedidoObjetivo.setCantidad(cantidad);
        servicioPedidoCerveza.guardarPedidoCerveza(pedidoObjetivo);

        return new ResponseEntity<>("Se ha modificado la cantidad del item.", HttpStatus.OK);
    }

    private ByteArrayInputStream createPdf(List<PedidoCervezaDTO> pedidos) throws IOException {
        Document document = new Document( PageSize.A4, 36, 36, 90, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

//        Image image = Image.getInstance("./static/web/assets/imagenes/logo-nombre-largo.png");
//        image.setAbsolutePosition(250f, 700f);
//        document.add(image);
        // Creating a table
        Table table = new Table(4);
        table.setWidth(100);
        table.setBorderWidth(1);
        table.setPadding(5);

        // Adding cells to the table
        table.addCell("Nombre");
        table.addCell("Cantidad");
        table.addCell("Precio Unitario");
        table.addCell("Precio Total");
        pedidos.forEach(pedido -> {
            table.addCell(pedido.getCerveza().getNombre());
            table.addCell(pedido.getCantidad().toString());
            table.addCell(pedido.getCerveza().getPrecio().toString());
            table.addCell(String.valueOf(pedido.getCerveza().getPrecio() * pedido.getCantidad()));
        });
        Cell total =
                new Cell("Total: "+ String.valueOf(pedidos.stream().mapToDouble(pedidoCervezaDTO -> pedidoCervezaDTO.getCantidad() * pedidoCervezaDTO.getCerveza().getPrecio()).sum()));
        total.setColspan(3);
        table.addCell(total);

        document.add(table);

        // Closing the document
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
    @GetMapping("/pedido/descargar-pdf")
    public ResponseEntity<InputStreamResource> downloadPdf(Authentication auth) throws IOException{
        Cliente clienteActual = servicioCliente.buscarClientePorEmail(auth.getName());
        Compra compraActual = clienteActual.getCompras().stream().findFirst().filter(compra -> compra.getEstado().equals(EstadoCompra.CONFIRMADA)).get();
        List<PedidoCervezaDTO> listaPedidoCLiente = compraActual.getPedidoCerveza().stream().map(PedidoCervezaDTO::new).toList();
        ByteArrayInputStream bis = createPdf(listaPedidoCLiente);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=theCamp.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}
