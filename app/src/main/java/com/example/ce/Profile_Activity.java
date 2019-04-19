package com.example.ce;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    String username,usrname;
    private Menu action;
    private static String URL_ProfileEdit = "http://idexserver.tk/sachinCE/user/profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

        premail = findViewById(R.id.etEmail);
        prnumber = findViewById(R.id.etNumber);
        prname = findViewById(R.id.etName);

        TextView prusername = (TextView) findViewById(R.id.tvUsername);
        Intent i = getIntent();
        usrname = i.getStringExtra("uname");
        prusername.setText(usrname);
        username = prusername.getText().toString();
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

                            if (success.equals("true")) {
                                JSONObject object = jsonObject.getJSONObject("data");

                                    String strnname = object.getString("name").trim();
                                    String stremail = object.getString("email").trim();
                                    String strnumber = object.getString("number").trim();

                                    prname.setText(strnname);
                                    premail.setText(stremail);
                                    prnumber.setText(strnumber);
                            }
                            else {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action,menu);
        action = menu;
        action.findItem(R.id.save).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                prname.setFocusableInTouchMode(true);
                premail.setFocusableInTouchMode(true);
                prnumber.setFocusableInTouchMode(true);

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(prname,InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.edit).setVisible(false);
                action.findItem(R.id.save).setVisible(true);

                return true;

            case R.id.save:
                SaveEditDetails();

                action.findItem(R.id.edit).setVisible(true);
                action.findItem(R.id.save).setVisible(false);

                prname.setFocusableInTouchMode(false);
                premail.setFocusableInTouchMode(false);
                prnumber.setFocusableInTouchMode(false);

                prname.setFocusable(false);
                premail.setFocusable(false);
                prnumber.setFocusable(false);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void SaveEditDetails(){
        final String prname = this.prname.getText().toString().trim();
        final String oremail = this.premail.getText().toString().trim();
        final String prnumber = this.prnumber.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ProfileEdit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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

                return super.getParams();
            }
        };
    }
}