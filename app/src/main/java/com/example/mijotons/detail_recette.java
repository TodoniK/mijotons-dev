package com.example.mijotons;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mijotons.classBaseDonnees.recette;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class detail_recette extends AppCompatActivity {
    Intent intent;

    static recette recetteAfficher;
    ImageView iv_imageRecette;
    TextView tv_nomRecetteDetail;
    TextView tv_ingrediantsManquants;
    TextView tv_etapes;
    TextView tv_ingredient;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recette);

        iv_imageRecette= findViewById(R.id.iv_imageRecette);
        tv_nomRecetteDetail = findViewById(R.id.tv_nomRecetteDetail);
        tv_ingrediantsManquants = findViewById(R.id.tv_ingrediantsManquants);
        tv_etapes = findViewById(R.id.tv_etapes);

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

        iv_imageRecette.setImageResource(getResources().getIdentifier(recetteAfficher.getImage(), "drawable", getPackageName()));
        tv_nomRecetteDetail.setText(recetteAfficher.getNom());
        tv_ingrediantsManquants.setText("Ingrédient(s) manquant(s) : "+String.valueOf(ListeRecette.filtre));
        for(int i = 0 ;i < recetteAfficher.getAliment().length;i++)
        {
            tv_ingredient = findViewById(getResources().getIdentifier("tv_ingredient"+String.valueOf(i+1),"id",this.getPackageName()));
            tv_ingredient.setText(recetteAfficher.getAliment()[i]);
            tv_ingredient.setVisibility(View.VISIBLE);

        }
        tv_etapes.setText(recetteAfficher.getDescription());
    }
}