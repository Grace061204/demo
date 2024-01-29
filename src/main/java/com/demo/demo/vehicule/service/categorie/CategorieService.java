package com.demo.demo.vehicule.service.categorie;

import com.demo.demo.vehicule.model.categorie.Categorie;
import com.demo.demo.vehicule.repository.categorie.CategorieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public Categorie createCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Optional<Categorie> getCategorieById(int id) {
        return categorieRepository.findById(id);
    }


    // public 

    public Categorie updateCategorie(int id, Categorie newCategorie) {
        Optional<Categorie> optionalCategorie = categorieRepository.findById(id);
        if (optionalCategorie.isPresent()) {
            Categorie existingCategorie = optionalCategorie.get();
            existingCategorie.setNom(newCategorie.getNom());
            return categorieRepository.save(existingCategorie);
        } else {
            return null;
        }
    }



    public void deleteCategorie(int id) {
        categorieRepository.deleteById(id);
    }
}
