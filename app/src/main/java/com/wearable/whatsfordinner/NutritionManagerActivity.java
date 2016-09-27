package com.wearable.whatsfordinner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NutritionManagerActivity extends AppCompatActivity {

    ArrayList<View> nutrientViewList;
    boolean isWeeklyMealPlanView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_manager);



        nutrientViewList = new ArrayList<>();
        ArrayList<DataIngredient> targetNutrients;// = DataHolder.getInstance().getTargetNutrients();

        Intent intent = getIntent();
        if(intent.hasExtra("weeklyMealPlan")){
            isWeeklyMealPlanView = true;
            targetNutrients = DataHolder.getInstance().getMealPlanNutrients();
            Button button = (Button) findViewById(R.id.save_btn);
            button.setVisibility(View.GONE);
            TextView title = (TextView) findViewById(R.id.nut_man_title);
            title.setText("Meal Plan Nutrition");
        } else {
            isWeeklyMealPlanView = false;
            targetNutrients = DataHolder.getInstance().getTargetNutrients();
        }

        for(int i=0; i<targetNutrients.size(); i++){
            createNewNutrient(targetNutrients.get(i));
        }
    }

    private void createNewNutrient(DataIngredient in ) {
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.nutrient_item, null, false);
        nutrientViewList.add(v);
        if(in != null){
            final TextView listItemText = (TextView)v.findViewById(R.id.nutrient_name);
            listItemText.setText( in.getName() );
            listItemText.setEnabled(false);

            final TextView qtyTextView = (TextView) v.findViewById(R.id.inputQty);
            qtyTextView.setText(Float.toString(in.getQuantity()));
            if(isWeeklyMealPlanView == true) qtyTextView.setEnabled(false);

            final TextView unitTextView = (TextView) v.findViewById(R.id.inputUnit);
            unitTextView.setText(in.getUnit());
            unitTextView.setEnabled(false);
        }
        LinearLayout parent = (LinearLayout) findViewById(R.id.n_view);
        parent.addView(v);
    }

    public void save(View view){
        for(int i=0; i<nutrientViewList.size(); i++){
            View v = nutrientViewList.get(i);
            String nName = ((TextView)v.findViewById(R.id.nutrient_name)).getText().toString();
            String inQtyStr = ((EditText) v.findViewById(R.id.inputQty)).getText().toString();
            float inQty = inQtyStr.length() == 0 ? 0 : Float.valueOf( inQtyStr );
            String inUnit = ((EditText) v.findViewById(R.id.inputUnit)).getText().toString();
            DataHolder.getInstance().updateTargetNutrient(nName, new DataIngredient(nName, inQty,inUnit ));

        }
        Toast toast = Toast.makeText(getApplicationContext(), "Nutrient targets saved", Toast.LENGTH_SHORT);
        toast.show();
    }
}
