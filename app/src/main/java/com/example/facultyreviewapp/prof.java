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
import com.example.facultyreviewapp.model.ProfessorData;
import com.google.common.collect.Range;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class prof extends AppCompatActivity {
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();


        setContentView(R.layout.activity_prof);
        FirebaseAuth.getInstance().signInAnonymously();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
    }
    public void addProf(View v)
    {
        //pass valules from textbox to variables
        EditText e1=(EditText)findViewById(R.id.profid);
        EditText e2=(EditText)findViewById(R.id.profname);
        EditText e3=(EditText)findViewById(R.id.profinfo);
        awesomeValidation.addValidation(this, R.id.profname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.profinfo,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.Classerror);
        awesomeValidation.addValidation(this, R.id.profid,Range.closed(0, 60), R.string.iderror);



        //add record in firebase
        ProfessorData s1=new ProfessorData();
        s1.setPid(e1.getText().toString());
        s1.setPname(e2.getText().toString());
        s1.setPinfo(e3.getText().toString());
        s1.setCs("0");
        s1.setDs("0");
        s1.setPs("0");
        s1.setPun("0");
        s1.setSubjectskill("0");
        s1.setCount("0");
        if (awesomeValidation.validate()) {

            FirebaseDatabase.getInstance().getReference().child("professorData").child(e1.getText().toString()).setValue(s1);
            Toast.makeText(getApplicationContext(),"Record is Added",Toast.LENGTH_LONG).show();
            e1.setText("");
            e2.setText("");
            e3.setText("");


        }
        else {
            Toast.makeText(getApplicationContext(), "Enter Valid Record", Toast.LENGTH_LONG).show();

        }

    }
    public void logout3(View view){

        Intent intent = new Intent(prof.this,adminlogin.class);
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
