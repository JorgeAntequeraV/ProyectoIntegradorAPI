package com.buyNotes.service;

import com.buyNotes.model.InvitacionLista;
import com.buyNotes.model.Listas;
import com.buyNotes.model.Usuario;
import com.buyNotes.repository.InvitacionListaRepository;
import com.buyNotes.repository.ListasRepository;
import com.buyNotes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitacionListaService {

    private final InvitacionListaRepository invitacionRepo;
    private final UsuarioRepository usuarioRepo;
    private final ListasRepository listasRepo;

    @Transactional
    public InvitacionLista invitar(Long userIdAnfitrion, Long listaId, String tagInvitado) {
        Usuario anfitrion = usuarioRepo.getUsuarioById(userIdAnfitrion);
        if (anfitrion == null) throw new IllegalArgumentException("Anfitrion no encontrado");

        Listas lista = listasRepo.findById(listaId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada"));

        if (lista.getUsuariosConAcceso().stream().noneMatch(u -> u.getId().equals(userIdAnfitrion)))
            throw new RuntimeException("No tienes acceso a esta lista");

        Usuario invitado = usuarioRepo.findAll().stream()
                .filter(u -> tagInvitado.equalsIgnoreCase(u.getTagAmigo()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No existe ningún usuario con ese tag"));

        if (invitado.getId().equals(anfitrion.getId()))
            throw new IllegalArgumentException("No puedes invitarte a ti mismo");

        if (lista.getUsuariosConAcceso().stream().anyMatch(u -> u.getId().equals(invitado.getId())))
            throw new IllegalArgumentException("Ese usuario ya tiene acceso a la lista");

        if (invitacionRepo.existsByListaAndInvitado(lista, invitado))
            throw new IllegalArgumentException("Ya hay una invitación pendiente para ese usuario");

        InvitacionLista inv = new InvitacionLista();
        inv.setLista(lista);
        inv.setInvitado(invitado);
        inv.setAnfitrion(anfitrion);
        return invitacionRepo.save(inv);
    }

    @Transactional
    public List<InvitacionLista> listarPendientes(Long userId) {
        Usuario u = usuarioRepo.getUsuarioById(userId);
        return invitacionRepo.findByInvitado(u);
    }

    @Transactional
    public void aceptar(Long userId, Long invitacionId) {
        InvitacionLista inv = invitacionRepo.findById(invitacionId)
                .orElseThrow(() -> new IllegalArgumentException("Invitación no encontrada"));
        if (!inv.getInvitado().getId().equals(userId))
            throw new RuntimeException("Esta invitación no es para ti");

        Listas lista = inv.getLista();
        lista.getUsuariosConAcceso().add(inv.getInvitado());
        listasRepo.save(lista);
        invitacionRepo.delete(inv);
    }

    @Transactional
    public void rechazar(Long userId, Long invitacionId) {
        InvitacionLista inv = invitacionRepo.findById(invitacionId)
                .orElseThrow(() -> new IllegalArgumentException("Invitación no encontrada"));
        if (!inv.getInvitado().getId().equals(userId))
            throw new RuntimeException("Esta invitación no es para ti");
        invitacionRepo.delete(inv);
    }
}
