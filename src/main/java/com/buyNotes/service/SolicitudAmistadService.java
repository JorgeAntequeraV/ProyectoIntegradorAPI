package com.buyNotes.service;

import com.buyNotes.model.SolicitudAmistad;
import com.buyNotes.model.Usuario;
import com.buyNotes.repository.SolicitudAmistadRepository;
import com.buyNotes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudAmistadService {

    private final SolicitudAmistadRepository solicitudRepo;
    private final UsuarioRepository usuarioRepo;

    @Transactional
    public SolicitudAmistad enviar(Long userIdRemitente, String tagDestinatario) {
        Usuario remitente = usuarioRepo.getUsuarioById(userIdRemitente);
        if (remitente == null) throw new IllegalArgumentException("Usuario no encontrado");

        Usuario destinatario = usuarioRepo.findAll().stream()
                .filter(u -> tagDestinatario.equalsIgnoreCase(u.getTagAmigo()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No existe usuario con ese tag"));

        if (destinatario.getId().equals(remitente.getId()))
            throw new IllegalArgumentException("No puedes enviarte solicitud a ti mismo");

        if (remitente.getAmigos().stream().anyMatch(a -> a.getId().equals(destinatario.getId())))
            throw new IllegalArgumentException("Ya sois amigos");

        if (solicitudRepo.existsByRemitenteAndDestinatario(remitente, destinatario))
            throw new IllegalArgumentException("Ya existe una solicitud pendiente");

        SolicitudAmistad s = new SolicitudAmistad();
        s.setRemitente(remitente);
        s.setDestinatario(destinatario);
        return solicitudRepo.save(s);
    }

    @Transactional
    public List<SolicitudAmistad> listarPendientes(Long userId) {
        Usuario u = usuarioRepo.getUsuarioById(userId);
        return solicitudRepo.findByDestinatario(u);
    }

    @Transactional
    public void aceptar(Long userId, Long solicitudId) {
        SolicitudAmistad s = solicitudRepo.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        if (!s.getDestinatario().getId().equals(userId))
            throw new RuntimeException("Esa solicitud no es tuya");

        Usuario u1 = s.getRemitente();
        Usuario u2 = s.getDestinatario();
        if (u1.getAmigos().stream().noneMatch(a -> a.getId().equals(u2.getId())))
            u1.getAmigos().add(u2);
        if (u2.getAmigos().stream().noneMatch(a -> a.getId().equals(u1.getId())))
            u2.getAmigos().add(u1);
        usuarioRepo.save(u1);
        usuarioRepo.save(u2);
        solicitudRepo.delete(s);
    }

    @Transactional
    public void rechazar(Long userId, Long solicitudId) {
        SolicitudAmistad s = solicitudRepo.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        if (!s.getDestinatario().getId().equals(userId))
            throw new RuntimeException("Esa solicitud no es tuya");
        solicitudRepo.delete(s);
    }

    @Transactional
    public List<Usuario> listarAmigos(Long userId) {
        Usuario u = usuarioRepo.getUsuarioById(userId);
        return u.getAmigos();
    }

    @Transactional
    public void eliminarAmigo(Long userId, Long idAmigo) {
        Usuario u = usuarioRepo.getUsuarioById(userId);
        Usuario amigo = usuarioRepo.getUsuarioById(idAmigo);
        if (amigo == null) throw new IllegalArgumentException("Amigo no encontrado");

        u.getAmigos().removeIf(a -> a.getId().equals(idAmigo));
        amigo.getAmigos().removeIf(a -> a.getId().equals(userId));
        usuarioRepo.save(u);
        usuarioRepo.save(amigo);
    }
}
