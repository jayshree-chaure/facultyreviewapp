package com.example.facultyreviewapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class studentb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_studentb);
    }
    public void sl(View view) {


        Intent intent = new Intent(studentb.this,show_s.class);
        startActivity(intent);
    }
    public void adds(View view){

        Intent intent = new Intent(studentb.this,studactiv.class);
        startActivity(intent);
    }
    public void log(View view){

        Intent intent = new Intent(studentb.this,adminlogin.class);
        startActivity(intent);
    }
    public void upd(View view){

        Intent intent = new Intent(studentb.this,student_update.class);
        startActivity(intent);
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
