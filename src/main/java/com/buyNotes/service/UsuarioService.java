package com.buyNotes.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.buyNotes.dto.UsuarioDTO;
import com.buyNotes.model.Usuario;
import com.buyNotes.model.enums.Rol;
import com.buyNotes.repository.UsuarioRepository;
import com.buyNotes.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	
	private static final char[] alfabeto = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();
	
	private final UsuarioRepository usuarioRepo;
	
	private final JwtUtil jwtUtil;
    
    private final BCryptPasswordEncoder passwordEncoder;
	
	
	public Usuario createUsuario(Usuario usuario) {

        // Validar nombreUsuario único
        Usuario existente = usuarioRepo.getUsuarioByNombreUsuario(usuario.getNombreUsuario());
        if (existente != null) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }

        // Validar email único
        Usuario emailExistente = usuarioRepo.getUsuarioByEmail(usuario.getEmail());
        if (emailExistente!= null) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        if (usuario.getRol() == null) {
            usuario.setRol(Rol.USER);
        }
        usuario.setTagAmigo(generarTagUnico());
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));


        Usuario nuevo = usuarioRepo.save(usuario);

        try {
            String toName = nuevo.getNombreUsuario() != null
                    ? nuevo.getNombreUsuario()
                    : nuevo.getNombre();

           
        } catch (Exception e) {
            // Opcional: loggear pero no romper el registro
            System.out.println(" Error enviando mail de bienvenida: " + e.getMessage());
        }

        return nuevo;
    }
	
	public Usuario login(String nombreUsuario, String contrasena) {
        Usuario usuarioOpt = usuarioRepo.getUsuarioByNombreUsuario(nombreUsuario);

        if (usuarioOpt != null) {
            Usuario usuario = usuarioOpt;

            if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                return usuario;
            } else {
                throw new IllegalArgumentException("Contraseña incorrecta.");
            }
        } else {
            throw new IllegalArgumentException("Usuario no encontrado.");
        }
    }
	
	//Método para generar un tag para añadir usuarios
	public String generarTagUnico() {
	    String nuevoTag;
		do {
	        nuevoTag = NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR,alfabeto, 8); // Genera uno de 8
	    } while (usuarioRepo.existsByTagAmigo(nuevoTag)); // Reintenta si ya existe
	    return nuevoTag;
	}
	
	 public Usuario resolverUsuario(String authHeader) {
	        String token = authHeader.replace("Bearer ", "");
	        String nombreUsuario = jwtUtil.extractUsername(token);
	        
	        Usuario usuario = usuarioRepo.getUsuarioByNombreUsuario(nombreUsuario);
	        
	        return usuario;
	    }
	 
	 public void changePassword(String nombreUsuario, String currentPassword, String newPassword) {
	        Usuario usuario = usuarioRepo.getUsuarioByNombreUsuario(nombreUsuario);

	        if (usuario == null) {
	            throw new IllegalArgumentException("Usuario no encontrado.");
	        }

	       

	        // Verificamos la contraseña actual
	        if (!passwordEncoder.matches(currentPassword, usuario.getContrasena())) {
	            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
	        }

	        // Establecemos la nueva contraseña encriptada
	        usuario.setContrasena(passwordEncoder.encode(newPassword));
	        usuarioRepo.save(usuario);
	    }
	 
	 public Usuario updatePerfil(String nombreUsuario, UsuarioDTO perfilDTO) {
	        Usuario usuarioOpt = usuarioRepo.getUsuarioByNombreUsuario(nombreUsuario);
	        if (usuarioOpt == null) {
	            throw new IllegalArgumentException("Usuario no encontrado.");
	        }

	        Usuario usuario = usuarioOpt;

	        // VALIDAR EMAIL ÚNICO
	        if (!perfilDTO.getEmail().equals(usuario.getEmail())) {
	            Usuario emailExistente = usuarioRepo.getUsuarioByEmail(perfilDTO.getEmail());
	            if (emailExistente != null) {
	                throw new IllegalArgumentException("El email ya está registrado por otro usuario.");
	            }
	        }

	        usuario.setNombre(perfilDTO.getNombre());
	        usuario.setEmail(perfilDTO.getEmail());
	        usuario.setTelefono(perfilDTO.getTelefono());

	       

	        return usuarioRepo.save(usuario);
	    }


}
