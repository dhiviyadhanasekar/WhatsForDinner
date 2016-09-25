package com.wearable.whatsfordinner;

import android.graphics.Color;

/**
 * Created by Sujan on 9/24/2016.
 */
public class Utils {
    public static int getColorForRowPos(int pos){
        if(pos %2 == 1) {
            // Set a background color for ListView regular row/item
            return Color.parseColor("#E0E0E0");

        } else {
            // Set the background color for alternate row/item
            return Color.parseColor("#FFFFFF");
        }
    }
}
