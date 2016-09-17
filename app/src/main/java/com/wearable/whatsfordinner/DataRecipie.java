package com.wearable.whatsfordinner;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sujan on 9/14/2016.
 */
public class DataRecipie {
    private List<DataIngredient> ingredients = new ArrayList<DataIngredient>();
    private String recipieName = "";
    private Uri imageUri = null;

    public void DataRecipie(){
        for(int i=0; i<DataConstants.MAX_INGREDIENT_COUNT; i++){
            ingredients.add(new DataIngredient());
        }
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

    public void setIngredients(List<DataIngredient> newIngredients){
        this.ingredients = newIngredients;
    }
    public void setRecipieName(String name){
        this.recipieName = name;
    }
    public void setImageUri(Uri imageUri){
        this.imageUri = imageUri;
    }
}
