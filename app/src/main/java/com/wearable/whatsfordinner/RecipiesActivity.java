package com.wearable.whatsfordinner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);

        Configuration config = getResources().getConfiguration();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment contentFragment = null;
        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            RecipiesLandscapeFragment contentFragment = new RecipiesLandscapeFragment();
            contentFragment = new RecipiesLandscapeFragment();
//            fragmentTransaction.replace(android.R.id.content, contentFragment);
        } else {
            contentFragment = new RecipiesPortraitFragment();
        }
        fragmentTransaction.replace(android.R.id.content, contentFragment);
        fragmentTransaction.commit();
    }
}
