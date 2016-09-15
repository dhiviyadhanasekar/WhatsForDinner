package com.wearable.whatsfordinner;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.util.logging.Logger;

public class NewDishActivity extends AppCompatActivity {

    private DataRecipie recipie = new DataRecipie();
    private int defaultTextColor;
    PopupWindow imgUrlPopup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dish);

//        final EditText recipeNameEditText = (EditText) findViewById(R.id.recipeNameEditText);
//        if(recipeNameEditText != null) {
//            defaultTextColor = recipeNameEditText.getTextColors().getDefaultColor();
//            recipeNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (!hasFocus) {
//                        // code to execute when EditText loses focus
//                        String newRecipieName = recipeNameEditText.getText().toString();
//                        boolean recipieAlreadyExists = DataHolder.getInstance().recipieExists(newRecipieName);
//                        if (recipieAlreadyExists == true) {
//                            recipeNameEditText.setTextColor(Color.parseColor("#cc0000"));
//                        } else {
//                            recipeNameEditText.setTextColor(defaultTextColor);
//                        }
//                        Log.v("NewDishActivity", "recipie name" + newRecipieName + " => " + recipieAlreadyExists);
//                    }
//                }
//            });
//        }
    }

    public void addRecipieImage(View view){
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imgUrlPopup = new PopupWindow(
                inflater.inflate(R.layout.popup_recipie_url, null, false),
                1000,
                750,
                true);

        imgUrlPopup.showAtLocation(this.findViewById(R.id.newDishActivity), Gravity.CENTER, 0, 0);
    }

}
