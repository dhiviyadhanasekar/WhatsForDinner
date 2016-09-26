package com.wearable.whatsfordinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.MessageFormat;

public class GroceriesActivity extends AppCompatActivity {

    private ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);

        lv = (ListView) findViewById(R.id.recipiesListView);
        final DataIngredient[] products = DataHolder.getInstance().getGroceriesToShop();
        GroceriesListAdapter adapter = new GroceriesListAdapter(products, getApplicationContext());
        lv.setAdapter(adapter);

    }

}
