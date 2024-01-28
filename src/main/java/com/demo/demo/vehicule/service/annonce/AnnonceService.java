package com.demo.demo.vehicule.service.annonce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.demo.vehicule.model.annonce.Annonce;
import com.demo.demo.vehicule.repository.annonce.AnnonceRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AnnonceService {
    @Autowired
    private AnnonceRepository annonceRepository;

    public List<Annonce> getAnnoncesNonVendues() {
        return annonceRepository.findByStatusAndEstValide(0, 1);
    }

    public List<Annonce> getAnnoncesByProprietaire(int propietaire) {
        return annonceRepository.findByIdproprietaire(propietaire);
    }

    public Annonce insertAnnonce(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    public void update(String id, Annonce updatedAnnonce) {
        Annonce existingAnnonce = annonceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Annonce non trouvée avec l'ID : " + id));

        existingAnnonce.setTitre(updatedAnnonce.getTitre());
        existingAnnonce.setDescription(updatedAnnonce.getDescription());
        existingAnnonce.setCategorie(updatedAnnonce.getCategorie());
        existingAnnonce.setMarque(updatedAnnonce.getMarque());
        existingAnnonce.setPrix(updatedAnnonce.getPrix());
        existingAnnonce.setDateAjout(updatedAnnonce.getDateAjout());
        existingAnnonce.setDateVente(updatedAnnonce.getDateVente());
        existingAnnonce.setStatus(updatedAnnonce.getStatus());
        existingAnnonce.setImage(updatedAnnonce.getImage());
        existingAnnonce.setIdproprietaire(updatedAnnonce.getIdproprietaire());
        existingAnnonce.setFavoris(updatedAnnonce.getFavoris());
        existingAnnonce.setEstValide(updatedAnnonce.getEstValide());

        annonceRepository.save(existingAnnonce);
    }

    public Annonce findById(String id) {
        Optional<Annonce> optionalAnnonce = annonceRepository.findById(id);

        if (optionalAnnonce.isPresent()) {
            return optionalAnnonce.get();
        } else {
            throw new EntityNotFoundException("Annonce non trouvée avec l'ID : " + id);
        }
    }
}

