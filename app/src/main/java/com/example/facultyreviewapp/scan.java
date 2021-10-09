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




public class scan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();


        setContentView(R.layout.scan);


        }
    public void barc(View view)
    {
        Intent intent = new Intent(this, barcode.class);
        startActivity(intent);
    }
    public void checkConnection(){
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo =manager.getActiveNetworkInfo();

        if (null!=activeNetworkInfo){
            if(activeNetworkInfo.getType()== ConnectivityManager.TYPE_WIFI){

            }
            if(activeNetworkInfo.getType()== ConnectivityManager.TYPE_MOBILE){

            }

        }
        else {

               Toast.makeText(this, "NO INTERNET CONNECTION",Toast.LENGTH_SHORT).show();
        }

    }



    }



