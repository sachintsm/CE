package com.example.ce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register_Activity extends AppCompatActivity {
    private EditText rname;
    private EditText rusername;
    private EditText rpassword;
    private EditText remail;
    private EditText rnumber;
    private EditText rconpassword;
    private Button rregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);


        rconpassword = (EditText)findViewById(R.id.etConpassword);
        rname = (EditText)findViewById(R.id.etName);
        rusername = (EditText)findViewById(R.id.etUsername);
        rpassword = (EditText)findViewById(R.id.etPassword);
        remail = (EditText)findViewById(R.id.etEmail);
        rnumber = (EditText)findViewById(R.id.etNumber);
        rregister = (Button) findViewById(R.id.btnregister);
    }

    public void OnReg(View view){
        String name = rname.getText().toString();
        String email = remail.getText().toString();
        String password = rpassword.getText().toString();
        String number = rnumber.getText().toString();
        String username = rusername.getText().toString();
        String conpassword = rconpassword.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || number.isEmpty() || username.isEmpty()){
            rusername.setError("Please insert username");
            rpassword.setError("Please insert password");
            rconpassword.setError("Please insert password");
            remail.setError("Please insert email");
            rnumber.setError("Please insert mobile number");
            rname.setError("Please insert your name");
        }
        else{
            if(!password.equals(conpassword)){
                Toast.makeText(Register_Activity.this,"Password didn't match !",Toast.LENGTH_LONG).show();
            }
            else {

                String type = "register";

                DBHelper dbHelper = new DBHelper(this);
                dbHelper.execute(type, name, username, email, password, number);

                rregister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Register_Activity.this, Welcome_Activity.class));
                    }
                });
            }
        }
    }
}
