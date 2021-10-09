package com.example.facultyreviewapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyreviewapp.model.ProfessorData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class proflist extends AppCompatActivity {
    ListView l1;
    ArrayList pname,pinfo;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);


        Window w = getWindow();
        w.setTitle("WELCOME TO PROFESSOR'S LIST");
        setContentView(R.layout.activity_proflist);
        l1=(ListView)findViewById(R.id.Listpp);
        FirebaseAuth.getInstance().signInAnonymously();


        mDatabase = FirebaseDatabase.getInstance().getReference("professorData");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pname=new ArrayList();
                pinfo=new ArrayList();
                for(DataSnapshot x:dataSnapshot.getChildren()) {
                    ProfessorData allprofessor = x.getValue(ProfessorData.class);
                    Log.e("MyError","Testing");
                    try{
                        pname.add(allprofessor.getPid().toString() +": " +allprofessor.getPname().toString());
                        pinfo.add("SUBJECT : "+ allprofessor.getPinfo().toString() );
                    }catch(Exception ex){
                        Log.e("MyError",ex.getMessage());
                    }
                    //Toast.makeText(getApplicationContext(), allproducts.getName(), Toast.LENGTH_LONG).show();
                }
                //set adapter for listview
                proflist.CustomAdapter c1=new proflist.CustomAdapter();
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
            return pname.size();
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
            convertView = getLayoutInflater().inflate(R.layout.playout, null);
            TextView t1 = (TextView) convertView.findViewById(R.id.np);
            //i1.setImageResource();
            t1.setText(pname.get(position).toString());

            TextView t2 = (TextView) convertView.findViewById(R.id.sub);
            //i1.setImageResource();
            t2.setText(pinfo.get(position).toString());
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
