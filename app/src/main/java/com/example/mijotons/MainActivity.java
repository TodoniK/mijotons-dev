package com.example.mijotons;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mijotons.classBaseDonnees.aliment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static boolean ajoutCourse = false;
    Intent intent;
    private LinearLayout ll_fruits,ll_legumes,ll_viandes,ll_lait,ll_recherche,ll_menuG,ll_autre;
    TextView tv_nomMenuPlacards;



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mes_placards);

        //Navigation
        BottomNavigationView mBottomNavigationView = findViewById(R.id.navigationBar);

        final int nbrColonne = 4;

        ll_fruits=findViewById(R.id.ll_fruits);
        ll_legumes=findViewById(R.id.ll_legumes);
        ll_viandes=findViewById(R.id.ll_viandes);
        ll_lait=findViewById(R.id.ll_lait);
        ll_recherche=findViewById(R.id.ll_recherche);
        ll_menuG=findViewById(R.id.ll_menuG);
        ll_autre=findViewById(R.id.ll_autre);

        Button b_fruits = findViewById(R.id.b_fruits);
        Button b_viandes = findViewById(R.id.b_viandes);
        Button b_legumes = findViewById(R.id.b_legumes);
        Button b_lait = findViewById(R.id.b_lait);
        Button b_autre = findViewById(R.id.b_autre);
        Button b_terminerChoixPlacards = findViewById(R.id.b_terminerChoixPlacards);

        EditText et_recherche = findViewById(R.id.et_recherche);
        tv_nomMenuPlacards = findViewById(R.id.tv_nomMenuPlacards);

        //Lire la base de données aliment et initializer les listes

        if(ajoutCourse)
        {
            aliment.initCourse();
            tv_nomMenuPlacards.setText("Ajoutez à vos courses");
            b_terminerChoixPlacards.setText("Voir ma liste de courses !");
        }
        else
        {
            tv_nomMenuPlacards.setText("Choisissez vos aliments !");
            b_terminerChoixPlacards.setText("Voir mes recettes !");
        }

        GridLayout gridLayoutF = new GridLayout(getApplicationContext());
        gridLayoutF.setColumnCount(nbrColonne);
        gridLayoutF.setRowCount(aliment.fruits.size()/2);

        GridLayout gridLayoutPL = new GridLayout(getApplicationContext());
        gridLayoutPL.setColumnCount(nbrColonne);
        gridLayoutPL.setRowCount(aliment.lait.size()/2);

        GridLayout gridLayoutV = new GridLayout(getApplicationContext());
        gridLayoutV.setColumnCount(nbrColonne);
        gridLayoutV.setRowCount(aliment.viandes.size()/2);

        GridLayout gridLayoutL = new GridLayout(getApplicationContext());
        gridLayoutL.setColumnCount(nbrColonne);
        gridLayoutL.setRowCount(aliment.legumes.size()/2);

        GridLayout gridLayoutA = new GridLayout(getApplicationContext());
        gridLayoutA.setColumnCount(nbrColonne);
        gridLayoutA.setRowCount(aliment.autre.size()/2);

        mBottomNavigationView.setOnItemSelectedListener(item-> {
                switch (item.getItemId()){
                    case R.id.action_placards:
                        if(ajoutCourse)
                        {
                            ajoutCourse=false;
                            intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
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

        //menu Fruits
        b_fruits.setOnClickListener(view -> {
            if(aliment.fruits.size() != 0 && ll_fruits.getChildCount() != 0){
                gridLayoutF.removeAllViews();
                ll_fruits.removeAllViews();
            }
            else if(ll_fruits.getChildCount() == 0)
            {
                try {
                    aliment.initRecherche(getApplicationContext());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                if(!ajoutCourse)
                {
                    for(int i=0;i<aliment.fruits.size();i++)
                    {
                        gridLayoutF.addView(aliment.fruits.get(i));
                    }
                }
                else
                {
                    for(int i=0;i<aliment.fruitsCourse.size();i++)
                    {
                        gridLayoutF.addView(aliment.fruitsCourse.get(i));
                    }
                }

                ll_fruits.addView(gridLayoutF);
            }
        });

        //menu lait
        b_lait.setOnClickListener(view -> {
            if(aliment.lait.size() != 0 && ll_lait.getChildCount() != 0){
                gridLayoutPL.removeAllViews();
                ll_lait.removeAllViews();
            }
            else if (ll_lait.getChildCount() == 0)
            {
                try {
                    aliment.initRecherche(getApplicationContext());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

                if(!ajoutCourse)
                {
                    for (int i = 0; i < aliment.lait.size(); i++)
                    {
                        gridLayoutPL.addView(aliment.lait.get(i));
                    }
                }
                else
                {
                    for (int i = 0; i < aliment.laitCourse.size(); i++)
                    {
                        gridLayoutPL.addView(aliment.laitCourse.get(i));
                    }
                }
                ll_lait.addView(gridLayoutPL);
            }
        });

        //menu viandes
        b_viandes.setOnClickListener(view -> {
            if(aliment.viandes.size() != 0 && ll_viandes.getChildCount() != 0){
                gridLayoutV.removeAllViews();
                ll_viandes.removeAllViews();
            }
            else if (ll_viandes.getChildCount() == 0)
            {
                try {
                    aliment.initRecherche(getApplicationContext());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                if(!ajoutCourse)
                {
                    for(int i=0;i<aliment.viandes.size();i++)
                    {
                        gridLayoutV.addView(aliment.viandes.get(i));
                    }
                }
                else
                {
                    for(int i=0;i<aliment.viandesCourse.size();i++)
                    {
                        gridLayoutV.addView(aliment.viandesCourse.get(i));
                    }
                }

                ll_viandes.addView(gridLayoutV);
            }
        });

        //menu legumes
        b_legumes.setOnClickListener(view -> {
            if(aliment.legumes.size() != 0 && ll_legumes.getChildCount() != 0){
                gridLayoutL.removeAllViews();
                ll_legumes.removeAllViews();
            }
            else if (ll_legumes.getChildCount() == 0)
            {
                try {
                    aliment.initRecherche(getApplicationContext());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                if(!ajoutCourse)
                {
                    for(int i=0;i<aliment.legumes.size();i++)
                    {
                        gridLayoutL.addView(aliment.legumes.get(i));
                    }
                }
                else
                {
                    for(int i=0;i<aliment.legumesCourse.size();i++)
                    {
                        gridLayoutL.addView(aliment.legumesCourse.get(i));
                    }
                }

                ll_legumes.addView(gridLayoutL);
            }
        });

        //menu autres
        b_autre.setOnClickListener(view -> {
            if(aliment.autre.size() != 0 && ll_autre.getChildCount() != 0){
                gridLayoutA.removeAllViews();
                ll_autre.removeAllViews();
            }
            else if (ll_autre.getChildCount() == 0)
            {
                try {
                    aliment.initRecherche(getApplicationContext());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                if(!ajoutCourse)
                {
                    for(int i=0;i<aliment.autre.size();i++)
                    {
                        gridLayoutA.addView(aliment.autre.get(i));
                    }
                }
                else
                {
                    for(int i=0;i<aliment.autreCourse.size();i++)
                    {
                        gridLayoutA.addView(aliment.autreCourse.get(i));
                    }
                }

                ll_autre.addView(gridLayoutA);
            }
        });

        //bouton j'ai finis
        b_terminerChoixPlacards.setOnClickListener(view -> {
            aliment.initCocher();
            if(ajoutCourse)
            {
                intent = new Intent(this, Courses.class);
                startActivity(intent);
            }
            else
            {
                /*
                gridLayoutF.removeAllViews();
                gridLayoutL.removeAllViews();
                gridLayoutPL.removeAllViews();
                gridLayoutV.removeAllViews();
                gridLayoutA.removeAllViews();
                ll_legumes.removeAllViews();
                ll_viandes.removeAllViews();
                ll_lait.removeAllViews();
                ll_fruits.removeAllViews();
                ll_autre.removeAllViews();*/

                intent = new Intent(this, ListeRecette.class);
                startActivity(intent);
            }

        });

        //Barre de recherche
        et_recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()!=0) {
                    gridLayoutF.removeAllViewsInLayout();
                    gridLayoutL.removeAllViews();
                    gridLayoutPL.removeAllViews();
                    gridLayoutV.removeAllViews();
                    gridLayoutA.removeAllViews();
                    ll_legumes.removeAllViews();
                    ll_viandes.removeAllViews();
                    ll_lait.removeAllViews();
                    ll_fruits.removeAllViews();
                    ll_autre.removeAllViews();
                    ll_menuG.setVisibility(View.INVISIBLE);
                    ll_recherche.removeAllViews();
                    try {
                        aliment.initRecherche(getApplicationContext());
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    if(!ajoutCourse)
                    {
                        for (int indice = 0; indice < aliment.fruits.size(); indice++) {
                            if (aliment.nomFruits[indice].toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                                ll_recherche.addView(aliment.fruits.get(indice));
                            }
                        }

                        for (int indice = 0; indice < aliment.viandes.size(); indice++) {
                            if (aliment.nomViandes[indice].toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                                ll_recherche.addView(aliment.viandes.get(indice));
                            }
                        }

                        for (int indice = 0; indice < aliment.legumes.size(); indice++) {
                            if (aliment.nomLegumes[indice].toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                                ll_recherche.addView(aliment.legumes.get(indice));
                            }
                        }

                        for (int indice = 0; indice < aliment.lait.size(); indice++) {
                            if (aliment.nomLait[indice].toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                                ll_recherche.addView(aliment.lait.get(indice));
                            }
                        }

                        for (int indice = 0; indice < aliment.autre.size(); indice++) {
                            if (aliment.nomAutre[indice].toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                                ll_recherche.addView(aliment.autre.get(indice));
                            }
                        }
                    }
                    else
                    {
                        for (int indice = 0; indice < aliment.fruitsCourse.size(); indice++) {
                            if (aliment.nomFruits[indice].toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                                ll_recherche.addView(aliment.fruitsCourse.get(indice));
                            }
                        }

                        for (int indice = 0; indice < aliment.viandesCourse.size(); indice++) {
                            if (aliment.nomViandes[indice].toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                                ll_recherche.addView(aliment.viandesCourse.get(indice));
                            }
                        }

                        for (int indice = 0; indice < aliment.legumesCourse.size(); indice++) {
                            if (aliment.nomLegumes[indice].toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                                ll_recherche.addView(aliment.legumesCourse.get(indice));
                            }
                        }

                        for (int indice = 0; indice < aliment.laitCourse.size(); indice++) {
                            if (aliment.nomLait[indice].toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                                ll_recherche.addView(aliment.laitCourse.get(indice));
                            }
                        }

                        for (int indice = 0; indice < aliment.autreCourse.size(); indice++) {
                            if (aliment.nomAutre[indice].toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                                ll_recherche.addView(aliment.autreCourse.get(indice));
                            }
                        }
                    }


                }
                else if (charSequence.length() == 0 )
                {
                    ll_recherche.removeAllViews();
                    ll_menuG.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

}