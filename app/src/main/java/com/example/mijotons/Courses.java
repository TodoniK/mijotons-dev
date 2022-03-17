package com.example.mijotons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mijotons.classBaseDonnees.aliment;
import com.example.mijotons.classBaseDonnees.readJson;
import com.example.mijotons.classBaseDonnees.recette;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Courses extends AppCompatActivity {
    Intent intent;

    LinearLayout ll_course;
    CheckBox cb_toutSelectionner;
    Button b_supp;
    EditText et_champRecherche;
    ScrollView sv_course;
    ImageButton ib_ajouterProduit;


    ArrayList<CheckBox> listeCheckBox = new ArrayList<>();
    ArrayList<LinearLayout> listeLinearLayout = new ArrayList<>();
    ArrayList<LinearLayout> listeLinearLayoutRecherche = new ArrayList<>();
    static ArrayList<String> listeNom = new ArrayList<>();
    static ArrayList<Integer> listeQuantite = new ArrayList<>();
    static ArrayList<String> listeSuffixe = new ArrayList<>();


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        ll_course = findViewById(R.id.ll_course);
        cb_toutSelectionner = findViewById(R.id.cb_toutSelectionner);
        b_supp = findViewById(R.id.b_supp);
        et_champRecherche = findViewById(R.id.et_champRecherche);
        sv_course = findViewById(R.id.sv_course);
        ib_ajouterProduit = findViewById(R.id.ib_ajouterProduit);

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

        cb_toutSelectionner.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b)
            {
                for(CheckBox cb : listeCheckBox)
                {
                    cb.setChecked(true);
                }
            }
            else
            {
                for(CheckBox cb : listeCheckBox)
                {
                    cb.setChecked(false);
                }
            }
        });

        b_supp.setOnClickListener(view -> {
            int compteur = listeCheckBox.size();

            for(int i = compteur-1;i>=0;i--)
            {
                if(listeCheckBox.get(i).isChecked())
                {
                    listeNom.remove(i);
                    listeQuantite.remove(i);
                    listeSuffixe.remove(i);
                }
            }

            initCourse();

            if(!et_champRecherche.getText().toString().equals(""))
            {
                et_champRecherche.setText("");
            }

            try {
                readJson.enregistrementCourse(listeNom,listeQuantite,listeSuffixe,getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        et_champRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listeLinearLayoutRecherche.clear();
                if(charSequence.length() != 0)
                {
                    for(CheckBox cb : listeCheckBox)
                    {
                        if(cb.getText().toString().startsWith(charSequence.toString()))
                        {
                            listeLinearLayoutRecherche.add(listeLinearLayout.get(listeCheckBox.indexOf(cb)));
                        }
                    }
                    initRechercheCourse();
                }
                else
                {
                    initCourse();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ib_ajouterProduit.setOnClickListener(view -> {
            MainActivity.ajoutCourse = true;
            aliment.cocherAliment.clear();
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        if(MainActivity.ajoutCourse)
        {
            for(String nomAlim : aliment.cocherAliment)
            {
                for(recette rec : ListeRecette.listeRecette)
                {
                    for(int i = 0 ; i< rec.getAliment().length ; i++)
                    {
                        if(rec.getAliment()[i].equals(nomAlim))
                        {
                            String quantite = rec.getQuantite()[i];
                            String regex = "";
                            Pattern p = Pattern.compile("[a-z]");
                            Matcher matcher = p.matcher(quantite);
                            while(matcher.find()){
                                regex += matcher.group();
                            }
                            ajoutCourse(nomAlim,"0"+regex);
                        }
                    }
                }
            }
            MainActivity.ajoutCourse = false;
        }

        initCourse();
        try {
            readJson.enregistrementCourse(listeNom,listeQuantite,listeSuffixe,getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void ajoutCourse(String nom,String quantite){

        if(listeNom.contains(nom)){
            String regex = "";
            Pattern p = Pattern.compile("[0-9]");
            Matcher matcher = p.matcher(quantite);
            while(matcher.find()){
                regex += matcher.group();
            }

            int index = listeNom.indexOf(nom);
            listeQuantite.set(index,listeQuantite.get(index)+Integer.parseInt(regex));
        }
        else
        {
            String regex = "";
            Pattern p = Pattern.compile("[0-9]");
            Matcher matcher = p.matcher(quantite);
            while(matcher.find()){
                regex += matcher.group();
            }
            String regex2 = "";
            p = Pattern.compile("[a-z]");
            matcher = p.matcher(quantite);
            while(matcher.find()){
                regex2 += matcher.group();
            }

            listeNom.add(nom);
            listeQuantite.add(Integer.parseInt(regex));
            listeSuffixe.add(regex2);
        }

    }

    public void initCourse()
    {
        ll_course.removeAllViews();
        initialisationListe();
        for(LinearLayout ll : listeLinearLayout)
        {
            ll_course.addView(ll);
        }
    }

    public void initialisationListe(){
        listeLinearLayout.clear();
        listeCheckBox.clear();

        for(int i=0 ; i<listeNom.size();i++)
        {
            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,0.7f);
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setText(listeNom.get(i));
            EditText editText =  new EditText(getApplicationContext());
            editText.setText(String.valueOf(listeQuantite.get(i)));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            TextView textView = new TextView(getApplicationContext());
            textView.setText(listeSuffixe.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
            linearLayout.addView(checkBox,layoutParams);
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,0.2f);
            linearLayout.addView(editText,layoutParams);
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,0.1f);
            linearLayout.addView(textView,layoutParams);
            listeCheckBox.add(checkBox);
            listeLinearLayout.add(linearLayout);
        }

    }

    public void initRechercheCourse()
    {
        ll_course.removeAllViews();
        for(LinearLayout ll : listeLinearLayoutRecherche)
        {
            ll_course.addView(ll);
        }
    }

}