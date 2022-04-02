package com.example.mijotons;

import static android.view.Gravity.CENTER_HORIZONTAL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mijotons.classBaseDonnees.recette;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Locale;

@SuppressLint("SetTextI18n")
public class Historique extends AppCompatActivity {

    Intent intent;
    ImageView iv_image;
    TextView tv_titre;
    TextView tv_temps;
    TextView tv_ingredient;
    TextView tv_nbrPersonne;
    LinearLayout ll_rien;
    LinearLayout ll_historique;
    EditText et_recherche;
    ScrollView sv_historique;

    static ArrayList<recette> listeHistorique = new ArrayList<>();
    ArrayList<recette> listeHistoriqueRecherche = new ArrayList<>();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        sv_historique = findViewById(R.id.sv_historique);
        ll_historique = findViewById(R.id.ll_historique);
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

        sv_historique.setOnTouchListener((v, event) -> true);

        et_recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0)
                {
                    listeHistoriqueRecherche.clear();
                    visibilite();
                    for(recette rec : listeHistorique)
                    {
                        if(rec.getNom().toUpperCase().startsWith(charSequence.toString().toUpperCase()))
                        {
                            listeHistoriqueRecherche.add(listeHistorique.get(listeHistorique.indexOf(rec)));
                        }
                    }
                    initHistorique(true);
                }
                else
                {
                    initHistorique(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        initHistorique(false);
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


    public void initHistorique(boolean recherche)
    {
        if(!recherche)
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
                    remplirHistorique(listeHistorique.get(i),i);
                }
                if(listeHistorique.size()>3){
                    sv_historique.setOnTouchListener(null);
                }
            }
        }
        else
        {
            if(listeHistoriqueRecherche.size() == 0)
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
                for(int i=0;i<listeHistoriqueRecherche.size();i++)
                {
                    remplirHistorique(listeHistoriqueRecherche.get(i),i);
                }
                if(listeHistorique.size()>3){
                    sv_historique.setOnTouchListener(null);
                }
            }
        }

    }

    public void remplirHistorique(recette recette,int indice)
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
        tv_nbrPersonne.setText("Pour : "+recette.getNbrPersonne());

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