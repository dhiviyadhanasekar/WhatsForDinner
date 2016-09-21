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

    private ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);

        Configuration config = getResources().getConfiguration();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment contentFragment = null;
        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            contentFragment = new RecipiesLandscapeFragment();
        } else {
            contentFragment = new RecipiesPortraitFragment();
        }
        fragmentTransaction.replace(android.R.id.content, contentFragment);
        fragmentTransaction.commit();

        lv = (ListView) findViewById(R.id.recipiesListView);
        final String products[] = DataHolder.getInstance().getAllRecipies();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item, products){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                if(position %2 == 1)
                {
                    // Set a background color for ListView regular row/item
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                else
                {
                    // Set the background color for alternate row/item
                    view.setBackgroundColor(Color.parseColor("#CCCCCC"));
                }
                return view;
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3) {
//                Toast.makeText(getApplicationContext(),"hiihih",Toast.LENGTH_SHORT).show();
                Log.v("onclick list it ", products[pos]);
            }
        });
    }
}
