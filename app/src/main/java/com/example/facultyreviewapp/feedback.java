package com.example.facultyreviewapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyreviewapp.model.GlobalVariables;
import com.example.facultyreviewapp.model.ProfessorData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class feedback extends AppCompatActivity {

    ProfessorData sprof;
    ListView l2;
    ArrayList pname,cs,ds,ps,pun,subjectskill;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();


        setContentView(R.layout.activity_feedback);
        l2=(ListView)findViewById(R.id.ListView2);
        FirebaseAuth.getInstance().signInAnonymously();

        l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                //   GlobalVariables.globalproductid=imagename.get(position).toString();
                //get selected faculty id and store it in global variable
                String selecteditem=pname.get(position).toString();
                selecteditem=selecteditem.substring(0,selecteditem.indexOf(":"));
                Toast.makeText(getApplicationContext(),selecteditem,Toast.LENGTH_LONG).show();
                GlobalVariables.facultyid=Integer.parseInt(selecteditem.trim());




            }



        });

        mDatabase = FirebaseDatabase.getInstance().getReference("professorData");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pname=new ArrayList();
                cs=new ArrayList();
                ds=new ArrayList();
                ps =new ArrayList();
                pun =new ArrayList();
                subjectskill =new ArrayList();
                for(DataSnapshot x:dataSnapshot.getChildren()) {
                    ProfessorData allprofessor = x.getValue(ProfessorData.class);
                    Log.e("MyError","Testing");
                    try{
                        int count = Integer.parseInt(allprofessor.getCount().toString());
                        int csAvg = Integer.parseInt(allprofessor.getCs().toString())/count;
                        int dsAvg = Integer.parseInt(allprofessor.getDs().toString())/count;
                        int psAvg = Integer.parseInt(allprofessor.getPs().toString())/count;
                        int punAvg = Integer.parseInt(allprofessor.getPun().toString())/count;
                        int subjectskillAvg = Integer.parseInt(allprofessor.getSubjectskill().toString())/count;

                        pname.add(allprofessor.getPid().toString() +": " +allprofessor.getPname().toString());
                        cs.add(" Communication skill: " +csAvg);
                        ds.add(" Doubt Solving: " +dsAvg);
                        ps.add(" Presentation Skill: " +psAvg);
                        pun.add(" Punctuality: " +punAvg);
                        subjectskill.add(" Subject Skill: " +subjectskillAvg);

                    }catch(Exception ex){
                        Log.e("MyError",ex.getMessage());
                    }
                    //Toast.makeText(getApplicationContext(), allproducts.getName(), Toast.LENGTH_LONG).show();
                }
                //set adapter for listview
                feedback.CustomAdapter c1=new feedback.CustomAdapter();
                l2.setAdapter(c1);

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
            convertView = getLayoutInflater().inflate(R.layout.feedlist, null);
            TextView t1 = (TextView) convertView.findViewById(R.id.textView_name1);
            //i1.setImageResource();
            t1.setText(pname.get(position).toString());
            TextView t2 = (TextView) convertView.findViewById(R.id.textView_name2);
            t2.setText(cs.get(position).toString());


            TextView t3 = (TextView) convertView.findViewById(R.id.textView_name3);
            t3.setText(ds.get(position).toString());


            TextView t4 = (TextView) convertView.findViewById(R.id.textView_name4);
            t4.setText(pun.get(position).toString());


            TextView t5 = (TextView) convertView.findViewById(R.id.textView_name5);
            t5.setText(ps.get(position).toString());


            TextView t6 = (TextView) convertView.findViewById(R.id.textView_name6);
            t6.setText(subjectskill.get(position).toString());






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







