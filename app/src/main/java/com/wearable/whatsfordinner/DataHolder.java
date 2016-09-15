package com.wearable.whatsfordinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dhiviya on 9/14/2016.
 */
public class DataHolder {

    private static Map<String,String> ingredientNames = new HashMap<String, String>();
    private static Map<String, DataRecipie> recipieNames = new HashMap<String, DataRecipie>();
//    private static List<DataRecipie> recipies = new ArrayList<DataRecipie>();
    {
        populateDummyRecipies();
    }

    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}

    private static void populateDummyRecipies() {
        DataRecipie hamburger = new DataRecipie();
        recipieNames.put("hamburger", hamburger);
    }

    public boolean recipieExists(String newRecipieName){
        newRecipieName = newRecipieName.toLowerCase();
        if(recipieNames.containsKey(newRecipieName)) return true;
        return false;
    }
}