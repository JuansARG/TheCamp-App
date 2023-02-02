package com.mindhub.cerveceria.controladores;

import com.mindhub.cerveceria.dtos.CervezaDTO;
import com.mindhub.cerveceria.entidades.Cerveza;
import com.mindhub.cerveceria.entidades.PresentacionCerveza;
import com.mindhub.cerveceria.servicios.ServicioCerveza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorCerveza {

    @Autowired
    ServicioCerveza servicioCerveza;

    @GetMapping("/cervezas")
    public List<CervezaDTO> listarCervezas(){
        return servicioCerveza.listarCervezasDTO();
    }

    @GetMapping("/cervezas/{id}")
    public CervezaDTO mostrarCerveza(@PathVariable Long id){
        return new CervezaDTO(servicioCerveza.buscarCervezaPorId(id));
    }

    @GetMapping("/cervezas/tipos")
    public List<String> mostrarTipos(){
        return servicioCerveza.mostrarTipoDeCerveza();
    }

    @GetMapping("/cervezas/fabricas")
    public List<String> mostrarFabricantes(){
        return servicioCerveza.mostrarFabricas();
    }

    @GetMapping("/cervezas/presentaciones")
    public List<String> mostrarPresentaciones(){
        return List.of("LATA", "BOTELLA");
    }

    @PostMapping("/cervezas/agregar")
    public ResponseEntity<?> agregarCerveza(@RequestBody CervezaDTO cervezaDTO){

        if(cervezaDTO.getNombre().isEmpty()){
            return new ResponseEntity<>("Nombre invalido.", HttpStatus.FORBIDDEN);
        }

        if(cervezaDTO.getAbv() < 0){
            return new ResponseEntity<>("Valor de abv invalido.", HttpStatus.FORBIDDEN);
        }

        if(cervezaDTO.getIbu() < 0){
            return new ResponseEntity<>("Valor de ibu invalido.", HttpStatus.FORBIDDEN);
        }

        if(cervezaDTO.getDescripcion().isEmpty()){
            return new ResponseEntity<>("Descripcion invalida.", HttpStatus.FORBIDDEN);
        }

        if(cervezaDTO.getPrecio() < 100){
            return new ResponseEntity<>("Precio invalido.", HttpStatus.FORBIDDEN);
        }

        List<String> categorias = List.of("APA", "IPA", "STOUT", "PORTER", "LAGER", "HONEY", "ALE");
        if(!categorias.contains(cervezaDTO.getTipoCerveza().toString())){
            return new ResponseEntity<>("Categoria invalida.", HttpStatus.FORBIDDEN);
        }

        List<String> presentaciones = List.of("LATA",  "BOTELLA");
        if(!presentaciones.contains(cervezaDTO.getPresentacion().toString())){
            return new ResponseEntity<>("Presentacion invalida.", HttpStatus.FORBIDDEN);
        }

        if(cervezaDTO.getFabricante().isEmpty()){
            return new ResponseEntity<>("Fabrica invalida.", HttpStatus.FORBIDDEN);
        }

        if(cervezaDTO.getImg().isEmpty()){
            return new ResponseEntity<>("El link de la imagen es invalido.", HttpStatus.FORBIDDEN);
        }

        if(cervezaDTO.getStock() <= 0 ){
            return new ResponseEntity<>("Stock invalido.", HttpStatus.FORBIDDEN);
        }

        servicioCerveza.guardarCerveza(servicioCerveza.crearCerveza(cervezaDTO));
        return new ResponseEntity<>("La cerveza fue agregada.", HttpStatus.CREATED);
    }


    @PutMapping("/cervezas/{id}")
    public ResponseEntity<?> modificarCerveza(@PathVariable Long id, @RequestBody CervezaDTO cervezaDTO) {

        Cerveza cervezaObjetivo = servicioCerveza.buscarCervezaPorId(id);

        if(cervezaObjetivo != null){

            if(cervezaDTO.getNombre().isEmpty()){
                return new ResponseEntity<>("Nombre invalido.", HttpStatus.FORBIDDEN);
            }


            if(cervezaDTO.getAbv() < 0){
                return new ResponseEntity<>("Valor de abv invalido.", HttpStatus.FORBIDDEN);
            }


            if(cervezaDTO.getIbu() < 0){
                return new ResponseEntity<>("Valor de ibu invalido.", HttpStatus.FORBIDDEN);
            }


            if(cervezaDTO.getDescripcion().isEmpty()){
                return new ResponseEntity<>("Descripcion invalida.", HttpStatus.FORBIDDEN);
            }


            if(cervezaDTO.getPrecio() < 100){
                return new ResponseEntity<>("Precio invalido.", HttpStatus.FORBIDDEN);
            }

            List<String> categorias = List.of("APA", "IPA", "STOUT", "PORTER", "LAGER", "HONEY", "ALE");
            if(!categorias.contains(cervezaDTO.getTipoCerveza().toString())){
                return new ResponseEntity<>("Categoria invalida.", HttpStatus.FORBIDDEN);
            }

            List<String> presentaciones = List.of("LATA",  "BOTELLA");
            if(!presentaciones.contains(cervezaDTO.getPresentacion().toString())){
                return new ResponseEntity<>("Presentacion invalida.", HttpStatus.FORBIDDEN);
            }

            if(cervezaDTO.getFabricante().isEmpty()){
                return new ResponseEntity<>("Fabrica invalida.", HttpStatus.FORBIDDEN);
            }


            if(cervezaDTO.getImg().isEmpty()){
                return new ResponseEntity<>("El link de la imagen es invalido.", HttpStatus.FORBIDDEN);
            }


            if(cervezaDTO.getStock() <= 0 ){
                return new ResponseEntity<>("Stock invalido.", HttpStatus.FORBIDDEN);
            }

            cervezaObjetivo.setNombre(cervezaDTO.getNombre());
            cervezaObjetivo.setAbv(cervezaDTO.getAbv());
            cervezaObjetivo.setIbu(cervezaDTO.getIbu());
            cervezaObjetivo.setDescripcion(cervezaDTO.getDescripcion());
            cervezaObjetivo.setPrecio(cervezaDTO.getPrecio());
            cervezaObjetivo.setTipoCerveza(cervezaDTO.getTipoCerveza());
            cervezaObjetivo.setPresentacion(cervezaDTO.getPresentacion());
            cervezaObjetivo.setFabricante(cervezaDTO.getFabricante());
            cervezaObjetivo.setImg(cervezaDTO.getImg());
            cervezaObjetivo.setStock(cervezaDTO.getStock());
            servicioCerveza.guardarCerveza(cervezaObjetivo);

            return new ResponseEntity<>("La cerveza ha sido modificada exitosamente.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("El id proporcionado no corresponde a una cerveza.", HttpStatus.BAD_REQUEST);
        }
    }


    @PatchMapping("/cervezas/stock")
    public ResponseEntity<?> modificarStockDeCerveza(@RequestParam Long id, @RequestParam Integer stock){

        Cerveza cervezaObjetivo = servicioCerveza.buscarCervezaPorId(id);

        if(cervezaObjetivo != null){

            if(stock <= 0 ){
                return new ResponseEntity<>("El numero de Stock es invalido.", HttpStatus.FORBIDDEN);
            }

            cervezaObjetivo.setStock(stock);
            servicioCerveza.guardarCerveza(cervezaObjetivo);

            return new ResponseEntity<>("El stock de la cerveza ha sido modificada exitosamente.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("El id proporcionado no corresponde a una cerveza.", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("cervezas/borrar/{id}")
    public ResponseEntity<?> borrarCerveza(@PathVariable Long id){

        Cerveza cervezaObjetivo = servicioCerveza.buscarCervezaPorId(id);

        if(cervezaObjetivo != null){

            if(!cervezaObjetivo.getEstado()){
                return new ResponseEntity<>("La cerveza ya ha sido dada de baja.", HttpStatus.CONFLICT);
            }

            cervezaObjetivo.setEstado(false);
            servicioCerveza.guardarCerveza(cervezaObjetivo);

            return new ResponseEntity<>("La cerveza ha sido dada de baja.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("El id proporcionado no corresponde a una cerveza.", HttpStatus.BAD_REQUEST);
        }
    }


    //CAMBIE EL TIPO DE PETICION A PATCH
    //TODO: agregar nueva url al autorization
    @PatchMapping("cervezas/alta/{id}")
    public ResponseEntity<?> darAltaCerveza(@PathVariable Long id){

        Cerveza cervezaObjetivo = servicioCerveza.buscarCervezaPorId(id);

        if(cervezaObjetivo != null){

            if(cervezaObjetivo.getEstado()){
                return new ResponseEntity<>("La cerveza se encuentra dada de alta.", HttpStatus.CONFLICT);
            }

            cervezaObjetivo.setEstado(true);
            servicioCerveza.guardarCerveza(cervezaObjetivo);

            return new ResponseEntity<>("La cerveza ha sido dada de alta.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("El id proporcionado no corresponde a una cerveza.", HttpStatus.BAD_REQUEST);
        }
    }
}


