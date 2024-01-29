package com.demo.demo.vehicule.repository.utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.demo.vehicule.model.utilisateur.Token;
import java.util.List;


@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>{
    List<Token> findByToken(String token);
}
