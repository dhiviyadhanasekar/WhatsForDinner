package com.wearable.whatsfordinner;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroceriesActivity extends AppCompatActivity {

    private ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);
        lv = (ListView) findViewById(R.id.recipiesListView);
        final DataIngredient[] products = DataHolder.getInstance().getGroceriesToShop();
        //src: https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
        ArrayAdapter<DataIngredient> adapter = new ArrayAdapter<DataIngredient>(this, R.layout.list_item, R.id.list_item, products){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                DataIngredient dataIngredient = products[position];
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
                }
                View view = super.getView(position,convertView,parent);
                TextView itemTextView = (TextView) convertView.findViewById(R.id.list_item);
                itemTextView.setText(getGroceryToDisplay(dataIngredient));
                view.setBackgroundColor(Utils.getColorForRowPos(position));
                return view;
            }
        };
        lv.setAdapter(adapter);
    }

    private String getGroceryToDisplay( DataIngredient dataIngredient){
        return dataIngredient.getName() + " (" + dataIngredient.getQuantity() + " " + dataIngredient.getUnit() + ")";
    }
}
