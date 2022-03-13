package com.example.mijotons.classBaseDonnees;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class recette {
    private int idRecette;
    private String nom;
    private String[] aliment;
    private String[] quantite;
    private String image;
    private String description;
    private String temps;
    private int nbrPersonne;

    public recette(int id,String n,JSONArray a,JSONArray q,String i,String d,String t,int personne) throws JSONException {
        idRecette = id;
        nom = n;
        ArrayList<String> exampleList = new ArrayList<>();
        for (int j = 0; j < a.length(); j++) {
            exampleList.add(a.getString(j));
        }
        aliment = exampleList.toArray(new String[exampleList.size()]);
        exampleList.clear();
        for (int j = 0; j < q.length(); j++) {
            exampleList.add(q.getString(j));
        }
        quantite = exampleList.toArray(new String[exampleList.size()]);
        image = i;
        description = d;
        temps=t;
        nbrPersonne=personne;
    }



    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public int getNbrPersonne() {
        return nbrPersonne;
    }

    public void setNbrPersonne(int nbrPersonne) {
        this.nbrPersonne = nbrPersonne;
    }

    public int getIdRecette() {
        return idRecette;
    }

    public void setIdRecette(int idRecette) {
        this.idRecette = idRecette;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String[] getAliment() {
        return aliment;
    }

    public void setAliment(String[] aliment) {
        this.aliment = aliment;
    }

    public String[] getQuantite() {
        return quantite;
    }

    public void setQuantite(String[] quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
