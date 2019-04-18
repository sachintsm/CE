package com.example.ce;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class Postadd_Activity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    private ImageView ivphoto;
    private Button btnphoto,btnpostnow;
    private EditText pitem,pdescription,pcity;
    private Spinner pdistrict;
    private TextView pdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postadd_);

        btnpostnow = findViewById(R.id.btnpostnow);
        ivphoto = findViewById(R.id.ivphoto);
        btnphoto = findViewById(R.id.btnphoto);
        pitem = findViewById(R.id.etitem);
        pdescription = findViewById(R.id.etdescription);
        pcity = findViewById(R.id.etcity);
        pdistrict = findViewById(R.id.spndistrict);
        pdate = findViewById(R.id.tvdate);


        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getInstance().format(calendar.getTime());
        pdate.setText(currentdate);

        btnphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }
    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            ivphoto.setImageURI(imageUri);
        }
    }
    public void PostAdd(View view) {
//        //String username = pusername.getText().toString();
//        String item = pitem.getText().toString();
//        //String photo = ivphoto.getImageMatrix().toString();
//        String description = pdescription.getText().toString();
//        String district = pdistrict.getText().toString();
//        String city = pcity.getText().toString();
//        String date = pdate.getText().toString();
//
//        if(username.isEmpty() || item.isEmpty() || photo.isEmpty() || description.isEmpty() || district.isEmpty() || city.isEmpty() || date.isEmpty()){
//            Toast.makeText(this,"Fill all fields !...",Toast.LENGTH_SHORT).show();
//        }
//        else{
//            String type = "postadd";
//
//            DBHelper dbHelper = new DBHelper(this);
//            dbHelper.execute(type,username,item,photo,description,district,city,date);
//
//            btnpostnow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(Postadd_Activity.this,Adds.class));
//                }
//            });
//        }
//        Toast.makeText(this,"Post your add successfully...",Toast.LENGTH_SHORT).show();
    }
    public void Clear(View v){
        pitem.setText("");
        pdescription.setText("");
        pcity.setText("");
    }
}
