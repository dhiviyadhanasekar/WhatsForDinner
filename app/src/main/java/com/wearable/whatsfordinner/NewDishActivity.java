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
import android.widget.EditText;
import android.widget.ImageView;

public class NewDishActivity extends AppCompatActivity {

    private static final int imageID = 1;
    private static final int PERMISSION_READ_MEDIA = 2;

    ImageView recipieImageView = null;

    DataHolder dataInstance = DataHolder.getInstance();
    private DataRecipie recipie = new DataRecipie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dish);

        final EditText recipeNameEditText = (EditText) findViewById(R.id.recipeNameEditText);
        recipieImageView = (ImageView) findViewById(R.id.recipie_photo);

    }

    public void addRecipieImage(View view){
        Log.v("addRecipieImage", "");
        //Src: https://www.youtube.com/watch?v=8nDKwtTcOUg
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