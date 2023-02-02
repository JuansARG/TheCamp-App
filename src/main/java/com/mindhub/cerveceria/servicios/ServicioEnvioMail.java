package com.mindhub.cerveceria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("servicioEnvioMail")
public class ServicioEnvioMail {

    private JavaMailSender javaMailSender;

    @Autowired
    public ServicioEnvioMail(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }


    @Async
    public void enviarEmail(SimpleMailMessage email){
        javaMailSender.send(email);
    }

}
