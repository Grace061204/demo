package com.demo.demo.vehicule.repository.utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.demo.vehicule.model.utilisateur.Token;
import java.util.List;


@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>{
    // List<Token> findByToken(String token);
    void deleteByToken(String token);
    @Query(value = "SELECT * FROM token WHERE token=:token",nativeQuery = true)
    Token getTokenByToken(@Param("token") String token);
}
