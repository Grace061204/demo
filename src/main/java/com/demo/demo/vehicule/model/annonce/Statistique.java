package com.demo.demo.vehicule.model.annonce;

import com.demo.demo.vehicule.model.categorie.Categorie;

public class Statistique {
    Categorie categorie;
    double effectif;

    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    public double getEffectif() {
        return effectif;
    }
    public void setEffectif(double effectif) {
        double eff = (double)(((int)effectif*100)/100.0);
        this.effectif = eff;
    }
}
