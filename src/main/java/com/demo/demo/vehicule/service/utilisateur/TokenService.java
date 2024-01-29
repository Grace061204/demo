package com.demo.demo.vehicule.service.utilisateur;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.demo.vehicule.model.utilisateur.Token;
import com.demo.demo.vehicule.repository.utilisateur.TokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;

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

    public Token getByToken(String token){
        return tokenRepository.getTokenByToken(token);
    }

    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }

    
    public void deleteTokenByToken(Token token){
        tokenRepository.deleteByToken(token.getToken());
    }
     public Claims extractClaims(Token token) {
        return Jwts.parser().setSigningKey(token.getCle()).parseClaimsJws(token.getToken()).getBody();
    }

    public Claims getClaims(Token token) throws Exception  {
        try {
            return this.extractClaims(token);
        } catch (Exception e) {
            e.printStackTrace();
            this.deleteTokenByToken(token);
            throw new Exception("Session expirée. Veuillez vous reconnecter .");
        }
    }

    @Transactional
    public int verifAuth(String token) throws Exception{
        String parseToken = token.replace("Bearer ","");
        Token listToken = this.getByToken(parseToken);

        if(listToken==null){
            throw new Exception("Session expirée. Veuillez vous reconnecter .");
        }
        Claims claims = this.getClaims(listToken);
        int id = claims.get("iduser", Integer.class);
        return id;
    }
}
