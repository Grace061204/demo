package com.demo.demo.vehicule.controller.utilisateur;

import com.demo.demo.api.APIResponse;
import com.demo.demo.vehicule.model.utilisateur.Utilisateur;
import com.demo.demo.vehicule.service.utilisateur.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs")
@CrossOrigin("*")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/")
    public ResponseEntity<APIResponse> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur createdUtilisateur = utilisateurService.createUtilisateur(utilisateur);
            return ResponseEntity.ok(new APIResponse("Utilisateur créé avec succès", createdUtilisateur));
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la création de l'utilisateur: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<APIResponse> getAllUtilisateurs() {
        try {
            List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
            return ResponseEntity.ok(new APIResponse("", utilisateurs));
        } catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la récupération de tous les utilisateurs: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getUtilisateurById(@PathVariable Long id) {
        try {
            Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);
            if (utilisateur.isPresent()) {
                return ResponseEntity.ok(new APIResponse("", utilisateur.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e) {
            APIResponse response = new APIResponse("Erreur lors de la récupération de tous les utilisateurs: " + e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
       
