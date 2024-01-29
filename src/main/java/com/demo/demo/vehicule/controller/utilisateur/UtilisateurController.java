package com.demo.demo.vehicule.controller.utilisateur;

import com.demo.demo.api.APIResponse;
import com.demo.demo.vehicule.model.utilisateur.Token;
import com.demo.demo.vehicule.model.utilisateur.Utilisateur;
import com.demo.demo.vehicule.service.utilisateur.TokenService;
import com.demo.demo.vehicule.service.utilisateur.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs")
@CrossOrigin("*")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final TokenService tokenService;


    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService,TokenService tokenService) {
        this.utilisateurService = utilisateurService;
        this.tokenService = tokenService;
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

    @PostMapping(value="/login", consumes="application/json", produces="application/json")
    public ResponseEntity<?> login(@RequestBody Utilisateur utilisateur) {
    try {
        Utilisateur verif = utilisateurService.findByEmailAndMdp(utilisateur.getEmail(), utilisateur.getMdp());

         if (verif != null) {
                Map<String, Object> map = utilisateurService.generateToken(verif);
                
                // Connection connect = new Connect().getConnection();
                Token token = new Token();
                token.setCle((String) map.get("cle"));
                token.setToken((String) map.get("token"));
                token.setDatecreation(Date.valueOf(map.get("date").toString()));
                token.setDateexpiration(Date.valueOf(map.get("expirer").toString()));

                
                tokenService.createToken(token);

                APIResponse api = new APIResponse("Authentication successful", token.getToken());
                api.setStatus_code("200");
                
                return ResponseEntity.ok(api);

        }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIResponse("Authentication failed", false));
    } catch (Exception e) { 
        APIResponse response = new APIResponse("Internal Server Error: " + e.getMessage(), false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
    try {
        // Utilisateur verif = utilisateurService.findByEmailAndMdp(utilisateur.getEmail(), utilisateur.getMdp());

                // Map<String, Object> map = utilisateurService.generateToken(verif);
                
                Token listTokens = tokenService.getByToken(token);
                if(listTokens!=null){
                    tokenService.deleteToken(listTokens);
                    return ResponseEntity.ok(new APIResponse("logout success", true));

                }
                else{

                    return ResponseEntity.ok(new APIResponse("token undefined", true));
                }

               
        } catch (Exception e) { 
            APIResponse response = new APIResponse("Internal Server Error: " + e.getMessage(), false);
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
       
