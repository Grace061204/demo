package com.demo.demo.vehicule.repository.utilisateur;

import com.demo.demo.vehicule.model.utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmailAndMdp(String email, String mdp);
}
