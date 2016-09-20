package com.wearable.whatsfordinner;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dhiviya on 9/14/2016.
 */
public class DataHolder {

    private static Map<String,Boolean> ingredientNames = new HashMap<String, Boolean>();
    private static Map<String, DataRecipie> recipieNames = new HashMap<String, DataRecipie>();
//    private static List<DataRecipie> recipies = new ArrayList<DataRecipie>();
    {
        populateDummyRecipies();
    }

    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}

    private static void populateDummyRecipies() {
        DataRecipie hamburger = new DataRecipie();
        recipieNames.put("ham", hamburger);
    }

    public boolean recipieExists(String newRecipieName){
        newRecipieName = newRecipieName.toLowerCase();
        if(recipieNames.containsKey(newRecipieName)) return true;
        return false;
    }

    public DataRecipie getRecipie(String recipieName){
        recipieName = recipieName.toLowerCase();
        if(recipieNames.containsKey(recipieName)) return recipieNames.get(recipieName);
        return null;
    }

    public void printRecipies(){
        Object[] rNames = recipieNames.keySet().toArray();
        for(int i=0; i<rNames.length; i++) {
            Log.v("DataHolder", "recipie => " + rNames[i]);
        }
    }

    public void saveRecipie(DataRecipie r){
        recipieNames.put(r.getRecipieName().toLowerCase(), r);
//        printRecipies();
    }

    public void addNewIngredient(String newIngredient){
        newIngredient = newIngredient.toLowerCase();
        if(ingredientNames.containsKey(newIngredient)) return;
        ingredientNames.put(newIngredient, true);
    }

    public String[] getAllRecipies(){
        Set keys = recipieNames.keySet();
        String[] retValue = (String[]) keys.toArray(new String[keys.size()]);
        return retValue;
    }

    public String[] getAllIngredients(){
        Set keys = ingredientNames.keySet();
        return (String[]) keys.toArray(new String[keys.size()]);
    }
}
