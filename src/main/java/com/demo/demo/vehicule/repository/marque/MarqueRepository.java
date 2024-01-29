package com.demo.demo.vehicule.repository.marque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.demo.vehicule.model.categorie.Categorie;
import com.demo.demo.vehicule.model.marque.Marque;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Integer>{

    
} 