package com.example.mijotons;

import static android.view.Gravity.CENTER_HORIZONTAL;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mijotons.classBaseDonnees.recette;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Historique extends AppCompatActivity {
    Intent intent;
    ImageView iv_image;
    TextView tv_titre;
    TextView tv_temps;
    TextView tv_ingredient;


    static ArrayList<recette> listeHistorique = new ArrayList<>();

    LinearLayout ll_historique;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        ll_historique = findViewById(R.id.ll_historique);

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

    public static void ajoutHistorique(recette rec){
        if(listeHistorique.size() == 20)
        {
            listeHistorique.add(0,rec);
            listeHistorique.remove(21);
        }
        else
        {
            listeHistorique.add(0,rec);
        }
    }

    public void initHistorique()
    {
        if(listeHistorique.size() == 0)
        {
            TextView tv_rien = new TextView(getApplicationContext());
            tv_rien.setText("Pas d'historique");
            tv_rien.setTextSize(20);
            ll_historique.addView(tv_rien);
        }
        else
        {
            for(int i=0;i<listeHistorique.size();i++)
            {
                LinearLayout ll_recette = findViewById(getResources().getIdentifier("ll_recette"+String.valueOf(i),"id",this.getPackageName()));
                ll_recette.setVisibility(View.VISIBLE);
                int finalI = i;
                ll_recette.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        detail_recette.recetteAfficher = listeHistorique.get(finalI);
                        intent = new Intent(getApplicationContext(), detail_recette.class);
                        startActivity(intent);
                    }
                });
                iv_image = findViewById(getResources().getIdentifier("iv_image"+String.valueOf(i),"id",this.getPackageName()));
                iv_image.setImageResource(getResources().getIdentifier(listeHistorique.get(i).getImage(), "drawable", getPackageName()));
                tv_titre = findViewById(getResources().getIdentifier("titre"+String.valueOf(i),"id",this.getPackageName()));
                tv_titre.setText(listeHistorique.get(i).getNom());

                for(int j = 0 ;j < listeHistorique.get(i).getAliment().length;j++)
                {
                    tv_ingredient = findViewById(getResources().getIdentifier("tv_ingredient"+String.valueOf(j+6*i),"id",this.getPackageName()));
                    tv_ingredient.setText(listeHistorique.get(i).getAliment()[j]);
                    tv_ingredient.setVisibility(View.VISIBLE);
                }
            }
        }
    }


}