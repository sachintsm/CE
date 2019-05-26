package com.example.ce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button signin;
    private ProgressBar loading;
    private static String URL_LOGIN = "http://sachintsm.tk/user/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = findViewById(R.id.loading);
        username = findViewById(R.id.etUname);
        password = findViewById(R.id.etPword);
        signin = findViewById(R.id.btnsignin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_username = username.getText().toString();
                String str_password = password.getText().toString();

                if(!str_username.isEmpty() || !str_password.isEmpty()) {
                    Login(str_username,str_password);
                }
                else {
                    username.setError("Please insert username");
                    password.setError("Please insert password");
                }

            }

        });
    }
    public void Login(final String username, final String password) {

        loading.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String success  = jsonObject.getString("success");

                    if(success.equals("true")){
                        JSONObject jsonArray= jsonObject.getJSONObject("data");
                        String username = jsonArray.getString("username");

                        loading.setVisibility(View.GONE);

                        Intent i = new Intent(MainActivity.this, Adds.class);
                        i.putExtra("uname", username);
                        startActivity(i);
                    }
                    else{
                        loading.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,"Check Username/Password",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    signin.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,"Error" +e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.setVisibility(View.GONE);
                    signin.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,"Error" +error.toString(),Toast.LENGTH_SHORT).show();
                }
            })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void openReg(View v){
        Intent intent = new Intent(this,Register_Activity.class);
        startActivity(intent);
    }
    public void reset(View v){
        Intent intent = new Intent(this,ResetPassword_Activity.class);
        startActivity(intent);
    }
}
