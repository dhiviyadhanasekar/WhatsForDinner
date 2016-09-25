package com.wearable.whatsfordinner;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipiesLandscapeFragment extends Fragment {

    private ListView lv ;
    View fragmentView;
    int lastClickedPos = 0;

    public RecipiesLandscapeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipies_landscape, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        fragmentView = v;
        lv = (ListView) v.findViewById(R.id.recipiesListView);
        final String products[] = DataHolder.getInstance().getAllRecipies();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.list_item, products){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                if(position == lastClickedPos){
                    view.setBackgroundColor(Color.parseColor("#33E0FF"));
                } else view.setBackgroundColor(Utils.getColorForRowPos(position));
                return view;
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View convertView, int pos,
                                    long arg3) {
                parent.getChildAt(lastClickedPos).setBackgroundColor(Utils.getColorForRowPos(pos));
                parent.getChildAt(pos).setBackgroundColor(Color.parseColor("#33E0FF"));
                lastClickedPos = pos;
                Log.v("onclick list it ", products[pos]);
                loadRecipie(DataHolder.getInstance().getRecipie(products[pos]));

            }
        });
        loadRecipie(DataHolder.getInstance().getRecipie(products[lastClickedPos]));
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

    private void loadRecipie(DataRecipie selectedRecipie){
        TextView recipieNameTxtView = (TextView) fragmentView.findViewById(R.id.recipie_name);
        recipieNameTxtView.setText(selectedRecipie.getRecipieName());

        TextView ingredientListTxtView = (TextView) fragmentView.findViewById(R.id.ingredient_list);
        StringBuffer ingredientListStr = new StringBuffer("");
        for(int i=0; i<selectedRecipie.getIngredients().size(); i++){
            DataIngredient in = selectedRecipie.getIngredients().get(i);
            ingredientListStr.append("* ");
            ingredientListStr.append(in.getName());
            ingredientListStr.append(" ");
            ingredientListStr.append("(");
            ingredientListStr.append(in.getQuantity());
            ingredientListStr.append(" ");
            ingredientListStr.append(in.getUnit());
            ingredientListStr.append(") \n");
        }
        ingredientListTxtView.setText(ingredientListStr.toString());

        ImageView recipieImageView = (ImageView) fragmentView.findViewById(R.id.recipie_photo);
        if(selectedRecipie.getImageUri() == null){
            recipieImageView.setImageResource(R.drawable.ic_default_recipie);
        } else {
            recipieImageView.setImageURI(selectedRecipie.getImageUri());
        }
        recipieImageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.recipieImageView_height);
        recipieImageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.recipieImageView_width);

        TextView recipieDescription  = (TextView) fragmentView.findViewById(R.id.recipie_directions);
        recipieDescription.setText(selectedRecipie.getInstructions());
    }
}
