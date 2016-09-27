package com.wearable.whatsfordinner;

import android.net.Uri;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhiviya on 9/14/2016.
 */
public class DataRecipie {
    private List<DataIngredient> ingredients = new ArrayList<DataIngredient>();
    private List<DataIngredient> nutrients = new ArrayList<>();
    private String recipieName = "";
    private Uri imageUri = null;
    private String instructions = "";

    public DataRecipie(){
//        for(int i=0; i<DataConstants.MAX_INGREDIENT_COUNT; i++){
//            ingredients.add(new DataIngredient());
//        }
    }

    public List<DataIngredient> getIngredients(){
        return ingredients;
    }
    public String getRecipieName(){
        return recipieName;
    }
    public Uri getImageUri(){
        return imageUri;
    }
    public String getInstructions() { return  instructions; }
    public List<DataIngredient> getNutrients() { return nutrients; }

//    public void addIngredient(DataIngredient newIngredient){
//        ingredients.add(newIngredient);
//        DataHolder.getInstance().addNewIngredient(newIngredient.getName());
//    }
    public void setIngredients(List<DataIngredient> newIngredients){
        this.ingredients = newIngredients;
    }
    public void setRecipieName(String name){
        this.recipieName = name;
    }
    public void setImageUri(Uri imageUri){
        this.imageUri = imageUri;
    }
    public void setInstructions(String instructions){ this.instructions = instructions; }
    public DataRecipie getClone(){
        DataRecipie rClone = new DataRecipie();
        rClone.setRecipieName(this.getRecipieName());
        rClone.setInstructions(this.getInstructions());
        rClone.setImageUri(this.getImageUri());
        ArrayList<DataIngredient> inList = new ArrayList<>();
        for(int i=0; i<this.getIngredients().size(); i++){
            inList.add(this.getIngredients().get(i).getClone());
        }
        rClone.setIngredients(inList);
        ArrayList<DataIngredient> nList = new ArrayList<>();
        for(int i=0; i<this.getNutrients().size(); i++){
            nList.add(this.getNutrients().get(i).getClone());
        }
        rClone.setNutrients(nList);
        return rClone;
    }
    public void setNutrients(List<DataIngredient> n){this.nutrients = n; }
}
