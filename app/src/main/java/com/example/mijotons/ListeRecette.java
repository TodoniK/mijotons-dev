package com.example.mijotons;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_HORIZONTAL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowMetrics;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mijotons.classBaseDonnees.aliment;
import com.example.mijotons.classBaseDonnees.readJson;
import com.example.mijotons.classBaseDonnees.recette;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class ListeRecette extends AppCompatActivity {

    Intent intent;
    int filtre = 0;

    ArrayList<recette> listeRecette = new ArrayList<>();
    ArrayList<recette> listeRecetteAfficher =  new ArrayList<>();

    Spinner s_filtre;
    LinearLayout ll_listeRecette;
    EditText et_recherche;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_recette);

        ll_listeRecette = findViewById(R.id.ll_listeRecette);
        s_filtre = findViewById(R.id.s_filtre);
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
                case R.id.action_favoris:
                    intent = new Intent(this, Favoris.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

        //Initialisation de la liste des recettes
        try {
            for (int i = 1;i<25;i++){
                listeRecette.add(readJson.readBaseRecette(getApplicationContext(),String.valueOf(i)));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        //Filtre
        s_filtre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filtre=s_filtre.getSelectedItemPosition();
                initListeRecette();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        et_recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    initListeRecette();
                }
                else
                {
                    initRecherche(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Initialisation de  l'affichage de la liste des recettes
        initListeRecette();


    }

    private void initRecherche(CharSequence charSequence) {
        ll_listeRecette.removeAllViews();

        if (listeRecetteAfficher.size() != 0){
            int compteur=0;
            //Paramètre des boutons pour leur taille
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels/2-45,getResources().getDisplayMetrics().widthPixels/3,0f);
            params.setMargins(15,5,15,5);
            LinearLayout linearLayout = null;
            for(recette rec : listeRecetteAfficher) {
                if (rec.getNom().toUpperCase().startsWith(charSequence.toString().toUpperCase())) {
                    // Tout les 2 éléments dans un layout j'en créer un nouveau
                    if (compteur % 2 == 0) {
                        linearLayout = new LinearLayout(getApplicationContext());
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout.setPadding(10, 10, 10, 10);
                        ll_listeRecette.addView(linearLayout);
                    }
                    //Création du bouton
                    Button button = new Button(getApplicationContext());
                    button.setText(rec.getNom());
                    button.setTextColor(getColor(R.color.white));
                    button.setBackgroundResource(getResources().getIdentifier(rec.getImage(), "drawable", getPackageName()));
                    button.setGravity(CENTER_HORIZONTAL);
                    button.setLayoutParams(params);
                    //Redirection vers la page du détaille
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Historique.ajoutHistorique(rec);
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        }
                    });
                    //Ajout du bouton au layout
                    linearLayout.addView(button);
                    compteur++;
                }
            }
        }

        if(ll_listeRecette.getChildCount() == 0){
            TextView textView = new TextView(getApplicationContext());
            textView.setText("Aucune recette correspondante à votre recherche!");
            textView.setTextSize(25);
            ll_listeRecette.addView(textView);
        }


    }


    public void initListeRecette(){
        //Trouver les recettes qui correspondent au aliment cocher
        listeRecetteAfficher.clear();
        for(recette rec : listeRecette)
        {
            int quantite = rec.getAliment().length;
            int compteur = 0;
            for (String alim : rec.getAliment())
            {
                if(aliment.cocherAliment.contains(alim))
                {
                    compteur++;
                }
            }
            if ((compteur + filtre) >= quantite){
                listeRecetteAfficher.add(rec);
            }
        }

        ll_listeRecette.removeAllViews();

        if (listeRecetteAfficher.size() != 0){
            int compteur=0;
            //Paramètre des boutons pour leur taille
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels/2-45,getResources().getDisplayMetrics().widthPixels/3,0f);
            params.setMargins(15,5,15,5);
            LinearLayout linearLayout = null;

            for(recette rec : listeRecetteAfficher)
            {
                // Tout les 2 éléments dans un layout j'en créer un nouveau
                if(compteur%2 == 0){
                    linearLayout = new LinearLayout(getApplicationContext());
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setPadding(10,10,10,10);
                    ll_listeRecette.addView(linearLayout);
                }
                //Création du bouton
                Button button = new Button(getApplicationContext());
                button.setText(rec.getNom());
                button.setTextColor(getColor(R.color.white));
                button.setBackgroundResource(getResources().getIdentifier(rec.getImage(),"drawable",getPackageName()));
                button.setGravity(CENTER_HORIZONTAL);
                button.setLayoutParams(params);
                //Redirection vers la page du détaille
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Historique.ajoutHistorique(rec);
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                //Ajout du bouton au layout
                linearLayout.addView(button);

                compteur++;
            }
        }
        else
        {
            TextView textView = new TextView(getApplicationContext());
            textView.setText("Aucune recette correspondante !");
            textView.setTextSize(25);
            ll_listeRecette.addView(textView);
        }
    }

}