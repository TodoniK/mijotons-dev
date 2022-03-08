package com.example.mijotons.classBaseDonnees;

import android.content.Context;
import android.location.Address;
import android.util.Log;

import com.example.mijotons.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class readJson {

    public static String[] readBaseAliment(Context context,String nom) throws IOException, JSONException {

        // Read content of company.json
        String jsonText = readText(context, R.raw.basealiment);

        JSONObject jsonRoot = new JSONObject(jsonText);

        JSONArray jsonArray = jsonRoot.getJSONArray(nom);
        String[] liste = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            liste[i] = jsonArray.getString(i);
        }

        return liste;
    }

    public static recette readBaseRecette(Context context,String nom) throws IOException, JSONException {

        // Read content of company.json
        String jsonText = readText(context, R.raw.baserecette);

        JSONObject jsonRoot = new JSONObject(jsonText);
        //OKAY jusqu'ici
        JSONObject recette = jsonRoot.getJSONObject(nom);
        return new recette(Integer.valueOf(nom),recette.getString("Nom"),recette.getJSONArray("Aliments"),recette.getJSONArray("Quantite"),recette.getString("Image"),recette.getString("Etape"));
    }

    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s= null;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}