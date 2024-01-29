package com.demo.demo.vehicule.service.marque;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.demo.vehicule.model.marque.Marque;
import com.demo.demo.vehicule.repository.marque.MarqueRepository;

@Service
public class MarqueService {
    private final MarqueRepository marqueRepository;

    @Autowired
    public MarqueService(MarqueRepository marqueRepository) {
        this.marqueRepository = marqueRepository;
    }

    public Marque createMarque(Marque marque) {
        return marqueRepository.save(marque);
    }

    public List<Marque> getAllMarques() {
        return marqueRepository.findAll();
    }

    public Optional<Marque> getMarqueById(int id) {
        return marqueRepository.findById(id);
    }

    public Marque updateMarque(int id, Marque newMarque) {
        Optional<Marque> optionalMarque = marqueRepository.findById(id);
        if (optionalMarque.isPresent()) {
            Marque existingMarque = optionalMarque.get();
            existingMarque.setNom(newMarque.getNom());
            return marqueRepository.save(existingMarque);
        } else {
            return null;
        }
    }

    public void deleteMarque(int id) {
        marqueRepository.deleteById(id);
    }
}
