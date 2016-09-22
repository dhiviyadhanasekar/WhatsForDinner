package com.wearable.whatsfordinner;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipiesLandscapeFragment extends Fragment {

    private ListView lv = null;
    public RecipiesLandscapeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipies_landscape, container, false);
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        lv = (ListView) v.findViewById(R.id.recipiesListView);
        final String products[] = DataHolder.getInstance().getAllRecipies();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.list_item, products){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                if(position %2 == 1) {
                    // Set a background color for ListView regular row/item
                    view.setBackgroundColor(Color.parseColor("#E0E0E0"));

                } else {
                    // Set the background color for alternate row/item
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
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
