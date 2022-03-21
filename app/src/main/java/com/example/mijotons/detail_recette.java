package com.example.mijotons;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mijotons.classBaseDonnees.readJson;
import com.example.mijotons.classBaseDonnees.recette;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class detail_recette extends AppCompatActivity {
    Intent intent;

    static recette recetteAfficher;
    ImageView iv_imageRecette;
    ImageView iv_favoris;
    TextView tv_nomRecetteDetail;
    TextView tv_ingrediantsManquants;
    TextView tv_etapes;
    TextView tv_ingredient;
    TextView tv_nombrePersonnes;
    TextView tv_tempsPreparation;
    ImageView iv_ajoutCourse;

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recette);

        iv_imageRecette= findViewById(R.id.iv_imageRecette);
        tv_nomRecetteDetail = findViewById(R.id.tv_nomRecetteDetail);
        tv_ingrediantsManquants = findViewById(R.id.tv_ingrediantsManquants);
        tv_etapes = findViewById(R.id.tv_etapes);
        iv_favoris = findViewById(R.id.iv_favoris);
        tv_nombrePersonnes = findViewById(R.id.tv_nombrePersonnes);
        tv_tempsPreparation = findViewById(R.id.tv_tempsPreparation);
        iv_ajoutCourse = findViewById(R.id.iv_ajoutCourse);

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

        iv_ajoutCourse.setOnClickListener(view -> {
            for(int i = 0;i<recetteAfficher.getAliment().length;i++){
                Courses.ajoutCourse(recetteAfficher.getAliment()[i],recetteAfficher.getQuantite()[i]);
            }
            try {
                readJson.enregistrementCourse(Courses.listeNom,Courses.listeQuantite,Courses.listeSuffixe,getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),"Ingrédients ajoutés aux courses",Toast.LENGTH_SHORT).show();
        });


        iv_favoris.setOnClickListener(view -> {
            if(Favoris.checkPresence(recetteAfficher)){
                Favoris.retirerFavoris(recetteAfficher);
                iv_favoris.setImageResource(getResources().getIdentifier("heart", "drawable", getPackageName()));
            }
            else
            {
                Favoris.ajoutFavoris(recetteAfficher);
                iv_favoris.setImageResource(getResources().getIdentifier("heartred", "drawable", getPackageName()));
            }
            try {
                readJson.enregistrement(Favoris.listeFavoris,"Favoris",getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        if(!Favoris.checkPresence(recetteAfficher)){
            iv_favoris.setImageResource(getResources().getIdentifier("heart", "drawable", getPackageName()));
        }
        else
        {
            iv_favoris.setImageResource(getResources().getIdentifier("heartred", "drawable", getPackageName()));
        }

        iv_imageRecette.setImageResource(getResources().getIdentifier(recetteAfficher.getImage(), "drawable", getPackageName()));
        tv_nomRecetteDetail.setText(recetteAfficher.getNom());
        tv_ingrediantsManquants.setText("Ingrédient(s) manquant(s) : "+ ListeRecette.filtre);
        tv_tempsPreparation.setText("Temps de préparation : " + recetteAfficher.getTemps());
        tv_nombrePersonnes.setText("Nombre de personnes : "+recetteAfficher.getNbrPersonne());
        for(int i = 0 ;i < recetteAfficher.getAliment().length;i++)
        {
            tv_ingredient = findViewById(getResources().getIdentifier("tv_ingredient"+ (i + 1),"id",this.getPackageName()));
            tv_ingredient.setText(recetteAfficher.getQuantite()[i] + " " + recetteAfficher.getAliment()[i]);
            tv_ingredient.setVisibility(View.VISIBLE);

        }
        tv_etapes.setText(recetteAfficher.getDescription());
    }
}