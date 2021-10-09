package com.example.facultyreviewapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class student_update extends AppCompatActivity {
    ListView l1;
    ArrayList sname,b,c;
    DatabaseReference mDatabase;
    List<StudentData> studentDatas;
    student_update.CustomAdapter c1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_student_update);
        l1=(ListView)findViewById(R.id.S12);
        FirebaseAuth.getInstance().signInAnonymously();

        studentDatas = new ArrayList<>();


        mDatabase = FirebaseDatabase.getInstance().getReference("studentdata");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public  void onDataChange(DataSnapshot dataSnapshot) {
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
                        StudentData studentData = allprofessor;
                        studentDatas.add(studentData);

                    }catch(Exception ex){
                        Log.e("MyError",ex.getMessage());
                    }
                    //Toast.makeText(getApplicationContext(), allproducts.getName(), Toast.LENGTH_LONG).show();
                }
                //set adapter for listview
                c1=new student_update.CustomAdapter();
                l1.setAdapter(c1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
            }
        });

        l1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                StudentData studentData = studentDatas.get(i);
                showUpdateDeleteDialog(studentData.getSname(),studentData.getBarCodeID(), studentData.getSclass(),i);
                return true;
            }
        });

    }


    private void showUpdateDeleteDialog(String sname, final String barcodeID, String sclass, final int i) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText updateName = (EditText) dialogView.findViewById(R.id.update_dialog_name);
        final EditText updateBarcode = (EditText) dialogView.findViewById(R.id.update_dialog_bid);
        final EditText updateClass = (EditText) dialogView.findViewById(R.id.update_dialog_class);


        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(sname);


        final AlertDialog b = dialogBuilder.create();
        b.show();

        updateName.setText(sname);
        updateBarcode.setText(barcodeID);
        updateClass.setText(sclass);


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = updateName.getText().toString().trim();
                String barcode = updateBarcode.getText().toString().trim();
                String cls = updateClass.getText().toString().trim();

                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(cls)&&!TextUtils.isEmpty(barcode)) {
                    updateArtist(name, barcode, cls,i);
                    b.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(barcodeID,i);
                b.dismiss();
            }
        });
    }

    private boolean deleteArtist(String barcode, int i) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child("studentdata").child(barcode);

        dR.removeValue();
        studentDatas.remove(i);

        Toast.makeText(getApplicationContext(), "Student Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean updateArtist(String name, String barcode, String cls, int i) {
        //getting the specified artist reference

        StudentData s1=new StudentData();
        s1.setSname(name);
        s1.setBarCodeID(barcode);
        s1.setSclass(cls);

        studentDatas.set(i,s1);

        FirebaseDatabase.getInstance().getReference().child("studentdata").child(barcode).setValue(s1);

        Toast.makeText(getApplicationContext(), "Student Updated", Toast.LENGTH_LONG).show();
        return true;
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
            convertView = getLayoutInflater().inflate(R.layout.s_update, null);
            TextView t1 = (TextView) convertView.findViewById(R.id.names1);





            TextView t2 = (TextView) convertView.findViewById(R.id.bid1);

            t2.setText(b.get(position).toString());

            TextView t3 = (TextView) convertView.findViewById(R.id.cl1);
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


