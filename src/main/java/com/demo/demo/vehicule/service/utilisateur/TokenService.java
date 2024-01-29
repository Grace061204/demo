package com.demo.demo.vehicule.service.utilisateur;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.demo.vehicule.model.utilisateur.Token;
import com.demo.demo.vehicule.model.utilisateur.Utilisateur;
import com.demo.demo.vehicule.repository.utilisateur.TokenRepository;
import com.demo.demo.vehicule.repository.utilisateur.UtilisateurRepository;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token createToken(Token token) {
        return tokenRepository.save(token);
    }

    public List<Token> getByToken(String token){
        return tokenRepository.findByToken(token);
    }

    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }


}
