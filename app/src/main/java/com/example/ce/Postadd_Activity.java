package com.example.ce;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Postadd_Activity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ivphoto;
    private Button btnphoto,btnpostnow,btnclear;
    private EditText pitem,pdescription,pcity;
    private Spinner pdistrict;
    private TextView pdate;
    private Bitmap bitmap;
    private final int IMG_REQUEST = 1;

    String username,usrname,district;
    private String url = "http://idexserver.tk/sachinCE/adds/postadd.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postadd_);

        btnpostnow = findViewById(R.id.btnpostnow);
        btnphoto = findViewById(R.id.btnphoto);
        btnclear = findViewById(R.id.btnclear);

        ivphoto = findViewById(R.id.ivphoto);

        pitem = findViewById(R.id.etitem);
        pdescription = findViewById(R.id.etdescription);
        pcity = findViewById(R.id.etcity);
        pdistrict = findViewById(R.id.spndistrict);
        pdate = findViewById(R.id.tvdate);

        district = String.valueOf(pdistrict.getSelectedItem());

        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getInstance().format(calendar.getTime());
        pdate.setText(currentdate);

        btnphoto.setOnClickListener(this);
        btnclear.setOnClickListener(this);
        btnpostnow.setOnClickListener(this);

        //take username
        TextView pusername = (TextView) findViewById(R.id.tvuname);
        Intent i = getIntent();
        usrname = i.getStringExtra("uname");
        pusername.setText(usrname);
        username = pusername.getText().toString();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnphoto: selectImage();
                break;
            case R.id.btnclear: Clear();
                break;
            case R.id.btnpostnow: PostAdd();
                break;
        }
    }
    public void PostAdd() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            Toast.makeText(Postadd_Activity.this,Response,Toast.LENGTH_SHORT).show();
                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Postadd_Activity.this, "Error reading details" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("item",pitem.getText().toString().trim());
                params.put("description",pdescription.getText().toString().trim());
                params.put("district",district);
                params.put("city",pcity.getText().toString().trim());
                params.put("date",pdate.getText().toString().trim());
                params.put("photo",imageToString(bitmap));
                return params;
            }
        };
        MySingleton.getInstance(Postadd_Activity.this).addToRequestQue(stringRequest);
    }
    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                ivphoto.setImageBitmap(bitmap);
                ivphoto.setVisibility(View.VISIBLE);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
    public void Clear(){
        pitem.setText("");
        pdescription.setText("");
        pcity.setText("");
    }
}
