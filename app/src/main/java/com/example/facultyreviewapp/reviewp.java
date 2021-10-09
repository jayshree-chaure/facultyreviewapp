package com.example.facultyreviewapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Spinner;
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

public class reviewp extends AppCompatActivity {


    ProfessorData sprof,sprof1;
    DatabaseReference mDatabase,mDatabase1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();



        setContentView(R.layout.reviewp);

    }


    public boolean validateSpinners( )
    {
        Spinner spinnerID = null;
        Resources res = getResources();
        String spinner_id[] = res.getStringArray(R.array.spinner_id);
        int numberOfSpinners = spinner_id.length;
        for(int i=0; i< numberOfSpinners;i++)
        {
           int resID = getResources().getIdentifier(spinner_id[i],"id",getPackageName());
            spinnerID = (Spinner) findViewById(resID);

            if (spinnerID != null)
            {
                int selectedItemOfMySpinner = spinnerID.getSelectedItemPosition();
                String actualPositionOfMySpinner = (String) spinnerID.getItemAtPosition(selectedItemOfMySpinner);
                if (actualPositionOfMySpinner.equals("choose"))
                {
                    return  false;
                }
            }
        }
        return true;
    }



    public void submit(View view)
    {
        if (validateSpinners()) {

            //retrive current values
           // Toast.makeText(getApplicationContext(),GlobalVariables.facultyid+"",Toast.LENGTH_LONG).show();
          FirebaseAuth.getInstance().signInAnonymously();

            mDatabase1 = FirebaseDatabase.getInstance().getReference("professorData");
            mDatabase1.child(""+GlobalVariables.facultyid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                     sprof = dataSnapshot.getValue(ProfessorData.class);
                     updateProfessor(sprof);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException());
                }
            });



           Intent intent = new Intent(reviewp.this,ProfessorList.class);
           startActivity(intent);


        }
        else
            Toast.makeText(getApplicationContext(), "PLEASE REVIEW ALL", Toast.LENGTH_SHORT).show();
    }
    public void updateProfessor(ProfessorData sprof)
    {   int cou= Integer.parseInt(sprof.getCount());
        cou++;
        mDatabase = FirebaseDatabase.getInstance().getReference("professorData");
        mDatabase.child(""+GlobalVariables.facultyid).child("count").setValue(""+cou);

       // int temp=0;
        int cs= Integer.parseInt(sprof.getCs().trim());
        //temp=(cs/cou);
        Spinner c1=(Spinner)findViewById(R.id.comm);
        cs=cs+Integer.parseInt(c1.getSelectedItem().toString().trim());
        mDatabase = FirebaseDatabase.getInstance().getReference("professorData");
        mDatabase.child(""+GlobalVariables.facultyid).child("cs").setValue(""+cs);
        //temp=0;

        int ds= Integer.parseInt(sprof.getDs());
        Spinner d=(Spinner)findViewById(R.id.doubts);
        ds=ds+Integer.parseInt(d.getSelectedItem().toString());
        mDatabase = FirebaseDatabase.getInstance().getReference("professorData");
        mDatabase.child(""+GlobalVariables.facultyid).child("ds").setValue(""+ds);


        int pc= Integer.parseInt(sprof.getPun());
        Spinner p1=(Spinner)findViewById(R.id.punctuality);
        pc=pc+Integer.parseInt(p1.getSelectedItem().toString());
        mDatabase = FirebaseDatabase.getInstance().getReference("professorData");
        mDatabase.child(""+GlobalVariables.facultyid).child("pun").setValue(""+pc);


        int s= Integer.parseInt(sprof.getSubjectskill());
        Spinner s1=(Spinner)findViewById(R.id.subject_skill);
        s=s+Integer.parseInt(s1.getSelectedItem().toString());
        mDatabase = FirebaseDatabase.getInstance().getReference("professorData");
        mDatabase.child(""+GlobalVariables.facultyid).child("subjectskill").setValue(""+s);


        int pr= Integer.parseInt(sprof.getPs());
        Spinner pr2=(Spinner)findViewById(R.id.presentation);
        pr=pr+Integer.parseInt(pr2.getSelectedItem().toString());
        mDatabase = FirebaseDatabase.getInstance().getReference("professorData");
        mDatabase.child(""+GlobalVariables.facultyid).child("ps").setValue(""+pr);


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

