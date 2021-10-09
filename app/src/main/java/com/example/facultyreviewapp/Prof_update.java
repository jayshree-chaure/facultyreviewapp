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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.facultyreviewapp.model.ProfessorData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.FragmentActivity;

import androidx.appcompat.app.AppCompatActivity;

public class Prof_update extends AppCompatActivity {
    ListView l1;
    ArrayList sname,b,c,cs,ps,ds,pr,ct,pn;
    DatabaseReference mDatabase;
    List<ProfessorData> studentDatas;
    public AwesomeValidation awesomeValidation;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        setContentView(R.layout.activity_prof_update);
        l1=(ListView)findViewById(R.id.S123);
        FirebaseAuth.getInstance().signInAnonymously();

        studentDatas = new ArrayList<>();



        mDatabase = FirebaseDatabase.getInstance().getReference("professorData");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public  void onDataChange(DataSnapshot dataSnapshot) {
                sname=new ArrayList();
                b=new ArrayList();
                c=new ArrayList();
                cs=new ArrayList();
                ps=new ArrayList();
                ds=new ArrayList();
                pr=new ArrayList();
                ct=new ArrayList();
                pn=new ArrayList();
                for(DataSnapshot x:dataSnapshot.getChildren()) {
                    ProfessorData allprofessor = x.getValue(ProfessorData.class);
                    Log.e("MyError","Testing");
                    try{
                        sname.add("Name : "+allprofessor.getPname().toString() );
                        b.add("ID : "+allprofessor.getPid().toString() );
                        c.add("Subject : "+allprofessor.getPinfo().toString() );
                        cs.add(
                                allprofessor.getCs().toString() );
                        ps.add(allprofessor.getPs().toString() );

                        ds.add(allprofessor.getDs().toString() );
                        pr .add(allprofessor.getSubjectskill().toString() );
                        ct.add(allprofessor.getCount().toString() );
                        pn.add(allprofessor.getPun().toString() );
                        ProfessorData professorData = allprofessor;
                        studentDatas.add(professorData);


                    }catch(Exception ex){
                        Log.e("MyError",ex.getMessage());
                    }

                }
                //set adapter for listview
                Prof_update.CustomAdapter c1=new Prof_update.CustomAdapter();
                l1.setAdapter(c1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_SHORT).show();


            }
        });

        l1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProfessorData professorData = studentDatas.get(i);
                showUpdateDeleteDialog(professorData.getPname(),professorData.getPid(), professorData.getPinfo(),i);
                return true;
            }
        });

    }


    private void showUpdateDeleteDialog(String sname, final String barcodeID, String sclass, final int i ) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog1, null);
        dialogBuilder.setView(dialogView);

        final EditText updateName = (EditText) dialogView.findViewById(R.id.update_dialog_name1);
        final EditText updateBarcode = (EditText) dialogView.findViewById(R.id.update_dialog_bid1);
        final EditText updateClass = (EditText) dialogView.findViewById(R.id.update_dialog_class1);


        awesomeValidation.addValidation(this, R.id.update_dialog_name1, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.update_dialog_class1,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.Classerror);
        awesomeValidation.addValidation(this, R.id.update_dialog_bid1,"(?<!\\d)\\d{10}(?!\\d)" , R.string.iderror);









        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist1);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist1);

        dialogBuilder.setTitle(sname);


        final AlertDialog b = dialogBuilder.create();
        b.show();

            updateName.setText(sname);
            updateBarcode.setText(barcodeID);
            updateClass.setText(sclass);



        awesomeValidation.addValidation(this, R.id.update_dialog_name1, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.update_dialog_class1,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.Classerror);
        awesomeValidation.addValidation(this, R.id.update_dialog_bid1,"(?<!\\d)\\d{10}(?!\\d)" , R.string.iderror);



        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = updateName.getText().toString().trim();
                String barcode = updateBarcode.getText().toString().trim();
                String cls = updateClass.getText().toString().trim();


                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(cls)&&!TextUtils.isEmpty(barcode)){


                        updateArtist(name, barcode, cls, i);
                        b.dismiss();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Enter Valid Record", Toast.LENGTH_LONG).show();

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

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child("professorData").child(barcode);

        dR.removeValue();
        studentDatas.remove(i);

        Toast.makeText(getApplicationContext(), "Professor Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean updateArtist(String name, String barcode, String cls, int i) {
        //getting the specified artist reference

        ProfessorData s1=new ProfessorData();
        s1.setPname(name);

        s1.setPid(barcode);
        s1.setPinfo(cls);
        s1.setCs("3");
        s1.setDs("4");
        s1.setPs("2");
        s1.setPun("5");
        s1.setSubjectskill("2");
        s1.setCount("1");
        studentDatas.set(i,s1);

        FirebaseDatabase.getInstance().getReference().child("professorData").child(barcode).setValue(s1);

        Toast.makeText(getApplicationContext(), "Professor Updated", Toast.LENGTH_LONG).show();
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
            convertView = getLayoutInflater().inflate(R.layout.profupd, null);
            TextView t1 = (TextView) convertView.findViewById(R.id.pnames1);





            TextView t2 = (TextView) convertView.findViewById(R.id.pid1);

            t2.setText(b.get(position).toString());

            TextView t3 = (TextView) convertView.findViewById(R.id.psub);
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



