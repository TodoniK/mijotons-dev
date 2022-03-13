package com.example.mijotons;


import static android.view.Gravity.CENTER_HORIZONTAL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mijotons.classBaseDonnees.recette;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class Favoris extends AppCompatActivity {
    Intent intent;

    ImageView iv_image;
    TextView tv_titre;
    TextView tv_temps;
    TextView tv_ingredient;
    TextView tv_nbrPersonne;
    LinearLayout ll_favoris;
    LinearLayout ll_rien;

    static ArrayList<recette> listeFavoris = new ArrayList<>();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        ll_favoris = findViewById(R.id.ll_favoris);
        ll_rien = findViewById(R.id.ll_rien);

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
            }
            return true;
        });

        initFavoris();
    }

    public static void ajoutFavoris(recette rec){
        if (!checkPresence(rec)){
            if(listeFavoris.size() == 20)
            {
                listeFavoris.add(0,rec);
                listeFavoris.remove(21);
            }
            else
            {
                listeFavoris.add(0,rec);
            }
        }
    }

    public static void retirerFavoris(recette rec){
        for(recette r : listeFavoris)
        {
            if (r.getNom().equals(rec.getNom())) {
                listeFavoris.remove(r);
                break;
            }
        }
    }

    public static boolean checkPresence(recette rec)
    {
        boolean presence=false;
        for(recette r : listeFavoris)
        {
            if (r.getNom().equals(rec.getNom())) {
                presence = true;
                break;
            }
        }
        return presence;
    }


    @SuppressLint("SetTextI18n")
    public void initFavoris()
    {
        if(listeFavoris.size() == 0)
        {
            TextView tv_rien = new TextView(getApplicationContext());
            tv_rien.setText("Pas de favoris");
            tv_rien.setGravity(CENTER_HORIZONTAL);
            tv_rien.setTextSize(20);
            ll_rien.addView(tv_rien);
        }
        else {
            for (int i = 0; i < listeFavoris.size(); i++) {
                LinearLayout ll_recette = findViewById(getResources().getIdentifier("ll_recette" + i, "id", this.getPackageName()));
                ll_recette.setVisibility(View.VISIBLE);
                int finalI = i;
                ll_recette.setOnClickListener(view -> {
                    detail_recette.recetteAfficher = listeFavoris.get(finalI);
                    intent = new Intent(getApplicationContext(), detail_recette.class);
                    startActivity(intent);
                });
                iv_image = findViewById(getResources().getIdentifier("iv_image" + i, "id", this.getPackageName()));
                iv_image.setImageResource(getResources().getIdentifier(listeFavoris.get(i).getImage(), "drawable", getPackageName()));
                tv_titre = findViewById(getResources().getIdentifier("titre" + i, "id", this.getPackageName()));
                tv_titre.setText(listeFavoris.get(i).getNom());
                tv_temps = findViewById(getResources().getIdentifier("temps"+ i,"id",this.getPackageName()));
                tv_temps.setText("Temps : "+listeFavoris.get(i).getTemps());
                tv_nbrPersonne = findViewById(getResources().getIdentifier("tv_nbrPersonne"+ i,"id",this.getPackageName()));
                tv_nbrPersonne.setText("Nombre de Personne : "+listeFavoris.get(i).getNbrPersonne());


                for (int j = 0; j < listeFavoris.get(i).getAliment().length; j++) {
                    tv_ingredient = findViewById(getResources().getIdentifier("tv_ingredient" + (j + 6 * i), "id", this.getPackageName()));
                    tv_ingredient.setText(listeFavoris.get(i).getAliment()[j]);
                    tv_ingredient.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}