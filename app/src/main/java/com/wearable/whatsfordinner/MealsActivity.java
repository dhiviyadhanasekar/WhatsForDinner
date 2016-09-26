package com.wearable.whatsfordinner;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MealsActivity extends AppCompatActivity {

    private static final String[] days = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
    private static final  String[] meals = {"b", "l"};
    PopupWindow pickRecipiesPopUp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        for(int i=0; i<days.length; i++){
            for(int j=0; j<1; j++){
                String buttonID = meals[j] + "_" + days[i] + "_" + "button";
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                final Button btn = (Button) findViewById(resID);
                final int row = i;
                final int col = j;
                btn.setText(DataHolder.getInstance().getMealSelected(row, col));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showRecipiesPopUp(v, btn, row, col);
                    }
                });
            }
        }
    }

    public void showRecipiesPopUp(View view, final Button b, final int row, final int col){
//        Log.v("My tag", "Reaching onclick showappinfo");
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.pick_recipies_popup, null, false);

        ListView lv = (ListView) v.findViewById(R.id.recipiesListView);
        final String products[] = DataHolder.getInstance().getAvailableMeals();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item, products){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                view.setBackgroundColor(Utils.getColorForRowPos(position));
                return view;
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View convertView, int pos,
                                    long arg3) {
                parent.getChildAt(pos).setBackgroundColor(Color.parseColor("#33E0FF"));
                Log.v("onclick list it ", products[pos]);
                String currentRecipie = b.getText().toString().toLowerCase();
                if(!currentRecipie.equals(DataHolder.eatingOut)){
                    DataHolder.getInstance().incrementMealCount(currentRecipie);
                }
                String selectedR = products[pos].toLowerCase();
                DataHolder.getInstance().decrementMealCount(selectedR);
                b.setText(selectedR);
                DataHolder.getInstance().setMealSelected(row, col, selectedR);
                pickRecipiesPopUp.dismiss();
            }
        });

        pickRecipiesPopUp = new PopupWindow(v, 1000, 650, true);
        pickRecipiesPopUp.showAtLocation(this.findViewById(R.id.meals_activity_screen), Gravity.CENTER, 0, 0);
    }
}
