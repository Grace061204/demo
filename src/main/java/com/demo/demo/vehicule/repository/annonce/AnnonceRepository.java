package com.demo.demo.vehicule.repository.annonce;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.demo.vehicule.model.annonce.Annonce;

public interface AnnonceRepository extends MongoRepository<Annonce, String> {
    List<Annonce> findByStatusAndEstValide(int status, int estValide);
    List<Annonce> findByEstValide(int estValide);

    List<Annonce> findByIdproprietaire(int idproprietaire);
    Optional<Annonce> findById(String id);
}