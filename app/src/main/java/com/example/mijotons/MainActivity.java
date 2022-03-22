package com.example.mijotons;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent intent;

    private LinearLayout ll_fruits,ll_legumes,ll_viandes,ll_lait;

    private final String[] nomFruits = { "Fraise", "Framboise", "Pomme", "Poire", "Mangue", "Passion", "Banane", "Kiwi" };
    private final String[] nomLaits = { "Beurre", "Lait", "Lait de riz", "Lait de soja", "Margarine", "Emmental", "Roquefort", "Camembert" };
    private final String[] nomViandes = { "Rôti", "Entrecôte", "Côte de boeuf", "Escalope de dinde", "Cordon bleu" };
    private final String[] nomLegumes = { "Poireau" , "Poivron", "Haricot vert", "Endive","Carotte","Tomate","Salade" };

    ArrayList<CheckBox> fruits = new ArrayList<>();
    ArrayList<CheckBox> lait = new ArrayList<>();
    ArrayList<CheckBox> viandes = new ArrayList<>();
    ArrayList<CheckBox> legumes = new ArrayList<>();


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mes_placards);

        //Navigation
        BottomNavigationView mBottomNavigationView = findViewById(R.id.navigationBar);
        ll_fruits=findViewById(R.id.ll_fruits);
        ll_legumes=findViewById(R.id.ll_legumes);
        ll_viandes=findViewById(R.id.ll_viandes);
        ll_lait=findViewById(R.id.ll_lait);
        Button b_fruits = findViewById(R.id.b_fruits);
        Button b_viandes = findViewById(R.id.b_viandes);
        Button b_legumes = findViewById(R.id.b_legumes);
        Button b_lait = findViewById(R.id.b_lait);
        Button b_terminerChoixPlacards = findViewById(R.id.b_terminerChoixPlacards);



        mBottomNavigationView.setOnItemSelectedListener(item-> {
                switch (item.getItemId()){
                    case R.id.action_historique:
                        intent = new Intent(this, Historique.class);
                        startActivity(intent);
                        //?finish();
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

        //menu Fruits
        b_fruits.setOnClickListener(view -> {
            if(fruits.size() != 0 && ll_fruits.getChildCount() != 0){
                ll_fruits.removeAllViews();
            }
            else
            {
                if(fruits.size() == 0)
                {
                    for (String nomFruit : nomFruits) {
                        CheckBox check = new CheckBox(getApplicationContext());
                        check.setText(nomFruit);
                        fruits.add(check);
                        ll_fruits.addView(check);
                    }
                }
                else
                {
                    for(int i=0;i<fruits.size();i++)
                    {
                        ll_fruits.addView(fruits.get(i));
                    }
                }

            }
        });


        //menu lait
        b_lait.setOnClickListener(view -> {
            if(lait.size() != 0 && ll_lait.getChildCount() != 0){
                ll_lait.removeAllViews();
            }
            else
            {
                if(lait.size() == 0)
                {
                    for (String nomLait : nomLaits) {
                        CheckBox check = new CheckBox(getApplicationContext());
                        check.setText(nomLait);
                        lait.add(check);
                        ll_lait.addView(check);
                    }
                }
                else
                {
                    for(int i=0;i<lait.size();i++)
                    {
                        ll_lait.addView(lait.get(i));
                    }
                }


            }
        });

        //menu viandes
        b_viandes.setOnClickListener(view -> {
            if(viandes.size() != 0 && ll_viandes.getChildCount() != 0){
                ll_viandes.removeAllViews();
            }
            else
            {
                if(viandes.size() == 0)
                {
                    for (String nomViande : nomViandes) {
                        CheckBox check = new CheckBox(getApplicationContext());
                        check.setText(nomViande);
                        viandes.add(check);
                        ll_viandes.addView(check);
                    }
                }
                else
                {
                    for(int i=0;i<viandes.size();i++)
                    {
                        ll_viandes.addView(viandes.get(i));
                    }
                }

            }
        });

        //menu legumes
        b_legumes.setOnClickListener(view -> {
            if(legumes.size() != 0 && ll_legumes.getChildCount() != 0){
                ll_legumes.removeAllViews();
            }
            else
            {
                if(legumes.size() == 0)
                {
                    for (String nomLegume : nomLegumes) {
                        CheckBox check = new CheckBox(getApplicationContext());
                        check.setText(nomLegume);
                        legumes.add(check);
                        ll_legumes.addView(check);
                    }
                }
                else
                {
                    for(int i=0;i<legumes.size();i++)
                    {
                        ll_legumes.addView(legumes.get(i));
                    }
                }

            }
        });

        //bouton j'ai finis
        b_terminerChoixPlacards.setOnClickListener(view -> {
            fruits.clear();
            lait.clear();
            legumes.clear();
            viandes.clear();
            ll_legumes.removeAllViews();
            ll_viandes.removeAllViews();
            ll_lait.removeAllViews();
            ll_fruits.removeAllViews();
        });
    }
}