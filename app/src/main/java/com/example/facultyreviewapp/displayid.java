package com.example.facultyreviewapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyreviewapp.model.StudentData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class displayid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);


        setContentView(R.layout.displayid);
        Intent intent = getIntent();
        String barcode_output = intent.getStringExtra("BARCODE_OUTPUT");
        TextView textView = (TextView) findViewById(R.id.t12);
        textView.setText(barcode_output);
        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference("studentdata");
        mDatabase.child(barcode_output).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StudentData s1 = dataSnapshot.getValue(StudentData.class);

                    String output = s1.getSname();
                    String output1 = s1.getSclass();
                    String output2 = s1.getBarCodeID();
                    String output3 = s1.getSname();


                    TextView textView1 = (TextView) findViewById(R.id.t15);
                    textView1.setText(output);
                    TextView textView2 = (TextView) findViewById(R.id.t13);
                    textView2.setText(output1);
                    TextView textView3 = (TextView) findViewById(R.id.t12);
                    textView3.setText(output2);
                    TextView textView4 = (TextView) findViewById(R.id.named);
                    textView4.setText(output3);


                }
                else
                {
                    Intent intent1 = new Intent(displayid.this, nostud.class);
                    startActivity(intent1);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Intent intent1 = new Intent(displayid.this, nostud.class);
                startActivity(intent1);
            }
        });

    }


    public void que(View view)
    {
        Intent intent = new Intent(displayid.this, ProfessorList.class);
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
