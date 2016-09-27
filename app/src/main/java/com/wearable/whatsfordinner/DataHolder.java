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
    public static String eatingOut = "eating out";
    private static Map<String, Integer> recipiesForMealPlan = new HashMap<>();
    private static Map<String, DataIngredient> groceriesToShop = new HashMap<>();
    private static String[][] mealsSelected = new String[7][3];


    //add dish screen data
    private static Map<String,Boolean> ingredientNames = new HashMap<String, Boolean>();
    private static Map<String, DataRecipie> recipieNames = new HashMap<String, DataRecipie>();
    private static Map<String, DataIngredient> targetNutrients = new HashMap<>();
    private static ArrayList<DataIngredient> mealPlanNutrients = new ArrayList<>();
//    private static Map<String, DataIngredient> nutrientsFromMealsSelected = new HashMap<>();
//    private static List<DataRecipie> recipies = new ArrayList<DataRecipie>();


    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}

    static {
        populateDummyRecipies();
        initMealsSelected();
//        holder.injectMockDataForTesting();
    }

    private static void initMealsSelected(){
        for(int i=0; i<7; i++){
            for(int j=0; j<3; j++){
                mealsSelected[i][j] = eatingOut;
            }
        }
    }

    public String getMealSelected(int row, int col){
        return mealsSelected[row][col];
    }
    public void setMealSelected(int row, int col, String recipieName){
        mealsSelected[row][col] = recipieName;
    }

    public void addNewNutrient(DataIngredient inName){
        if(targetNutrients.containsKey(inName.getName())) return;
        targetNutrients.put(inName.getName().toLowerCase(),
                new DataIngredient(inName.getName().toLowerCase(), 0, inName.getUnit().toLowerCase()));
    }
    public String[] getAvailableMeals(){
        Set keys = recipiesForMealPlan.keySet();
        String[] possibleMeals =  (String[]) keys.toArray(new String[keys.size()]);
        ArrayList<String> mealsToShow = new ArrayList<String>();
        for(int i=0; i<possibleMeals.length; i++){
            String r = possibleMeals[i];
            if(recipiesForMealPlan.get(r) == 0) continue;
            mealsToShow.add(r);
        }
        mealsToShow.add(eatingOut);
        return mealsToShow.toArray(new String[mealsToShow.size()]);
    }

//    public void addbackRecipie(String r){
//        if(recipiesForMealPlan.containsKey(r)){
//            Integer val = recipiesForMealPlan.get(r);
//            recipiesForMealPlan.put(r, val+1);
//        } else recipiesForMealPlan.put(r, val+1);
//    }

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
        addToMealPlan("banana bread");
