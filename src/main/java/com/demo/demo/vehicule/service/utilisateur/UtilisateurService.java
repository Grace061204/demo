package com.demo.demo.vehicule.service.utilisateur;

import com.demo.demo.vehicule.model.utilisateur.Utilisateur;
import com.demo.demo.vehicule.repository.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    public Utilisateur findByEmailAndMdp(String email, String mdp) {
        return utilisateurRepository.findByEmailAndMdp(email, mdp);
    }

    public static Claims extractClaims(String jwtToken, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
    }


    private static String generateSecretKey() {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public Map<String, Object> generateToken(Utilisateur utilisateur) {
        Date now = new Date();
        long jwtExpirationInMs = TimeUnit.MINUTES.toMillis(120);
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        String cle = generateSecretKey();
        Claims claims = Jwts.claims().setSubject(Long.toString(utilisateur.getId()));
        claims.put("type", utilisateur.getTypeUtilisateur());
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, cle)
                .compact();
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("cle", cle);
        response.put("date", now);
        response.put("expirer", expiryDate);
        return response;
    }
}
