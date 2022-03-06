package com.example.mijotons.classBaseDonnees;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.mijotons.MainActivity;

import org.json.JSONException;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

public class aliment {

    public static String[] nomFruits;
    public static String[] nomViandes;
    public static String[] nomLegumes;
    public static String[] nomLait;

    public static ArrayList<String> cocherAliment = new ArrayList<>();

    public static ArrayList<CheckBox> fruits = new ArrayList<>();
    public static ArrayList<CheckBox> lait = new ArrayList<>();
    public static ArrayList<CheckBox> viandes = new ArrayList<>();
    public static ArrayList<CheckBox> legumes = new ArrayList<>();

    public static ArrayList<CheckBox> replacement =  new ArrayList<>();

    public static void init(Context context) throws JSONException, IOException {
        fruits.clear();
        lait.clear();
        viandes.clear();
        legumes.clear();

        nomFruits = readJson.readBaseAliment(context, "Fruits");
        for (String nomFruit : aliment.nomFruits) {
            CheckBox check = new CheckBox(context);
            check.setText(nomFruit);
            fruits.add(check);
        }

        nomViandes = readJson.readBaseAliment(context,"ViandesPoissons");
        for (String nomViande : aliment.nomViandes) {
            CheckBox check = new CheckBox(context);
            check.setText(nomViande);
            viandes.add(check);
        }

        nomLegumes = readJson.readBaseAliment(context,"Legumes");
        for (String nomLegume : aliment.nomLegumes) {
            CheckBox check = new CheckBox(context);
            check.setText(nomLegume);
            legumes.add(check);
        }

        nomLait = readJson.readBaseAliment(context,"Produits laitiers");
        for (String nomLait : aliment.nomLait) {
            CheckBox check = new CheckBox(context);
            check.setText(nomLait);
            lait.add(check);
        }
    }

    public static void initCocher()
    {
        cocherAliment.clear();
        for(int i=0;i<fruits.size();i++)
        {
            if(fruits.get(i).isChecked()){
                cocherAliment.add(fruits.get(i).getText().toString());
            }
        }

        for(int i=0;i<viandes.size();i++)
        {
            if(viandes.get(i).isChecked()){
                cocherAliment.add(viandes.get(i).getText().toString());
            }
        }

        for(int i=0;i<legumes.size();i++)
        {
            if(legumes.get(i).isChecked()){
                cocherAliment.add(legumes.get(i).getText().toString());
            }
        }

        for(int i=0;i<lait.size();i++)
        {
            if(lait.get(i).isChecked()){
                cocherAliment.add(lait.get(i).getText().toString());
            }
        }
    }

    public static void initRecherche(Context context) throws JSONException, IOException{
        for(int i = 0 ; i<nomFruits.length;i++){
            CheckBox check = new CheckBox(context);
            check.setText(nomFruits[i]);
            if(fruits.get(i).isChecked()){
                check.setChecked(true);
            }
            fruits.set(i,check);
        }
        for(int i = 0 ; i<nomLait.length;i++){
            CheckBox check = new CheckBox(context);
            check.setText(nomLait[i]);
            if(lait.get(i).isChecked()){
                check.setChecked(true);
            }
            lait.set(i,check);
        }
        for(int i = 0 ; i<nomViandes.length;i++){
            CheckBox check = new CheckBox(context);
            check.setText(nomViandes[i]);
            if(viandes.get(i).isChecked()){
                check.setChecked(true);
            }
            viandes.set(i,check);
        }
        for(int i = 0 ; i<nomLegumes.length;i++){
            CheckBox check = new CheckBox(context);
            check.setText(nomLegumes[i]);
            if(legumes.get(i).isChecked()){
                check.setChecked(true);
            }
            legumes.set(i,check);
        }
    }

    public static String[] getNomFruits() {
        return nomFruits;
    }

    public static String[] getNomLait() {
        return nomLait;
    }

    public static String[] getNomLegumes() {
        return nomLegumes;
    }

    public static String[] getNomViandes() {
        return nomViandes;
    }

    public static void setNomFruits(String[] nomFruits) {
        aliment.nomFruits = nomFruits;
    }

    public static void setNomLait(String[] nomLait) {
        aliment.nomLait = nomLait;
    }

    public static void setNomLegumes(String[] nomLegumes) {
        aliment.nomLegumes = nomLegumes;
    }

    public static void setNomViandes(String[] nomViandes) {
        aliment.nomViandes = nomViandes;
    }
}
