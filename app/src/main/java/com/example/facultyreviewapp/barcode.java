package com.example.facultyreviewapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class barcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();


        scannerView = new ZXingScannerView((this));
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {

        String barcode_output = result.getText();
        onBackPressed();
        Intent intent = new Intent(barcode.this, displayid.class);
        intent.putExtra("BARCODE_OUTPUT", barcode_output);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
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

