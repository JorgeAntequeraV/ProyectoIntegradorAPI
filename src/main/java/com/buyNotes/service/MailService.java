package com.buyNotes.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String fromEmail;

    @Value("${mail.from-name:BuyNotes}")
    private String fromName;

    /** Envía un email en texto plano. */
    public void enviar(String to, String asunto, String cuerpo) {
        enviarInterno(to, asunto, cuerpo, false);
    }

    /** Envía un email en formato HTML (los enlaces &lt;a&gt; son pulsables). */
    public void enviarHtml(String to, String asunto, String cuerpoHtml) {
        enviarInterno(to, asunto, cuerpoHtml, true);
    }

    private void enviarInterno(String to, String asunto, String cuerpo, boolean html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name());
            helper.setFrom(new InternetAddress(fromEmail, fromName));
            helper.setTo(to);
            helper.setSubject(asunto);
            helper.setText(cuerpo, html);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println("Error enviando email a " + to + ": " + e.getMessage());
        }
    }
}
