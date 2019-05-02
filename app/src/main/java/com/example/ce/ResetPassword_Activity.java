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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword_Activity extends AppCompatActivity {
    private EditText resetConpassword,resetUsername,resetPassword,resetEmail;
    private Button resetSave;
    private ProgressBar loading;
    private static String URL_LOGIN = "http://idexserver.tk/sachinCE/user/reset_password.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_);

        loading = findViewById(R.id.loading);
        resetConpassword = findViewById(R.id.etresetConpassword);
        resetUsername = findViewById(R.id.etresetUname);
        resetPassword = findViewById(R.id.etresetPassword);
        resetEmail = findViewById(R.id.etresetEmail);
        resetSave = findViewById(R.id.btnresetpassword);

        resetSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = resetEmail.getText().toString();
                String username = resetUsername.getText().toString();
                String password = resetPassword.getText().toString();
                String conpassword = resetConpassword.getText().toString();

                if(!username.isEmpty() || !password.isEmpty() || !conpassword.isEmpty() || !email.isEmpty()) {
                    if(!password.equals(conpassword)){
                        Toast.makeText(ResetPassword_Activity.this,"Password didn't match !",Toast.LENGTH_LONG).show();
                    }
                    else{
                        resetPassword(username,password);
                    }

                }
                else {
                    resetUsername.setError("Please insert username");
                    resetConpassword.setError("Please insert password");
                    resetPassword.setError("Please insert password");
                    resetEmail.setError("Please insert Email");
                }

            }

        });
    }
    public void resetPassword(final String username, final String email){
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

                        Intent i = new Intent(ResetPassword_Activity.this, MainActivity.class);
//                        i.putExtra("uname", username);
                        startActivity(i);
                    }
                    else{
                        loading.setVisibility(View.GONE);
                        Toast.makeText(ResetPassword_Activity.this,"Check Username/Email",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    resetSave.setVisibility(View.VISIBLE);
                    Toast.makeText(ResetPassword_Activity.this,"Error" +e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        resetSave.setVisibility(View.VISIBLE);
                        Toast.makeText(ResetPassword_Activity.this,"Error" +error.toString(),Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("username",username);
                params.put("email",email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
