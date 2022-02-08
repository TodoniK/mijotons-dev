package com.example.mijotons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;


public class Chargement extends AppCompatActivity {

    private final int DUREE_ECRAN_CHARGEMENT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }

        };
        new Handler().postDelayed(runnable,DUREE_ECRAN_CHARGEMENT);

    }
}