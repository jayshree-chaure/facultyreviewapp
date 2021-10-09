package com.example.facultyreviewapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class adminlogin extends AppCompatActivity {
    public EditText Name;
    private EditText Password;
    public Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();


        setContentView(R.layout.adminlogin);
        Name = (EditText)findViewById(R.id.etname);
        Password = (EditText)findViewById(R.id.etpassword);

        Login = (Button)findViewById(R.id.btn);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(),Password.getText().toString());

            }
        });

    }
    private void validate(String userName, String userPassword){
        if (userName.equals("jayshree02") && userPassword.equals("jayu0204")){
            Intent intent = new Intent(adminlogin.this, viewf.class);
            startActivity(intent);
            Name.setText("");
            Password.setText("");
        }
        else{
            Toast.makeText(getApplicationContext(),"Invalid Login",Toast.LENGTH_SHORT).show();

        }
    }
    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();

        if (null != activeNetworkInfo) {
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

            }
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {

            }

        } else {

            Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }
    }



}
