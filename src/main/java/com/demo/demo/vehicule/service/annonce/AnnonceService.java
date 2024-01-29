package com.demo.demo.vehicule.service.annonce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.demo.vehicule.model.annonce.Annonce;
import com.demo.demo.vehicule.model.annonce.Statistique;
import com.demo.demo.vehicule.model.categorie.Categorie;
import com.demo.demo.vehicule.repository.annonce.AnnonceRepository;
import com.demo.demo.vehicule.repository.categorie.CategorieRepository;
import com.demo.demo.vehicule.service.categorie.CategorieService;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AnnonceService {
    @Autowired
    private AnnonceRepository annonceRepository;


    @Autowired
    private CategorieRepository categorieRepository;
    public List<Annonce> getAnnoncesNonVendues() {
        return annonceRepository.findByStatusAndEstValide(0, 1);
    }

    public List<Annonce> getAnnoncesByProprietaire(int propietaire) {
        return annonceRepository.findByProp(propietaire);
    }


    public List<Annonce> getAnnoncesNonValide() {
        return annonceRepository.findByEstValide(0);
    }

    public List<Annonce> getAnnoncesValide() {
        return annonceRepository.findByEstValide(1);
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
        existingAnnonce.setProp(updatedAnnonce.getProp());
        existingAnnonce.setFavoris(updatedAnnonce.getFavoris());
        existingAnnonce.setEstValide(updatedAnnonce.getEstValide());

        annonceRepository.save(existingAnnonce);
    }

    public List<Statistique> getAllStatistique()throws Exception{
        List<Categorie> listCateg = categorieRepository.findAll();
        List<Statistique> listStatistique = new ArrayList<>();
        List<Annonce> annonceByCateg = new ArrayList<>();
        double countAnnonceByCategorie = 0;
        double countAnnonceValide = this.getAnnoncesValide().size();
        for (int i = 0; i < listCateg.size(); i++) {
            Statistique statistique = new Statistique();
            annonceByCateg = annonceRepository.findByCategorie(listCateg.get(i).getId().toString());
            countAnnonceByCategorie = annonceByCateg.size();
            statistique.setCategorie(listCateg.get(i));
            statistique.setEffectif((countAnnonceByCategorie*100)/countAnnonceValide);
            listStatistique.add(statistique);
        }

        return listStatistique;
    }

    public Annonce findById(String id) throws Exception{
        Optional<Annonce> optionalAnnonce = annonceRepository.findById(id);

        if (optionalAnnonce.isPresent()) {
            return optionalAnnonce.get();
        } else {
            throw new EntityNotFoundException("Annonce non trouvée avec l'ID : " + id);
        }
    }
}

