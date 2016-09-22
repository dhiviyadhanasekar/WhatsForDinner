package com.wearable.whatsfordinner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.View



public class RecipiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment contentFragment = null;
        Configuration config = getResources().getConfiguration();

        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            contentFragment = new RecipiesLandscapeFragment();
        } else {
            contentFragment = new RecipiesPortraitFragment();
        }

        fragmentTransaction.replace(R.id.your_placeholder, contentFragment);
        fragmentTransaction.commit();
    }
}
