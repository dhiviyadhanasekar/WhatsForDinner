package com.wearable.whatsfordinner;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Dhiviya on 9/25/2016.
 */
public class GroceriesListAdapter extends BaseAdapter implements ListAdapter {
    private DataIngredient[] list ;
    float _xSwipe1, _xSwipe2;
    private Context context;



    public GroceriesListAdapter(DataIngredient[] list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int pos) {
        return list[pos];
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grocery_list_item, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item);
        listItemText.setText(list[position].getName());
        view.setBackgroundColor(Utils.getColorForRowPos(position));


        //Handle buttons and add onClickListeners
//        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
//        Button addBtn = (Button)view.findViewById(R.id.add_btn);
//
//        deleteBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                //do something
//                list.remove(position); //or some other task
//                notifyDataSetChanged();
//            }
//        });
//        addBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                //do something
//                notifyDataSetChanged();
//            }
//        });

        return view;
    }
}