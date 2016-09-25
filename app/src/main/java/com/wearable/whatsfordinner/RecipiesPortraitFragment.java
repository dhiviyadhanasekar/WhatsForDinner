package com.wearable.whatsfordinner;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import android.widget.AdapterView;
import android.graphics.Color;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipiesPortraitFragment extends Fragment {

    private ListView lv ;
    int lastClickedPos = -1;
    public RecipiesPortraitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipies_portrait, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        lv = (ListView) v.findViewById(R.id.recipiesListView);
        final String products[] = DataHolder.getInstance().getAllRecipies();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.list_item, products){
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
//                Toast.makeText(getApplicationContext(),"hiihih",Toast.LENGTH_SHORT).show();
                if(lastClickedPos != -1){
                    parent.getChildAt(lastClickedPos).setBackgroundColor(Utils.getColorForRowPos(pos));
                }
                lastClickedPos = pos;
                parent.getChildAt(pos).setBackgroundColor(Color.parseColor("#33E0FF"));
                Log.v("onclick list it ", products[pos]);
                DataHolder.getInstance().addToMealPlan(products[pos]);
                Toast.makeText(getActivity().getApplicationContext(), products[pos] +" added to the meal plan", Toast.LENGTH_SHORT).show();
            }
        });
        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                //Do some
                Intent editModeActivity = new Intent(getActivity().getApplicationContext(), NewDishActivity.class);
                editModeActivity.putExtra("recipieName", products[position]);
                startActivity(editModeActivity);
                return true;
            }
        });
    }

}
