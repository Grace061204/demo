package com.demo.demo.vehicule.service.utilisateur;

import com.demo.demo.vehicule.model.utilisateur.Utilisateur;
import com.demo.demo.vehicule.repository.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur updateUtilisateur(Long id, Utilisateur newUtilisateur) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);
        if (optionalUtilisateur.isPresent()) {
            Utilisateur existingUtilisateur = optionalUtilisateur.get();
            existingUtilisateur.setNom(newUtilisateur.getNom());
            existingUtilisateur.setDateNaissance(newUtilisateur.getDateNaissance());
            existingUtilisateur.setEmail(newUtilisateur.getEmail());
            existingUtilisateur.setMdp(newUtilisateur.getMdp());
            existingUtilisateur.setTypeUtilisateur(newUtilisateur.getTypeUtilisateur());
            return utilisateurRepository.save(existingUtilisateur);
        } else {
            return null;
        }
    }

    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    public Optional<Utilisateur> findByEmailAndMdp(String email, String mdp) {
        return utilisateurRepository.findByEmailAndMdp(email, mdp);
    }
}
