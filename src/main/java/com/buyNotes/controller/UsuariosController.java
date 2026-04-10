package com.buyNotes.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buyNotes.dto.UsuarioDTO;
import com.buyNotes.model.Usuario;
import com.buyNotes.repository.UsuarioRepository;
import com.buyNotes.security.JwtUtil;
import com.buyNotes.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:8080/usuarios")
public class UsuariosController {

	 private final UsuarioService usuarioService;
	 

	 private final JwtUtil jwtUtil; 
	 
	 
	 @PostMapping("/registro")
	    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Usuario usuario) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            Usuario nuevoUsuario = usuarioService.createUsuario(usuario);
	            response.put("message", "Usuario registrado correctamente");
	            response.put("userId", nuevoUsuario.getId());
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            response.put("error", e.getMessage());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        } catch (Exception e) {
	            response.put("error", "Error al registrar el usuario");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
	        try {
	            String nombreUsuario = loginRequest.get("nombreUsuario");
	            String contrasena = loginRequest.get("contrasena");

	            Usuario usuario = usuarioService.login(nombreUsuario, contrasena);

	        
	            String token = jwtUtil.generateToken(usuario);

	            System.out.println("🟢 Login exitoso para: " + nombreUsuario + " → Token: " + token);

	            

	            return ResponseEntity.ok(token);

	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error en el login"));
	        }
	    }
	    
	    @PutMapping("/cambiar-contrasena")
	    public ResponseEntity<?> cambiarContrasena(
	            @RequestHeader("Authorization") String authHeader,
	            @RequestBody Map<String, String> contrasenas) {
	        try {
	            String token = authHeader.replace("Bearer ", "");
	            String nombreUsuario = jwtUtil.extractUsername(token);

	            String currentPassword = contrasenas.get("contrasenaActual");
	            String newPassword = contrasenas.get("nuevaContrasena");

	            usuarioService.changePassword(nombreUsuario, currentPassword, newPassword);

	            return ResponseEntity.ok(Map.of("message", "Contraseña actualizada correctamente"));
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al cambiar la contraseña"));
	        }
	    }
	    
	    @PutMapping("/perfil")
	    public ResponseEntity<?> updatePerfil(
	            @RequestHeader("Authorization") String authHeader,
	            @RequestBody UsuarioDTO perfilDTO) {

	        try {
	            String token = authHeader.replace("Bearer ", "");
	            String nombreUsuario = jwtUtil.extractUsername(token);

	            Usuario usuarioActualizado = usuarioService.updatePerfil(nombreUsuario, perfilDTO);

	            return ResponseEntity.ok(Map.of(
	                    "message", "Perfil actualizado correctamente",
	                    "usuario", usuarioActualizado
	            ));
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al actualizar el perfil"));
	        }
	    }
	    
	 
	 
	 
	 
	 //Ejemplo de metodo con onbtención del usuario
//	 @GetMapping()
//	    public ResponseEntity<?> LogIn (
//	    @RequestHeader("Authorization") String authHeader
//	    //meter request body
//	    ) { try {
//	 Usuario usuario = usuarioService.resolverUsuario(authHeader);
//	        return null;
//	    } catch (IllegalArgumentException e) {
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                .body(Map.of("error", "Error al crear el crédito"));
//	    }
//	}
	 
	 
	
}
