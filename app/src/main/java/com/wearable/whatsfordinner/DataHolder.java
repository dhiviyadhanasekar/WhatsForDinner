package com.wearable.whatsfordinner;

import android.util.Log;

import java.lang.reflect.Array;
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

    //recipies screen data
    private static Map<String, Integer> recipiesForMealPlan = new HashMap<>();
    private static Map<String, DataIngredient> groceriesToShop = new HashMap<>();


    //add dish screen data
    private static Map<String,Boolean> ingredientNames = new HashMap<String, Boolean>();
    private static Map<String, DataRecipie> recipieNames = new HashMap<String, DataRecipie>();
//    private static List<DataRecipie> recipies = new ArrayList<DataRecipie>();
    static {
        populateDummyRecipies();
        injectMockDataForTesting();
    }

    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}

    public DataIngredient[] getGroceriesToShop(){
        Set keys = recipiesForMealPlan.keySet();
        String[] recipiesToAdd = (String[]) keys.toArray(new String[keys.size()]);
        for(int i=0; i<recipiesToAdd.length; i++){
            String rName = recipiesToAdd[i];
            rName = rName.toLowerCase();
            float rCount =  recipiesForMealPlan.get(rName);
            DataRecipie dataRecipie = recipieNames.get(rName);
            for(int j=0; j<dataRecipie.getIngredients().size(); j++){
                DataIngredient dataIngredient = dataRecipie.getIngredients().get(j);
                String inName = dataIngredient.getName().toLowerCase();
                if(groceriesToShop.containsKey(inName)){
                    DataIngredient groceryIn = groceriesToShop.get(inName);
                    groceryIn.setQuantity( groceryIn.getQuantity() + rCount*dataIngredient.getQuantity());
                    groceriesToShop.put(inName, groceryIn );
                } else {
                    groceriesToShop.put(inName, dataIngredient.getClone());
                }
            }
        }

//        return groceriesToShop;
        Set keysGroceries = groceriesToShop.keySet();
        String[] groceriesToAdd = (String[]) keysGroceries.toArray(new String[keysGroceries.size()]);
        DataIngredient[] groceriesToDisplay = new DataIngredient[keysGroceries.size()];

        for(int i=0; i<groceriesToAdd.length; i++){
            groceriesToDisplay[i] = (groceriesToShop.get(groceriesToAdd[i]));
        }
        return groceriesToDisplay;
    }

    private static void injectMockDataForTesting(){
        recipiesForMealPlan.put("eggs toast", 1);
        recipiesForMealPlan.put("banana bread", 2);
    }
    private static void populateDummyRecipies() {
        DataRecipie bananaBread = new DataRecipie();
        List<DataIngredient> bananaBreadIn = new ArrayList<>();
        bananaBreadIn.add(new DataIngredient("bread", 1, "loaves"));
        bananaBread.setIngredients( ( List<DataIngredient>) bananaBreadIn);
        bananaBread.setRecipieName("banana bread");
        bananaBread.setInstructions("Cream together butter and sugar."
        +"\nAdd eggs and crushed bananas.\nCombine well.\nSift together flour, soda and salt."+
                " Add to creamed mixture. Add vanilla.\nPour into greased and floured loaf pan."+
                "\nBake at 350 degrees for 60 minutes.\nKeeps well, refrigerated.");
        recipieNames.put("banana bread", bananaBread);
        ingredientNames.put("bread", true);

        DataRecipie eggsToast = new DataRecipie();
        List<DataIngredient> eggsToastIn = new ArrayList<>();
        eggsToastIn.add(new DataIngredient("eggs", 5, "eggs"));
        eggsToastIn.add(new DataIngredient("bread", 1, "loaves"));
        eggsToast.setIngredients( ( List<DataIngredient>) eggsToastIn);
        eggsToast.setRecipieName("eggs toast");
        eggsToast.setInstructions("Mix eggs and put them over bread. toast the bread now. ");
        recipieNames.put("eggs toast", eggsToast);
        ingredientNames.put("eggs", true);
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

    public void addToMealPlan(String recipieName){
        recipieName = recipieName.toLowerCase();
        Integer val = recipiesForMealPlan.get(recipieName);
        if(val == null){
            recipiesForMealPlan.put(recipieName, 1);
        } else {
            recipiesForMealPlan.put(recipieName, val+1);
        }
    }
}
