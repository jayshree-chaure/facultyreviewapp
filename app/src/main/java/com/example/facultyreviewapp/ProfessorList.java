package com.example.facultyreviewapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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



public class ProfessorList extends AppCompatActivity {
    ListView l1;
    ArrayList pname;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_professor_list);
        l1=(ListView)findViewById(R.id.ListView1);
        FirebaseAuth.getInstance().signInAnonymously();

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                //   GlobalVariables.globalproductid=imagename.get(position).toString();
                //get selected faculty id and store it in global variable

                String selecteditem=pname.get(position).toString();
                selecteditem=selecteditem.substring(0,selecteditem.indexOf(":"));
                Toast.makeText(getApplicationContext(),selecteditem,Toast.LENGTH_LONG).show();

                GlobalVariables.facultyid=Integer.parseInt(selecteditem.trim());
                Intent intent=new Intent(ProfessorList.this,reviewp.class);
                startActivity(intent);

            }

        });
        mDatabase = FirebaseDatabase.getInstance().getReference("professorData");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pname=new ArrayList();
                for(DataSnapshot x:dataSnapshot.getChildren()) {
                    ProfessorData allprofessor = x.getValue(ProfessorData.class);
                    Log.e("MyError","Testing");
                    try{
                        pname.add(allprofessor.getPid().toString() +": " +allprofessor.getPname().toString());
                    }catch(Exception ex){
                        Log.e("MyError",ex.getMessage());
                    }
                    //Toast.makeText(getApplicationContext(), allproducts.getName(), Toast.LENGTH_LONG).show();
                }
                //set adapter for listview
                ProfessorList.CustomAdapter c1=new ProfessorList.CustomAdapter();
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
            convertView = getLayoutInflater().inflate(R.layout.professforlayout, null);
            TextView t1 = (TextView) convertView.findViewById(R.id.textView_name);
            //i1.setImageResource();
            t1.setText(pname.get(position).toString());
            return convertView;        }

    }
    public void main1(View view){
        Intent intent = new Intent(ProfessorList.this,MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
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
