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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Postadd_Activity extends AppCompatActivity{

    private ImageView ivphoto;
    private Button btnphoto,btnpostnow;
    private EditText pitem,pdescription,pcity;
    private Spinner pdistrict;
    private TextView pdate;
    private ProgressBar loading;
    private Bitmap bitmap;

    String username,usrname;
    private String url = "http://sachintsm.tk/adds/postadd.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postadd_);

        loading = findViewById(R.id.loading);
        btnpostnow = findViewById(R.id.btnpostnow);
        btnphoto = findViewById(R.id.btnphoto);


        ivphoto = findViewById(R.id.ivphoto);

        pitem = findViewById(R.id.etitem);
        pdescription = findViewById(R.id.etdescription);
        pcity = findViewById(R.id.etcity);
        pdistrict = findViewById(R.id.spndistrict);
        pdate = findViewById(R.id.tvdate);

        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getInstance().format(calendar.getTime());
        pdate.setText(currentdate);

        //take username
        TextView pusername = (TextView) findViewById(R.id.tvuname);
        Intent i = getIntent();
        usrname = i.getStringExtra("uname");
        pusername.setText(usrname);
        username = pusername.getText().toString();

        btnphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"),999);
            }
        });
        btnpostnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAdd();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999 && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                ivphoto.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void postAdd() {
        loading.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String s = response.trim();

                        if(s.equalsIgnoreCase("false")){
                            Toast.makeText(Postadd_Activity.this, "Error details" , Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                        }
                        else{
                            Toast.makeText(Postadd_Activity.this, "Success uploading", Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(Postadd_Activity.this, "Error reading details" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("item",pitem.getText().toString().trim());
                params.put("description",pdescription.getText().toString().trim());
                params.put("district",String.valueOf(pdistrict.getSelectedItem()));
                params.put("city",pcity.getText().toString().trim());
                params.put("date",pdate.getText().toString().trim());
                params.put("photo",getStringImage(bitmap));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String getStringImage(Bitmap bitmap){
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