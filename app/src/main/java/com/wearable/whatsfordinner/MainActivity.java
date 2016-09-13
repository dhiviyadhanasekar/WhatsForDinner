package com.wearable.whatsfordinner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    PopupWindow appInfoPopup = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAppInfo(View view){
//        Log.v("My tag", "Reaching onclick showappinfo");
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        appInfoPopup = new PopupWindow(
                inflater.inflate(R.layout.popup_app_info, null, false),
                1000,
                750,
                true);

        appInfoPopup.showAtLocation(this.findViewById(R.id.mainViewLayout), Gravity.CENTER, 0, 0);
    }

    public void closeAppInfo(View view){
//        Log.v("My tag", "Clicked OKKKKKKK");
        appInfoPopup.dismiss();
    }
}
