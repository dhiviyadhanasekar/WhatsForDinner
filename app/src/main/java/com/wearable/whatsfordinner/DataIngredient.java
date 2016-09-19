package com.wearable.whatsfordinner;

/**
 * Created by Sujan on 9/14/2016.
 */
public class DataIngredient {
    private String name;
    private String unit;
    private float quantity;

    public DataIngredient(String inName, float inQty, String inUnit) {
        this.name = inName.toLowerCase();
        this.unit = inUnit.toLowerCase();
        this.quantity = inQty;
    }

    public String getName(){
        return this.name;
    }

    public String getUnit(){
        return this.unit;
    }

    public float getQuantity(){
        return quantity;
    }
}
