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


    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}

    static {
        populateDummyRecipies();
        holder.injectMockDataForTesting();
    }

    public DataIngredient[] getGroceriesToShop(){

        Set keysGroceries = groceriesToShop.keySet();
        String[] groceriesToAdd = (String[]) keysGroceries.toArray(new String[keysGroceries.size()]);
        DataIngredient[] groceriesToDisplay = new DataIngredient[keysGroceries.size()];

        for(int i=0; i<groceriesToAdd.length; i++){
            groceriesToDisplay[i] = (groceriesToShop.get(groceriesToAdd[i]));
        }
        return groceriesToDisplay;
    }

    public DataIngredient getGroceryItem(String gname){
        gname = gname.toLowerCase();
        return groceriesToShop.get(gname);
    }

    private void addRecipieIngredientsToGroceries(String rName, float rCount) {
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

    //todo: remove this test code
    private void injectMockDataForTesting(){
        addToMealPlan("eggs toast");
        addToMealPlan("banana bread");
//        addToMealPlan("banana bread");
//        for(int i=0; i<20; i++){
//            groceriesToShop.put("test " + i, new DataIngredient("test " + i, i, "pieces"));
//        }
//        recipiesForMealPlan.put("eggs toast", 1);
//        recipiesForMealPlan.put("banana bread", 2);
    }

    private static void populateDummyRecipies() {
        DataRecipie bananaBread = new DataRecipie();
        List<DataIngredient> bananaBreadIn = new ArrayList<>();
        bananaBreadIn.add(new DataIngredient("bread", 1, "loaves"));
        bananaBreadIn.add(new DataIngredient("banana", 20, "hands"));
        bananaBreadIn.add(new DataIngredient("baking powder", 4, "lbs"));
        bananaBreadIn.add(new DataIngredient("cashews", 2, "lbs"));
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
        eggsToastIn.add(new DataIngredient("eggs", 5, "dozens"));
        eggsToastIn.add(new DataIngredient("bread", 1, "loaves"));
        eggsToastIn.add(new DataIngredient("vanilla", 1, "oz"));
        eggsToastIn.add(new DataIngredient("sugar", 5, "lbs"));
        eggsToastIn.add(new DataIngredient("cinnamon", (float)0.5, "lbs"));
        eggsToastIn.add(new DataIngredient("maple syrup", 30, "oz"));
        eggsToastIn.add(new DataIngredient("strawberries", 10, "pieces"));
        eggsToastIn.add(new DataIngredient("grapes", 10, "pieces"));
        eggsToastIn.add(new DataIngredient("almonds", 10, "pieces"));
        eggsToastIn.add(new DataIngredient("milk", 10, "gallons"));
        eggsToastIn.add(new DataIngredient("salt", 1, "lb"));
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
        addRecipieIngredientsToGroceries(recipieName, 1);
    }
}
