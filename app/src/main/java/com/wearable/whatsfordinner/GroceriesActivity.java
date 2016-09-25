package com.wearable.whatsfordinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class GroceriesActivity extends AppCompatActivity {

    private ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);
        lv = (ListView) findViewById(R.id.recipiesListView);
        final DataIngredient[] products = DataHolder.getInstance().getGroceriesToShop();
        GroceriesListAdapter adapter = new GroceriesListAdapter(products, this);
        lv.setAdapter(adapter);
    }

    private String getGroceryToDisplay( DataIngredient dataIngredient){
        return dataIngredient.getName() + " (" + dataIngredient.getQuantity() + " " + dataIngredient.getUnit() + ")";
    }
}
