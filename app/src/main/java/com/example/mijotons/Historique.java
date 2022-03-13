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

@SuppressLint("SetTextI18n")
public class Historique extends AppCompatActivity {
    Intent intent;
    ImageView iv_image;
    TextView tv_titre;
    TextView tv_temps;
    TextView tv_ingredient;
    TextView tv_nbrPersonne;
    LinearLayout ll_rien;

    static ArrayList<recette> listeHistorique = new ArrayList<>();

    LinearLayout ll_historique;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        ll_historique = findViewById(R.id.ll_historique);
        ll_rien = findViewById(R.id.ll_rien);

        //Navigation
        BottomNavigationView mBottomNavigationView = findViewById(R.id.navigationBar);
        mBottomNavigationView.setOnItemSelectedListener(item-> {
            switch (item.getItemId()){
                case R.id.action_placards:
                    intent = new Intent(this, MainActivity.class);
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

        initHistorique();
    }

    public static void ajoutHistorique(recette uneRecette)
    {

        if(!checkPresence(uneRecette)){
            listeHistorique.add(0,uneRecette);
            if(listeHistorique.size() > 20)
            {
                listeHistorique.remove(21);
            }
        }
        else
        {
            for(recette rec : listeHistorique){
                if(rec.getNom().equals(uneRecette.getNom())){
                    listeHistorique.remove(rec);
                    listeHistorique.add(0,uneRecette);
                    break;
                }
            }
        }
    }

    public static boolean checkPresence(recette uneRecette)
    {
        boolean presence = false;
        for(recette rec : listeHistorique){
            if(rec.getNom().equals(uneRecette.getNom())){
                presence = true;
                break;
            }
        }
        return presence;
    }



    public void initHistorique()
    {
        if(listeHistorique.size() == 0)
        {
            TextView tv_rien = new TextView(getApplicationContext());
            tv_rien.setText("Pas d'historique");
            tv_rien.setGravity(CENTER_HORIZONTAL);
            tv_rien.setTextSize(20);
            ll_rien.addView(tv_rien);
        }
        else
        {
            ll_rien.removeAllViews();
            for(int i=0;i<listeHistorique.size();i++)
            {
                LinearLayout ll_recette = findViewById(getResources().getIdentifier("ll_recette"+i,"id",this.getPackageName()));
                ll_recette.setVisibility(View.VISIBLE);
                int finalI = i;
                ll_recette.setOnClickListener(view -> {
                    detail_recette.recetteAfficher = listeHistorique.get(finalI);
                    intent = new Intent(getApplicationContext(), detail_recette.class);
                    startActivity(intent);
                });
                iv_image = findViewById(getResources().getIdentifier("iv_image"+ i,"id",this.getPackageName()));
                iv_image.setImageResource(getResources().getIdentifier(listeHistorique.get(i).getImage(), "drawable", getPackageName()));
                tv_titre = findViewById(getResources().getIdentifier("titre"+ i,"id",this.getPackageName()));
                tv_titre.setText(listeHistorique.get(i).getNom());
                tv_temps = findViewById(getResources().getIdentifier("temps"+ i,"id",this.getPackageName()));
                tv_temps.setText("Temps : "+listeHistorique.get(i).getTemps());
                tv_nbrPersonne = findViewById(getResources().getIdentifier("tv_nbrPersonne"+ i,"id",this.getPackageName()));
                tv_nbrPersonne.setText("Nombre de Personne : "+listeHistorique.get(i).getNbrPersonne());

                for(int j = 0 ;j < listeHistorique.get(i).getAliment().length;j++)
                {
                    tv_ingredient = findViewById(getResources().getIdentifier("tv_ingredient"+ (j + 6 * i),"id",this.getPackageName()));
                    tv_ingredient.setText(listeHistorique.get(i).getAliment()[j]);
                    tv_ingredient.setVisibility(View.VISIBLE);
                }
            }
        }
    }


}