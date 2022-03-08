package com.example.mijotons;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.mijotons.classBaseDonnees.aliment;
import com.example.mijotons.classBaseDonnees.readJson;
import com.example.mijotons.classBaseDonnees.recette;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class ListeRecette extends AppCompatActivity {

    Intent intent;
    int filtre = 0;

    ArrayList<recette> listeRecette = new ArrayList<>();
    ArrayList<recette> listeRecetteAfficher =  new ArrayList<>();

    GridLayout gl_listeRecette;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_recette);

        gl_listeRecette = findViewById(R.id.gl_listeRecette);

        //Navigation
        BottomNavigationView mBottomNavigationView = findViewById(R.id.navigationBar);
        mBottomNavigationView.setOnItemSelectedListener(item-> {
            switch (item.getItemId()){
                case R.id.action_placards:
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_historique:
                    intent = new Intent(this, Historique.class);
                    startActivity(intent);
                    break;
                case R.id.action_courses:
                    intent = new Intent(this, Courses.class);
                    startActivity(intent);
                    break;
                case R.id.action_carte:
                    intent = new Intent(this, Carte.class);
                    startActivity(intent);
                    break;
                case R.id.action_favoris:
                    intent = new Intent(this, Favoris.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        //Initialisation de la liste des recettes
        try {
            for (int i = 1;i<25;i++){
                listeRecette.add(readJson.readBaseRecette(getApplicationContext(),String.valueOf(i)));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        //Trouver les recettes qui correspondent au aliment cocher
        for(recette rec : listeRecette)
        {
            int quantite = rec.getAliment().length;
            int compteur = 0;
                for (String alim : rec.getAliment())
                {
                    if(aliment.cocherAliment.contains(alim))
                    {
                        compteur++;
                    }
                }
            if ((compteur + filtre) == quantite){
                listeRecetteAfficher.add(rec);
            }
        }

        gl_listeRecette.removeAllViews();
        if (listeRecetteAfficher.size() != 0){
            for(recette rec : listeRecetteAfficher){
                Button button = new Button(getApplicationContext());
                button.setText(rec.getNom());
                button.setBackgroundResource(getResources().getIdentifier(rec.getImage(),"drawable",getPackageName()));
                button.setHeight(125);
                button.setGravity(4);
                button.setPadding(5,5,5,5);
                gl_listeRecette.addView(button);
            }
        }
        else
        {
            Log.d("test","test");
            TextView textView = new TextView(getApplicationContext());
            textView.setText("Aucune recette correspondante !");
            textView.setTextSize(25);
            gl_listeRecette.addView(textView);
        }


    }
}