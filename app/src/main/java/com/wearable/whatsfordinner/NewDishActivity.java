package com.wearable.whatsfordinner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class NewDishActivity extends AppCompatActivity {

    private static final int imageID = 1;
    private static final int PERMISSION_READ_MEDIA = 2;

    ImageView recipieImageView = null;

    DataHolder dataInstance = DataHolder.getInstance();
    private DataRecipie recipie = new DataRecipie();
    private ArrayList<View> ingredientViews = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dish);

//        final EditText recipeNameEditText = (EditText) findViewById(R.id.recipeNameEditText);
        recipieImageView = (ImageView) findViewById(R.id.recipie_photo);

        // Listview Data
        final String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                "iPhone 4S", "Samsung Galaxy Note 800",
                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};
        for(int i=1; i<DataConstants.MAX_INGREDIENT_COUNT+1; i++) {
            initIngredientView(products, i);
        }
    }

    private void initIngredientView(final String[] products, final int i) {
        int ingredientInputRowId = getResources().getIdentifier("ingredient_input_row" + i,
                "id", getPackageName());
        View v = findViewById(ingredientInputRowId);
        ingredientViews.add(v);
        final ListView lv = (ListView) v.findViewById(R.id.list_view);
        lv.setVisibility(View.GONE);
        final EditText inputSearch = (EditText) v.findViewById(R.id.inputSearch);
        ImageButton showIngredientsButton = (ImageButton) v.findViewById(R.id.showIngredientsButton);

        showIngredientsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.v("Lv visiility", lv.isShown() + " = " + v);
                lv.setVisibility(lv.isShown() == true ? View.GONE : View.VISIBLE);
                if(lv.isShown()) {
                    lv.getLayoutParams().height = (int) getResources().getDimension(R.dimen.recipieImageView_height);
                }
            }
        });
        // Adding items to listview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item, products);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3) {
//                Toast.makeText(getApplicationContext(),"hiihih",Toast.LENGTH_SHORT).show();
                Log.v("onclick list it " + i ,products[pos]);
                inputSearch.setText(products[pos]);
                lv.setVisibility(View.GONE);//listview.setVisibility(View.VISIBLE);
            }
        });
    }

    //Src: https://www.youtube.com/watch?v=8nDKwtTcOUg
    public void addRecipieImage(View view){
        Log.v("addRecipieImage", "");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, imageID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("onActivityResult", requestCode + " => " + data);
        if(requestCode == imageID && resultCode == RESULT_OK && data != null){
            recipie.setImageUri(data.getData());
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            Log.v("addRecipieImage", permissionCheck + "");
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_MEDIA);
            } else {
                Log.v("loadImage", "from addRecipieImage");
                loadImage();
            }
        }
    }

    private void loadImage() {
        Log.v("loadImage", "yayy");
        recipieImageView.setImageURI(recipie.getImageUri());
        recipieImageView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.recipieImageView_height);
        recipieImageView.getLayoutParams().width = (int) getResources().getDimension(R.dimen.recipieImageView_width);
//        recipieImageView.requestLayout();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.v("onRequestPermiss", permissions.toString());
        switch (requestCode) {

            case PERMISSION_READ_MEDIA:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    loadImage();
                }
                break;

            default:
                break;
        }
    }

    public void createToast(String content){
        Toast toast = Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT);
        toast.show();
    }
    public void saveRecipie(View v){
        v.setEnabled(false);
        EditText recipieEditText = (EditText) findViewById(R.id.recipeNameEditText);
        String recipieName = recipieEditText.getText().toString();
        if(recipieName.length() == 0){
            createToast("Enter a recipie name");
            v.setEnabled(true);
            return;
        }

        ArrayList<DataIngredient> inList = new ArrayList<>();
        for(int i=0; i<ingredientViews.size(); i++){
            View inView = ingredientViews.get(i);
            String inName = ((EditText) inView.findViewById(R.id.inputSearch)).getText().toString();
            if(inName.length() == 0) continue;
            String inQtyStr = ((EditText) inView.findViewById(R.id.inputQty)).getText().toString();
            float inQty = inQtyStr.length() == 0 ? 0 : Float.valueOf( inQtyStr );
            String inUnit = ((EditText) inView.findViewById(R.id.inputUnit)).getText().toString();
            DataIngredient in = new DataIngredient(inName, inQty, inUnit);
            DataHolder.getInstance().addNewIngredient(inName);
            inList.add(new DataIngredient(inName, inQty, inUnit));
        }
        recipie.setIngredients(inList);

        String saveMessage = "Recipie " + recipieName ;
        if(dataInstance.recipieExists(recipieName)){
            saveMessage += " saved";
        } else{
            saveMessage += " updated";
        }
        recipie.setRecipieName(recipieName);
        String ins = ((EditText) findViewById(R.id.recipieInstructionsEditText)).getText().toString();
        recipie.setInstructions(ins);
        dataInstance.saveRecipie(recipie);

        createToast(saveMessage);
        v.setEnabled(true);
    }
}

//        if(recipeNameEditText != null) {
//            defaultTextColor = recipeNameEditText.getTextColors().getDefaultColor();
//            recipeNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (!hasFocus) {
//                        // code to execute when EditText loses focus
//                        String newRecipieName = recipeNameEditText.getText().toString();
//                        recipieExists = dataInstance.recipieExists(newRecipieName);
//                        if (recipieExists == true) {
//                            recipeNameEditText.setTextColor(Color.parseColor("#cc0000"));
////                            recipie = dataInstance.getRecipie(newRecipieName);
//                        } else {
//                            recipeNameEditText.setTextColor(defaultTextColor);
//                        }
//                        Log.v("NewDishActivity", "recipie name" + newRecipieName + " => " + recipieExists);
//                    }
//                }
//            });
//        }