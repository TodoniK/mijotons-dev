package com.example.mijotons;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mijotons.classBaseDonnees.aliment;
import com.example.mijotons.classBaseDonnees.readJson;
import com.example.mijotons.classBaseDonnees.recette;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class Chargement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);


        //aliment
        try {
            aliment.init(getApplicationContext());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        //Initialisation de la liste des recettes
        try {
            for (int i = 1;i<51;i++){
                ListeRecette.listeRecette.add(readJson.readBaseRecette(getApplicationContext(),String.valueOf(i)));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        //Historique et favoris
       try {
           ArrayList<String> listeNomFavoris = readJson.lireEnregistrement("Favoris",getApplicationContext());
           for(int i=listeNomFavoris.size()-1 ;i>=0;i--){
               for(recette rec : ListeRecette.listeRecette){
                   if(rec.getNom().equals(listeNomFavoris.get(i))){
                       Favoris.listeFavoris.add(0,rec);
                   }
               }
           }
           ArrayList<String> listeNomHistorique = readJson.lireEnregistrement("Historique",getApplicationContext());
           for(int i=listeNomHistorique.size()-1 ;i>=0;i--){
               for(recette rec : ListeRecette.listeRecette){
                   if(rec.getNom().equals(listeNomHistorique.get(i))){
                       Historique.listeHistorique.add(0,rec);
                   }
               }
           }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
       }

       //Course
       try{
           Courses.listeNom = readJson.lireEnregistrementCourse("Nom",getApplicationContext());
           Courses.listeQuantite = readJson.lireEnregistrementCourseQuantite("Quantite",getApplicationContext());
           Courses.listeSuffixe = readJson.lireEnregistrementCourse("Suffixe",getApplicationContext());
       }catch (JSONException | IOException e){
           e.printStackTrace();
       }


       Runnable runnable = () -> {
           Intent intent = new Intent(getApplicationContext(),MainActivity.class);
           startActivity(intent);
           finish();
       };
        int DUREE_ECRAN_CHARGEMENT = 3000;
        new Handler().postDelayed(runnable, DUREE_ECRAN_CHARGEMENT);

    }
}