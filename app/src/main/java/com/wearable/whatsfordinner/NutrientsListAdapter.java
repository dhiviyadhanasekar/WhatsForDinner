package com.wearable.whatsfordinner;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Sujan on 9/26/2016.
 */
public class NutrientsListAdapter extends BaseAdapter implements ListAdapter {
    Context context;
    List<DataIngredient> nutrients;

    public NutrientsListAdapter(List<DataIngredient> n, Context applicationContext) {
        this.context = applicationContext;
        this.nutrients = n;
    }

    public void setData(List<DataIngredient> n){
        nutrients = n;
        this.notifyDataSetChanged();
}
    @Override
    public int getCount() {
        return nutrients.size();
    }

    @Override
    public Object getItem(int position) {
        return nutrients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public List<DataIngredient> getNutrients(){
        return nutrients;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.nutrient_item, null);
        }
        final int pos = position;
        DataIngredient curNutrient = nutrients.get(position);
        //Handle TextView and display string from your list
        final TextView listItemText = (TextView)view.findViewById(R.id.nutrient_name);
        listItemText.setText( curNutrient.getName() );
        listItemText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = listItemText.getText().toString();
                nutrients.get(pos).setName(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
            }
        });

        final TextView qtyTextView = (TextView) view.findViewById(R.id.inputQty);
        qtyTextView.setText(Float.toString(curNutrient.getQuantity()));
        qtyTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = qtyTextView.getText().toString();
                nutrients.get(pos).setQuantity(new Float(text));
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
            }
        });

        final TextView unitTextView = (TextView) view.findViewById(R.id.inputUnit);
        unitTextView.setText(curNutrient.getUnit());
        unitTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = unitTextView.getText().toString();
                nutrients.get(pos).setUnit(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
            }
        });
        this.notifyDataSetChanged();
        return view;
    }
}
