package com.mindhub.cerveceria.controladores;

import com.mindhub.cerveceria.dtos.ClienteDTO;
import com.mindhub.cerveceria.entidades.Cliente;
import com.mindhub.cerveceria.entidades.ConfirmacionToken;
import com.mindhub.cerveceria.entidades.ResetPasswordToken;
import com.mindhub.cerveceria.repositorios.RepositorioConfirmacionToken;
import com.mindhub.cerveceria.repositorios.RepositorioResetPassword;
import com.mindhub.cerveceria.servicios.ServicioCliente;
import com.mindhub.cerveceria.servicios.ServicioEnvioMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorCliente {

    @Autowired
    private RepositorioConfirmacionToken repositorioConfirmacionToken;

    @Autowired
    private RepositorioResetPassword repositorioResetPassword;

    @Autowired
    private ServicioEnvioMail servicioEnvioMail;

    @Autowired
    ServicioCliente servicioCliente;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/clientes")
    public List<ClienteDTO> listarClientes(){
        return servicioCliente.listarClientesDTO();
    }


    @GetMapping("/clientes/{email}")
    public ClienteDTO mostrarCliente(@PathVariable String email){
        return servicioCliente.mostrarCliente(servicioCliente.buscarClientePorEmail(email));
    }

    @GetMapping("/clientes/autenticado")
    public ClienteDTO mostrarClienteAutenticado(Authentication auth){
        return servicioCliente.mostrarCliente(servicioCliente.buscarClientePorEmail(auth.getName()));
    }

    @PostMapping("/clientes/crear")
    public ResponseEntity<?> crearCliente( @RequestParam String email,
                                           @RequestParam String nombre,
                                           @RequestParam String apellido,
                                           @RequestParam String password,
                                           @RequestParam String fechaDeNacimiento,
                                           @RequestParam String ciudad,
                                           @RequestParam String pais,
                                           @RequestParam Integer cp){

        Cliente cliente = servicioCliente.buscarClientePorEmail(email);
        if(cliente != null){
            return  new ResponseEntity<>("El mail ingresado ya esta en uso.", HttpStatus.FORBIDDEN);
        }
        if(!email.contains("@")){
            return  new ResponseEntity<>("Mail invalido.", HttpStatus.FORBIDDEN);
        }
        if(nombre.isEmpty()){
            return  new ResponseEntity<>("Nombre invalido.", HttpStatus.FORBIDDEN);
        }
        if(apellido.isEmpty()){
            return  new ResponseEntity<>("Apellido invalido.", HttpStatus.FORBIDDEN);
        }
        if(password.isEmpty() || password.length() < 8){
            return  new ResponseEntity<>("Password invalido debe tener más de 8 carácteres.", HttpStatus.FORBIDDEN);
        }
        if(fechaDeNacimiento.isEmpty()){
            return  new ResponseEntity<>("Fecha de nacimiento invalida.", HttpStatus.FORBIDDEN);

        }

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse(fechaDeNacimiento, formatoFecha);
        Period periodo = Period.between(fechaNacimiento, LocalDate.now());

        if(periodo.getYears() < 18){
            return  new ResponseEntity<>("No tiene la edad suficiente para registrarte.", HttpStatus.FORBIDDEN);
        }
        if(ciudad.isEmpty()){
            return  new ResponseEntity<>("Ciudad invalida.", HttpStatus.FORBIDDEN);
        }
        if(pais.isEmpty()){
            return  new ResponseEntity<>("Pais invalido.", HttpStatus.FORBIDDEN);
        }
        if(cp == 0){
            return  new ResponseEntity<>("Codigo postal invalido.", HttpStatus.FORBIDDEN);
        }

        Cliente clienteNuevo = new Cliente( email,
                nombre,
                apellido,
                passwordEncoder.encode(password),
                fechaNacimiento,
                ciudad,
                pais,
                cp);

        servicioCliente.guardarCliente(clienteNuevo);


        SimpleMailMessage mensajeEmailSimple = new SimpleMailMessage();
        mensajeEmailSimple.setSubject("Registro completo");
        ConfirmacionToken confirmacionToken = new ConfirmacionToken(clienteNuevo);
        repositorioConfirmacionToken.save(confirmacionToken);

        mensajeEmailSimple.setTo(clienteNuevo.getEmail());
        mensajeEmailSimple.setText("Para confirmar tu cuenta por favor, has clic en el siguiente enlace: "
                +"/confirmar-cuenta.html?token="+confirmacionToken.getConfirmationToken());

        servicioEnvioMail.enviarEmail(mensajeEmailSimple);

        return new ResponseEntity<>("La cuenta ha sido creada con exito.", HttpStatus.CREATED);
    }

    @PostMapping("/clientes/confirmar-cuenta")
    public ResponseEntity<Object> confirmarCuenta(@RequestParam("token") String confirmacionToken){

        ConfirmacionToken token = repositorioConfirmacionToken.findByConfirmationToken(confirmacionToken);

        //REVISAR ESTO

        LocalDateTime hoy = LocalDateTime.now().minusHours(24);

        if (token != null || hoy.isAfter(token.getFechaDeCreacion())){

            Cliente cliente = servicioCliente.buscarClientePorEmail(token.getCliente().getEmail());
            cliente.setEstaActivado(true);
            servicioCliente.guardarCliente(cliente);

        }else{

            ConfirmacionToken nuevoToken = new ConfirmacionToken(token.getCliente());
            repositorioConfirmacionToken.save(nuevoToken);

            SimpleMailMessage mensajeEmailSimple = new SimpleMailMessage();
            mensajeEmailSimple.setTo(token.getCliente().getEmail());
            mensajeEmailSimple.setSubject("Registro completo");
            mensajeEmailSimple.setText("Para confirmar tu cuenta, por favor has click en el siguiente enlace: "
                    +"/confirmar-cuenta.html?token="+nuevoToken.getConfirmationToken());

            servicioEnvioMail.enviarEmail(mensajeEmailSimple);

            return new ResponseEntity<>("Enlace inválido o el token ha expirado, te hemos enviado un nuevo enlace :D",HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Cuenta activada",HttpStatus.ACCEPTED);
    }


    @PostMapping("/clientes/password-token")
    public ResponseEntity<Object> enviarTokenReinicioContrasena(@RequestParam String email){

        Cliente cliente = servicioCliente.buscarClientePorEmail(email);
        if(cliente == null){

            new ResponseEntity<>("Este email no existe", HttpStatus.FORBIDDEN);
        }
        servicioCliente.guardarCliente(cliente);

        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(cliente);
        repositorioResetPassword.save(resetPasswordToken);

        SimpleMailMessage mensajeEmail = new SimpleMailMessage();

        mensajeEmail.setTo(cliente.getEmail());
        mensajeEmail.setSubject("Cambiar contraseña");
        mensajeEmail.setText("Para cambiar tu contraseña has click en el siguiente enlace: "+"/reiniciar-contrasena.html?token="+resetPasswordToken.getResetPasswordToken());
        servicioEnvioMail.enviarEmail(mensajeEmail);

        return new ResponseEntity<>("Token enviado", HttpStatus.ACCEPTED);
    }


    @PostMapping("/clientes/reiniciar-contrasena")
    public ResponseEntity<Object> reiniciarContrasena(@RequestParam("token")String resetPasswordToken, @RequestParam String password){

        ResetPasswordToken token = repositorioResetPassword.findByresetPasswordToken(resetPasswordToken);

        if(token != null){

            if (password.length() < 8){
                return new ResponseEntity<>("La contraseña no puede ser menor a 8 carácteres", HttpStatus.LENGTH_REQUIRED);
            }

            Cliente cliente = servicioCliente.buscarClientePorEmail(token.getCliente().getEmail());
            cliente.setPassword(passwordEncoder.encode(password));
            servicioCliente.guardarCliente(cliente);

        }else{
            return new ResponseEntity<>("El token no es válido", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Contraseña cambiada", HttpStatus.CREATED);
    }

}
