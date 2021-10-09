package com.example.facultyreviewapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.facultyreviewapp.model.StudentData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class studactiv extends AppCompatActivity {


    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_studactiv);
        FirebaseAuth.getInstance().signInAnonymously();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

    }
    public void AddRecord(View v)
    {
        //pass valules from textbox to variables
        EditText e1=(EditText)findViewById(R.id.editTextBarCode);
        EditText e2=(EditText)findViewById(R.id.editTextName);
        EditText e3=(EditText)findViewById(R.id.editTextClass);


        awesomeValidation.addValidation(this, R.id.editTextName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editTextClass,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.Classerror);
        awesomeValidation.addValidation(this, R.id.editTextBarCode,"(?<!\\d)\\d{10}(?!\\d)" , R.string.iderror);





        //add record in firebase
        StudentData s1=new StudentData();
        s1.setBarCodeID(e1.getText().toString());
        s1.setSname(e2.getText().toString());
        s1.setSclass(e3.getText().toString());
        if (awesomeValidation.validate()) {

            FirebaseDatabase.getInstance().getReference().child("studentdata").child(e1.getText().toString()).setValue(s1);
            Toast.makeText(getApplicationContext(), "Record is Added", Toast.LENGTH_LONG).show();
            e1.setText("");
            e2.setText("");
            e3.setText("");

        }
        else {
            Toast.makeText(getApplicationContext(), "Enter Valid Record", Toast.LENGTH_LONG).show();

        }
    }

    public void logout2(View view){

        Intent intent = new Intent(studactiv.this,adminlogin.class);
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