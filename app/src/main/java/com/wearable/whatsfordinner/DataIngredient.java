package com.wearable.whatsfordinner;

/**
 * Created by Dhiviya on 9/14/2016.
 */
public class DataIngredient {
    private String name ="";
    private String unit ="";
    private float quantity = 0;

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
    public DataIngredient getClone(){
        DataIngredient d = new DataIngredient(this.name, this.quantity, this.unit);
        return d;
    }
    public void setQuantity(float newQty){
        this.quantity = newQty;
    }
    public void setName(String n) {this.name = n.toLowerCase();}
    public void setUnit(String u){this.unit = u.toLowerCase();}
}
