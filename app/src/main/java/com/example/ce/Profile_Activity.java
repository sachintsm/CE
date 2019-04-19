package com.example.ce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

public class Profile_Activity extends AppCompatActivity {

    private static final String TAG = Profile_Activity.class.getSimpleName();
    private Button btnsave;
    private EditText prname,premail,prnumber;
    String username;
    private static String URL_LOGIN = "http://idexserver.tk/sachinCE/user/profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

        btnsave = findViewById(R.id.btnsave);
        EditText prusername = (EditText)findViewById(R.id.etUsername);
        premail = findViewById(R.id.etEmail);
        prnumber = findViewById(R.id.etNumber);
        prname = findViewById(R.id.etName);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                username= null;
            } else {
                username= extras.getString("username");
            }
        } else {
            username= (String) savedInstanceState.getSerializable("username");
        }
        prusername.setText(username);
    }

    @Override
    protected void onResume() {
        super.onResume();


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://idexserver.tk/sachinCE/user/profile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals(true)) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strnname = object.getString("name").trim();
                                    String stremail = object.getString("email").trim();
                                    String strnumber = object.getString("number").trim();

                                    prname.setText(strnname);
                                    premail.setText(stremail);
                                    prnumber.setText(strnumber);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Profile_Activity.this, "Error reading details" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile_Activity.this, "Error reading details" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("username",username);
                        return params;
                    }
                };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}