package com.example.xmen_firebase;

import androidx.annotation.DrawableRes;

import java.util.HashMap;
import java.util.Map;

public class XMen {
    private String key;
    // Variables représentant les informations
    private String nom;
    private String alias;
    private String description;
    private String pouvoirs;

    private String idImage;

    // Constructeur avec paramètres
    public XMen(String nom, String alias, String description, String pouvoirs,  String idImage) {
        this.nom = nom;
        this.alias = alias;
        this.description = description;
        this.pouvoirs = pouvoirs;
        this.idImage = idImage;
        this.key = key;
    }

    // Constructeur sans paramètre
    public XMen() {
        nom = "inconnu";
        alias = "inconnu";
        description = "inconnue";
        pouvoirs = "inconnus";
        this.idImage = "https://firebasestorage.googleapis.com/v0/b/x-men-44819.appspot.com/o/undef.png?alt=media"; ;
    }

    // Getters et setters pour les champs manquants
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPouvoirs() {
        return pouvoirs;
    }

    public void setPouvoirs(String pouvoirs) {
        this.pouvoirs = pouvoirs;
    }


    public String getIdImage() {
        return idImage;
    }

    public void setIdImage( String idImage) {
        this.idImage = idImage;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("nom", nom);
        map.put("alias", alias);
        map.put("description", description);
        map.put("pouvoirs", pouvoirs);
        map.put("idImage", idImage);
        return map;
    }
}
