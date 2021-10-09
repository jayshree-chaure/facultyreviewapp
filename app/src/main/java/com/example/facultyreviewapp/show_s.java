package com.example.facultyreviewapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facultyreviewapp.model.StudentData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class show_s extends AppCompatActivity {
    ListView l1;
    ArrayList sname,b,c;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_show_s);
        l1=(ListView)findViewById(R.id.S1);
        FirebaseAuth.getInstance().signInAnonymously();


        mDatabase = FirebaseDatabase.getInstance().getReference("studentdata");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sname=new ArrayList();
                b=new ArrayList();
                c=new ArrayList();
                for(DataSnapshot x:dataSnapshot.getChildren()) {
                    StudentData allprofessor = x.getValue(StudentData.class);
                    Log.e("MyError","Testing");
                    try{
                        sname.add("Student Name : "+allprofessor.getSname().toString() );
                        b.add("Student Barcode ID : "+allprofessor.getBarCodeID().toString() );
                        c.add("Student Class : "+allprofessor.getSclass().toString() );
                    }catch(Exception ex){
                        Log.e("MyError",ex.getMessage());
                    }
                    //Toast.makeText(getApplicationContext(), allproducts.getName(), Toast.LENGTH_LONG).show();
                }
                //set adapter for listview
                show_s.CustomAdapter c1=new show_s.CustomAdapter();
                l1.setAdapter(c1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sname.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.shows, null);
            TextView t1 = (TextView) convertView.findViewById(R.id.names);





            TextView t2 = (TextView) convertView.findViewById(R.id.bid);

            t2.setText(b.get(position).toString());

            TextView t3 = (TextView) convertView.findViewById(R.id.cl);
            t3.setText(c.get(position).toString());

            //i1.setImageResource();
            t1.setText(sname.get(position).toString());
            return convertView;        }

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

