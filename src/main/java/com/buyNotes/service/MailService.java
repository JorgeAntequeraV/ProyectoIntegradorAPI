package com.buyNotes.service;

import org.springframework.stereotype.Service;

@Service
public class MailService {

    //Sustituir metodo  por llamada a la api de brevo, por ahora solo es por consola
    public void enviar(String to, String asunto, String cuerpo) {
        System.out.println("=========== EMAIL (stub) ===========");
        System.out.println("Para:    " + to);
        System.out.println("Asunto:  " + asunto);
        System.out.println("Cuerpo:");
        System.out.println(cuerpo);
        System.out.println("====================================");
    }
}
