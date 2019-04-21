package com.example.ce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyAdds_Activity extends AppCompatActivity {

    String usrname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adds_);

        TextView uname = (TextView) findViewById(R.id.tvuname);
        Intent i = getIntent();
        usrname = i.getStringExtra("uname");
        uname.setText(usrname);
    }
}
