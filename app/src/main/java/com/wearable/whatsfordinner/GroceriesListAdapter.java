package com.wearable.whatsfordinner;

import android.content.Context;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Dhiviya on 9/25/2016.
 */
public class GroceriesListAdapter extends BaseAdapter implements ListAdapter {
    DataIngredient[] list ;
    float _xSwipe1, _xSwipe2;
    private Context context;
//    GestureDetector gestureDetector;


    public GroceriesListAdapter(DataIngredient[] list, Context context) {
        this.list = list;
        this.context = context;
//        gestureDetector = new GestureDetector(context, new MyGestureDetector());
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

    private String getGroceryToDisplay( DataIngredient dataIngredient){
        return dataIngredient.getName() + " (" + dataIngredient.getQuantity() + " " + dataIngredient.getUnit() + ")";
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grocery_list_item, null);
        }

        //Handle TextView and display string from your list
        final TextView listItemText = (TextView)view.findViewById(R.id.list_item);
        listItemText.setText( getGroceryToDisplay(list[position]) );
        if(list[position].getQuantity() <= 0){
            listItemText.setPaintFlags(listItemText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        view.setBackgroundColor(Utils.getColorForRowPos(position));
        final GestureDetector gestureDetector = new GestureDetector(context, new MyGestureDetector(position, view));
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        //Handle buttons and add onClickListeners
        Button plusButton = (Button) view.findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                float qty = list[position].getQuantity();
                list[position].setQuantity(qty+1);
                listItemText.setText( getGroceryToDisplay(list[position]) );
//                Toast.makeText(context,"qty in map " + DataHolder.getInstance().getGroceryItem(list[position].getName()).getQuantity()
//                        ,Toast.LENGTH_SHORT).show();
            }
        });

        Button minusButton = (Button) view.findViewById(R.id.minusButton);
        minusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int qty = (int) list[position].getQuantity();
                if(qty > 0) {
                    list[position].setQuantity(qty - 1);
                } else if(list[position].getQuantity() >0){
                    list[position].setQuantity(0);
                }
                if((int)list[position].getQuantity() == 0){
                    listItemText.setPaintFlags(listItemText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                listItemText.setText( getGroceryToDisplay(list[position]) );
//                Toast.makeText(context,"qty in map " + DataHolder.getInstance().getGroceryItem(list[position].getName()).getQuantity()
//                        ,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private final static int REL_SWIPE_MIN_DISTANCE = 0;
    public boolean onRTLFling(int pos, View view){
        //show menu
        View buttonLayout = view.findViewById(R.id.buttons);
        if(!buttonLayout.isShown()) buttonLayout.setVisibility(View.VISIBLE);
//        Toast.makeText(context,"hiihih RTL " + list[pos].getName(),Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onLTRFling(int pos, View view){
        //hide menu
        View buttonLayout = view.findViewById(R.id.buttons);
        if(buttonLayout.isShown()) buttonLayout.setVisibility(View.GONE);
//        Toast.makeText(context,"hiihih Left to right " + list[pos].getName(),Toast.LENGTH_SHORT).show();
        return true;
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        int position = 0;
        View view;

        public MyGestureDetector(int pos, View view){
            position = pos;
            this.view = view;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() >= REL_SWIPE_MIN_DISTANCE) {
                return onRTLFling(position, view);
            }  else {
                return onLTRFling(position, view);
            }
        }

    }
}