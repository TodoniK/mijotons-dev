package com.example.mijotons;

import static android.view.Gravity.CENTER_HORIZONTAL;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mijotons.classBaseDonnees.recette;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Historique extends AppCompatActivity {
    Intent intent;

    static ArrayList<recette> listeHistorique = new ArrayList<>();

    LinearLayout ll_historique = new LinearLayout(getApplicationContext());

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
            listeHistorique.add(rec);
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
            for(recette rec : listeHistorique)
            {
                Button button = new Button(getApplicationContext());
            }
        }
    }


}