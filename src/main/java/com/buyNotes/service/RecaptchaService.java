package com.buyNotes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecaptchaService {

    @Value("${recaptcha.secret:}")
    private String secret;

    @Value("${recaptcha.url:https://www.google.com/recaptcha/api/siteverify}")
    private String url;

    /** Verifica el token contra la API de Google. Devuelve true si es válido. */
    public boolean verificar(String token) {
        if (token == null || token.isBlank()) {
            System.err.println("[reCAPTCHA] Token vacío o null");
            return false;
        }
        if (secret == null || secret.isBlank()) {
            System.err.println("[reCAPTCHA] secret no configurado en el servidor (RECAPTCHASECRET)");
            return true; // fallback en dev
        }

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("secret", secret);
        form.add("response", token);

        try {
            @SuppressWarnings("rawtypes")
            Map resp = rt.postForObject(url, new HttpEntity<>(form, headers), Map.class);
            if (resp == null) {
                System.err.println("[reCAPTCHA] Respuesta nula de Google");
                return false;
            }
            boolean success = Boolean.TRUE.equals(resp.get("success"));
            if (!success) {
                // Loggea las razones del fallo (codes oficiales de Google)
                Object errorCodes = resp.get("error-codes");
                System.err.println("[reCAPTCHA] FALLO. success=false  errorCodes=" + errorCodes
                        + "  hostname=" + resp.get("hostname")
                        + "  challenge_ts=" + resp.get("challenge_ts"));
            } else {
                System.out.println("[reCAPTCHA] OK. hostname=" + resp.get("hostname"));
            }
            return success;
        } catch (Exception e) {
            System.err.println("[reCAPTCHA] Excepción al llamar a Google: " + e.getMessage());
            return false;
        }
    }
}
