package com.example.mijotons.classBaseDonnees;

import android.content.Context;

import com.example.mijotons.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
        return new recette(Integer.parseInt(nom),recette.getString("Nom"),recette.getJSONArray("Aliments"),recette.getJSONArray("Quantite"),recette.getString("Image"),recette.getString("Etape"),recette.getString("Temps"),recette.getInt("Personnes"));
    }

    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void enregistrement(ArrayList<recette> listeRecette,String nomBaseDonnees,Context context) throws IOException {
        JSONObject obj = new JSONObject() ;

        try {
            for(int i = 0 ;i<listeRecette.size();i++){
                obj.put("Recette"+ i,listeRecette.get(i).getNom());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String userString = obj.toString();
        File file = new File(context.getFilesDir(),nomBaseDonnees);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(userString);
        bufferedWriter.close();
    }

    public static ArrayList<String> lireEnregistrement(String nomBaseDonnees, Context context) throws IOException, JSONException {
        File file = new File(context.getFilesDir(),nomBaseDonnees);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        // This responce will have Json Format String
        String responce = stringBuilder.toString();

        JSONObject jsonObject  = new JSONObject(responce);
        ArrayList<String> nomRecette = new ArrayList<>();
        //Java Object
        for(int i = 0 ;i<jsonObject.length();i++){
            nomRecette.add(jsonObject.getString("Recette"+ i));
        }

        return nomRecette;
    }

    public static void enregistrementCourse(ArrayList<String> nom, ArrayList<Integer> quantite,ArrayList<String> suffixe,Context context) throws IOException {
        JSONObject obj = new JSONObject() ;

        try {
            for(int i = 0 ;i<nom.size();i++){
                obj.put("Nom"+ i,nom.get(i));
                obj.put("Quantite"+i,quantite.get(i));
                obj.put("Suffixe"+i,suffixe.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String userString = obj.toString();
        File file = new File(context.getFilesDir(),"Course");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(userString);
        bufferedWriter.close();
    }

    public static ArrayList<String> lireEnregistrementCourse(String nomArraylist, Context context) throws IOException, JSONException {
        File file = new File(context.getFilesDir(),"Course");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        // This responce will have Json Format String
        String responce = stringBuilder.toString();

        JSONObject jsonObject  = new JSONObject(responce);
        ArrayList<String> arrayList = new ArrayList<>();
        //Java Object
        for(int i = 0 ;i<jsonObject.length()/3;i++){
            arrayList.add(jsonObject.getString(nomArraylist+i));
        }

        return arrayList;
    }

    public static ArrayList<Integer> lireEnregistrementCourseQuantite(String nomArraylist, Context context) throws IOException, JSONException {
        File file = new File(context.getFilesDir(),"Course");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        // This responce will have Json Format String
        String responce = stringBuilder.toString();

        JSONObject jsonObject  = new JSONObject(responce);
        ArrayList<Integer> arrayList = new ArrayList<>();
        //Java Object
        for(int i = 0 ;i<jsonObject.length()/3;i++){
            arrayList.add(jsonObject.getInt(nomArraylist+i));
        }

        return arrayList;
    }
}
