/*
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

    */
/*

    public void enviar(String to, String asunto, String cuerpo) {
        enviarInterno(to, asunto, cuerpo, false);
    }

    */
/*

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
*/

package com.buyNotes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${mail.from}")
    private String fromEmail;

    @Value("${mail.from-name:BuyNotes}")
    private String fromName;

    public void enviar(String to, String asunto, String cuerpo) {
        enviarInterno(to, asunto, null, cuerpo);
    }

    public void enviarHtml(String to, String asunto, String cuerpoHtml) {
        enviarInterno(to, asunto, cuerpoHtml, null);
    }

    private void enviarInterno(String to, String asunto, String htmlContent, String textContent) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);
        headers.set("accept", "application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("sender", Map.of("name", fromName, "email", fromEmail));
        body.put("to", List.of(Map.of("email", to)));
        body.put("subject", asunto);

        if (htmlContent != null) {
            body.put("htmlContent", htmlContent);
        }
        if (textContent != null) {
            body.put("textContent", textContent);
        }

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(url, request, String.class);
            System.out.println("Correo enviado con éxito a través de la API a: " + to);
        } catch (Exception e) {
            System.err.println("Error enviando email por API a " + to + ": " + e.getMessage());
        }
    }
}
