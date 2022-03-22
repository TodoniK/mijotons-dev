package com.example.mijotons;


import static android.view.Gravity.CENTER_HORIZONTAL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
    EditText et_recherche;
    LinearLayout ll_favoris;
    LinearLayout ll_rien;

    static ArrayList<recette> listeFavoris = new ArrayList<>();
    ArrayList<recette> listeFavorisRecherche = new ArrayList<>();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        ll_favoris = findViewById(R.id.ll_favoris);
        ll_rien = findViewById(R.id.ll_rien);
        et_recherche = findViewById(R.id.et_recherche);

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

        et_recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0)
                {
                    listeFavorisRecherche.clear();
                    visibilite();
                    for(recette rec : listeFavoris)
                    {
                        if(rec.getNom().toUpperCase().startsWith(charSequence.toString().toUpperCase()))
                        {
                            listeFavorisRecherche.add(listeFavoris.get(listeFavoris.indexOf(rec)));
                        }
                    }
                    initFavoris(true);
                }
                else
                {
                    initFavoris(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        initFavoris(false);
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
    public void initFavoris(boolean recherche)
    {
        if(!recherche)
        {
            if(listeFavoris.size() == 0)
            {
                TextView tv_rien = new TextView(getApplicationContext());
                tv_rien.setText("Pas de favoris");
                tv_rien.setGravity(CENTER_HORIZONTAL);
                tv_rien.setTextSize(20);
                ll_rien.addView(tv_rien);
            }
            else
            {
                ll_rien.removeAllViews();
                for(int i=0;i<listeFavoris.size();i++)
                {
                    remplirFavoris(listeFavoris.get(i),i);
                }
            }
        }
        else
        {
            if(listeFavorisRecherche.size() == 0)
            {
                TextView tv_rien = new TextView(getApplicationContext());
                tv_rien.setText("Pas de favoris");
                tv_rien.setGravity(CENTER_HORIZONTAL);
                tv_rien.setTextSize(20);
                ll_rien.addView(tv_rien);
            }
            else
            {
                ll_rien.removeAllViews();
                for(int i=0;i<listeFavorisRecherche.size();i++)
                {
                    remplirFavoris(listeFavorisRecherche.get(i),i);
                }
            }
        }
    }

    public void remplirFavoris(recette recette,int indice)
    {
        LinearLayout ll_recette = findViewById(getResources().getIdentifier("ll_recette"+indice,"id",this.getPackageName()));
        ll_recette.setVisibility(View.VISIBLE);
        ll_recette.setOnClickListener(view -> {
            detail_recette.recetteAfficher = recette;
            intent = new Intent(getApplicationContext(), detail_recette.class);
            startActivity(intent);
        });
        iv_image = findViewById(getResources().getIdentifier("iv_image"+ indice,"id",this.getPackageName()));
        iv_image.setImageResource(getResources().getIdentifier(recette.getImage(), "drawable", getPackageName()));
        tv_titre = findViewById(getResources().getIdentifier("titre"+ indice,"id",this.getPackageName()));
        tv_titre.setText(recette.getNom());
        tv_temps = findViewById(getResources().getIdentifier("temps"+ indice,"id",this.getPackageName()));
        tv_temps.setText("Temps : "+recette.getTemps());
        tv_nbrPersonne = findViewById(getResources().getIdentifier("tv_nbrPersonne"+ indice,"id",this.getPackageName()));
        tv_nbrPersonne.setText("Nombre de Personne : "+recette.getNbrPersonne());

        for(int j = 0 ;j < recette.getAliment().length;j++)
        {
            tv_ingredient = findViewById(getResources().getIdentifier("tv_ingredient"+ (j + 6 * indice),"id",this.getPackageName()));
            tv_ingredient.setText(recette.getAliment()[j]);
            tv_ingredient.setVisibility(View.VISIBLE);
        }
    }

    public void visibilite(){
        for(int i = 0; i<20;i++)
        {
            LinearLayout ll_recette = findViewById(getResources().getIdentifier("ll_recette"+i,"id",this.getPackageName()));
            ll_recette.setVisibility(View.INVISIBLE);
        }
    }
}