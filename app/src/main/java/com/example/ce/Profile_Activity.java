package com.example.ce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class Profile_Activity extends AppCompatActivity {

    private Button btnsave;
    private EditText pfusername,prname,premail,prunmber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

        btnsave = findViewById(R.id.btnsave);
        pfusername = findViewById(R.id.etUsername);
        premail = findViewById(R.id.etEmail);
        prunmber = findViewById(R.id.etNumber);
        prname = findViewById(R.id.etName);
    }
}
