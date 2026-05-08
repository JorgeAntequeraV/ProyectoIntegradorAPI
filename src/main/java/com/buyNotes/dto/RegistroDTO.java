package com.buyNotes.dto;

import com.buyNotes.model.Usuario;
import lombok.Data;

@Data
public class RegistroDTO {
    private Usuario usuario;
    private String recaptchaToken;
}