//        addToMealPlan("banana bread");
//        for(int i=0; i<20; i++){
//            groceriesToShop.put("test " + i, new DataIngredient("test " + i, i, "pieces"));
//        }
//        recipiesForMealPlan.put("eggs toast", 1);
//        recipiesForMealPlan.put("banana bread", 2);
    }

    public void updateTargetNutrient(String nName, DataIngredient in){
//        if(targetNutrients.containsKey(nName)){
            targetNutrients.put(nName, in);
//        }
    }

    private Map<String, DataIngredient> updateNutrientsFromMealPlan(){
        ArrayList<String> tempR = new ArrayList<>();
        for(int i=0; i<mealsSelected.length; i++){
            for(int j=0; j<mealsSelected[i].length; j++){
                String r = mealsSelected[i][j];
                if(r.equals(eatingOut)) continue;
                tempR.add(r);
            }
        }
        Map<String, DataIngredient> nutrientsFromMealsSelected = new HashMap<>();
        for(int i=0; i<tempR.size(); i++){
            String r = tempR.get(i);
            List<DataIngredient> inList = recipieNames.get(r).getNutrients();
            for(int j=0; j<inList.size(); j++){
                DataIngredient in  = inList.get(j);
                DataIngredient toUpdate = nutrientsFromMealsSelected.get(in.getName());
                if(nutrientsFromMealsSelected.containsKey(in.getName())){
                    toUpdate.setQuantity(toUpdate.getQuantity()+in.getQuantity());
                    nutrientsFromMealsSelected.put(in.getName(), toUpdate);
                } else  nutrientsFromMealsSelected.put(in.getName(), in.getClone());
            }
        }
        return nutrientsFromMealsSelected;
    }

    public ArrayList<DataIngredient> getMealPlanNutrients(){
        return mealPlanNutrients;
    }

    public void updateMealPlanNutrients(){
        Map<String, DataIngredient> nutrientsFromMealsSelected = updateNutrientsFromMealPlan();
        ArrayList<DataIngredient> list = new ArrayList<>();
        Set keys = nutrientsFromMealsSelected.keySet();
        String[] possibleMeals =  (String[]) keys.toArray(new String[keys.size()]);
        for(int i=0; i<possibleMeals.length; i++){
            String r = possibleMeals[i];
            list.add(nutrientsFromMealsSelected.get(r));
        }
        mealPlanNutrients = list;
    }

    private HashMap<String, DataIngredient> cloneTargetNutrients(){
        HashMap<String, DataIngredient> clone = new HashMap<>();
        Set keys = targetNutrients.keySet();
        String[] possibleMeals =  (String[]) keys.toArray(new String[keys.size()]);
        for(int i=0; i<possibleMeals.length; i++){
            String r = possibleMeals[i];
            clone.put(r, targetNutrients.get(r).getClone());
        }
        return clone;
    }
    public String hasTargetNutrientReached(){
        HashMap<String, DataIngredient> targetClone = cloneTargetNutrients();


        String result = "Yes";
        for(int i=0; i<mealPlanNutrients.size(); i++){
            DataIngredient in = mealPlanNutrients.get(i);
            if(targetClone.containsKey(in.getName())){
                DataIngredient targetIn = targetClone.get(in.getName());
                targetIn.setQuantity(targetIn.getQuantity()-in.getQuantity());
                if(targetIn.getQuantity() < 0) return "No";
            }
        }

        Set keys = targetClone.keySet();
        String[] possibleMeals =  (String[]) keys.toArray(new String[keys.size()]);
        for(int i=0; i<possibleMeals.length; i++){
            String r = possibleMeals[i];
            float val = targetClone.get(r).getQuantity();

            if(Math.abs(val)>0.09) //continue;
                return "No";
        }
        return "Yes";
    }

    public ArrayList<DataIngredient> getTargetNutrients(){
        ArrayList<DataIngredient> list = new ArrayList<>();
        Set keys = targetNutrients.keySet();
        String[] possibleMeals =  (String[]) keys.toArray(new String[keys.size()]);
        for(int i=0; i<possibleMeals.length; i++){
            String r = possibleMeals[i];
            list.add(targetNutrients.get(r));
        }
        return list;
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
        ingredientNames.put("banana", true);
        ingredientNames.put("baking powder", true);
        ingredientNames.put("cashews", true);

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
//        eggsToastIn.add(new DataIngredient("salt", 1, "lb"));
        eggsToast.setIngredients( ( List<DataIngredient>) eggsToastIn);
        eggsToast.setRecipieName("eggs toast");
        List<DataIngredient> eggsToastNu = new ArrayList<>();
        eggsToastNu.add(new DataIngredient("carbohydrates", 1, "cal"));
        holder.addNewNutrient(new DataIngredient("carbohydrates", 1, "cal"));
        eggsToast.setNutrients(eggsToastNu);
        eggsToast.setInstructions("Mix eggs and put them over bread. toast the bread now. ");
        recipieNames.put("eggs toast", eggsToast);
        ingredientNames.put("eggs", true);
        ingredientNames.put("vanilla", true);
        ingredientNames.put("sugar", true);
        ingredientNames.put("cinnamon", true);
        ingredientNames.put("maple syrup", true);
        ingredientNames.put("strawberries", true);
        ingredientNames.put("grapes", true);
        ingredientNames.put("almonds", true);
        ingredientNames.put("milk", true);
//        ingredientNames.put("salt", true);
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
        incrementMealCount(recipieName);
        addRecipieIngredientsToGroceries(recipieName, 1);
    }

    public void incrementMealCount(String recipieName) {
        Integer val = recipiesForMealPlan.get(recipieName);
        if(val == null){
            recipiesForMealPlan.put(recipieName, 1);
        } else {
            recipiesForMealPlan.put(recipieName, val+1);
        }
    }

    public void decrementMealCount(String recipieName) {
        Integer val = recipiesForMealPlan.get(recipieName);
        if(val != null && val > 0){
            recipiesForMealPlan.put(recipieName, val-1);
        }
    }
}
