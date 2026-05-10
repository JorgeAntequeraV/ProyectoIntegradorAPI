package com.buyNotes.service;

import com.buyNotes.model.PorDefecto;
import com.buyNotes.repository.PorDefectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PorDefectoService {

    private final PorDefectoRepository porDefectoRepo;

    public PorDefecto getById(Long id){
        return porDefectoRepo.getById(id);
    }
    public PorDefecto findTopByOrderByIdAsc(){
        return porDefectoRepo.findTopByOrderByIdAsc();
    }
    public  void save(PorDefecto d){
        porDefectoRepo.save(d);
    }
}
