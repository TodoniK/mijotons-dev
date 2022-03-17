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
    public static String[] nomAutre;

    public static ArrayList<String> cocherAliment = new ArrayList<>();

    public static ArrayList<CheckBox> fruits = new ArrayList<>();
    public static ArrayList<CheckBox> lait = new ArrayList<>();
    public static ArrayList<CheckBox> viandes = new ArrayList<>();
    public static ArrayList<CheckBox> legumes = new ArrayList<>();
    public static ArrayList<CheckBox> autre = new ArrayList<>();

    public static ArrayList<CheckBox> fruitsCourse = new ArrayList<>();
    public static ArrayList<CheckBox> laitCourse = new ArrayList<>();
    public static ArrayList<CheckBox> viandesCourse = new ArrayList<>();
    public static ArrayList<CheckBox> legumesCourse = new ArrayList<>();
    public static ArrayList<CheckBox> autreCourse = new ArrayList<>();


    public static void init(Context context) throws JSONException, IOException {
        if(fruits.size() == 0){
            nomFruits = readJson.readBaseAliment(context, "Fruits");
            for (String nomFruit : aliment.nomFruits) {
                CheckBox check = new CheckBox(context);
                check.setText(nomFruit);
                fruits.add(check);
                fruitsCourse.add(check);
            }

            nomViandes = readJson.readBaseAliment(context,"Viandes_Poissons");
            for (String nomViande : aliment.nomViandes) {
                CheckBox check = new CheckBox(context);
                check.setText(nomViande);
                viandes.add(check);
                viandesCourse.add(check);
            }

            nomLegumes = readJson.readBaseAliment(context,"Legumes");
            for (String nomLegume : aliment.nomLegumes) {
                CheckBox check = new CheckBox(context);
                check.setText(nomLegume);
                legumesCourse.add(check);
                legumes.add(check);
            }

            nomLait = readJson.readBaseAliment(context,"Produits_laitiers");
            for (String nomLait : aliment.nomLait) {
                CheckBox check = new CheckBox(context);
                check.setText(nomLait);
                lait.add(check);
                laitCourse.add(check);

            }

            nomAutre = readJson.readBaseAliment(context,"Autres");
            for (String nomAutre : aliment.nomAutre) {
                CheckBox check = new CheckBox(context);
                check.setText(nomAutre);
                autre.add(check);
                autreCourse.add(check);
            }

        }

    }

    public static void initCocher()
    {
        cocherAliment.clear();
        if(MainActivity.ajoutCourse)
        {
            for(int i=0;i<fruitsCourse.size();i++)
            {
                if(fruitsCourse.get(i).isChecked()){
                    cocherAliment.add(fruitsCourse.get(i).getText().toString());
                }
            }

            for(int i=0;i<viandesCourse.size();i++)
            {
                if(viandesCourse.get(i).isChecked()){
                    cocherAliment.add(viandesCourse.get(i).getText().toString());
                }
            }

            for(int i=0;i<legumesCourse.size();i++)
            {
                if(legumesCourse.get(i).isChecked()){
                    cocherAliment.add(legumesCourse.get(i).getText().toString());
                }
            }

            for(int i=0;i<laitCourse.size();i++)
            {
                if(laitCourse.get(i).isChecked()){
                    cocherAliment.add(laitCourse.get(i).getText().toString());
                }
            }

            for(int i=0;i<autreCourse.size();i++)
            {
                if(autreCourse.get(i).isChecked()){
                    cocherAliment.add(autreCourse.get(i).getText().toString());
                }
            }
        }
        else
        {
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

            for(int i=0;i<autre.size();i++)
            {
                if(autre.get(i).isChecked()){
                    cocherAliment.add(autre.get(i).getText().toString());
                }
            }
        }

    }

    public static void initRecherche(Context context) throws JSONException, IOException{
        if(MainActivity.ajoutCourse)
        {
            for(int i = 0 ; i<nomFruits.length;i++){
                CheckBox check = new CheckBox(context);
                check.setText(nomFruits[i]);
                if(fruitsCourse.get(i).isChecked()){
                    check.setChecked(true);
                }
                fruitsCourse.set(i,check);
            }
            for(int i = 0 ; i<nomLait.length;i++){
                CheckBox check = new CheckBox(context);
                check.setText(nomLait[i]);
                if(laitCourse.get(i).isChecked()){
                    check.setChecked(true);
                }
                laitCourse.set(i,check);
            }
            for(int i = 0 ; i<nomViandes.length;i++){
                CheckBox check = new CheckBox(context);
                check.setText(nomViandes[i]);
                if(viandesCourse.get(i).isChecked()){
                    check.setChecked(true);
                }
                viandesCourse.set(i,check);
            }
            for(int i = 0 ; i<nomLegumes.length;i++){
                CheckBox check = new CheckBox(context);
                check.setText(nomLegumes[i]);
                if(legumesCourse.get(i).isChecked()){
                    check.setChecked(true);
                }
                legumesCourse.set(i,check);
            }

            for(int i = 0 ; i<nomAutre.length;i++){
                CheckBox check = new CheckBox(context);
                check.setText(nomAutre[i]);
                if(autreCourse.get(i).isChecked()){
                    check.setChecked(true);
                }
                autreCourse.set(i,check);
            }
        }
        else
        {
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

            for(int i = 0 ; i<nomAutre.length;i++){
                CheckBox check = new CheckBox(context);
                check.setText(nomAutre[i]);
                if(autre.get(i).isChecked()){
                    check.setChecked(true);
                }
                autre.set(i,check);
            }
        }

    }

    public static void initCourse()
    {
        for(CheckBox cb : fruitsCourse)
        {
            cb.setChecked(false);
        }

        for(CheckBox cb : legumesCourse)
        {
            cb.setChecked(false);
        }

        for(CheckBox cb : laitCourse)
        {
            cb.setChecked(false);
        }

        for(CheckBox cb : viandesCourse)
        {
            cb.setChecked(false);
        }

        for(CheckBox cb : autreCourse)
        {
            cb.setChecked(false);
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
