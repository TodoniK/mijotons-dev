package com.example.mijotons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Courses extends AppCompatActivity {
    Intent intent;

    LinearLayout ll_course;
    CheckBox cb_toutSelectionner;
    Button b_supp;
    EditText et_champRecherche;
    static int indice;


    static ArrayList<CheckBox> listeCheckBox = new ArrayList<>();
    static ArrayList<LinearLayout> listeLinearLayout = new ArrayList<>();
    ArrayList<LinearLayout> listeLinearLayoutRecherche = new ArrayList<>();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        ll_course = findViewById(R.id.ll_course);
        cb_toutSelectionner = findViewById(R.id.cb_toutSelectionner);
        b_supp = findViewById(R.id.b_supp);
        et_champRecherche = findViewById(R.id.et_champRecherche);

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
            et_champRecherche.setText("");
            for(int i = compteur-1 ; i>=0;i--)
            {
                if(listeCheckBox.get(i).isChecked())
                {
                    listeCheckBox.remove(i);
                    listeLinearLayout.remove(i);
                }
            }
            initCourse();

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

        initCourse();
    }

    public static void ajoutCourse(Context context, String nom,String quantite){

        if(checkPresence(nom)){
            String regex = "";
            EditText et = (EditText) listeLinearLayout.get(indice).getChildAt(1);
            Pattern p = Pattern.compile("[0-9]");
            Matcher matcher = p.matcher(quantite);
            while(matcher.find()){
                regex += matcher.group();
            }
            et.setText(String.valueOf(Integer.parseInt(et.getText().toString())+Integer.parseInt(regex)));
        }
        else
        {
            String regex = "";
            LinearLayout linearLayout = new LinearLayout(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,0.7f);
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(nom);
            EditText editText =  new EditText(context);
            Pattern p = Pattern.compile("[0-9]");
            Matcher matcher = p.matcher(quantite);
            while(matcher.find()){
                regex += matcher.group();
            }
            editText.setText(regex);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            TextView textView = new TextView(context);
            regex="";
            p = Pattern.compile("[a-z]");
            matcher = p.matcher(quantite);
            while(matcher.find()){
                regex += matcher.group();
            }
            textView.setText(regex);
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

    public void initCourse()
    {
        ll_course.removeAllViews();
        for(LinearLayout linearLayout : listeLinearLayout){
            ll_course.addView(linearLayout);
        }
    }

    public void initRechercheCourse()
    {
        ll_course.removeAllViews();
        for(LinearLayout linearLayout : listeLinearLayoutRecherche)
        {
            ll_course.addView(linearLayout);
        }
    }

    static public boolean checkPresence(String nom)
    {
        boolean presence = false;
        for(CheckBox cb : listeCheckBox)
        {
            if(cb.getText().equals(nom))
            {
                indice = listeCheckBox.indexOf(cb);
                presence = true;
            }
        }
        return presence;
    }
}