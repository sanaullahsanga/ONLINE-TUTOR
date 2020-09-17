package com.constructors.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.constructors.CompleteYourProfile.Tutor;
import com.constructors.Model.TutorModel;
import com.constructors.Profile.TutorProfile;
import com.constructors.RegisterLogin.MainActivity;
import com.constructors.findtutor.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentSearch extends AppCompatActivity {

    private Spinner spinnerQualification ;

    private RecyclerView recyclerView;

    private DatabaseReference databaseReferenceTutors;

//    public static String email;

    private ArrayList<String> listContactNo;
    private ArrayList<String> listName;
    private ArrayList<String> listAddress;
    private ArrayList<String> listQualification;
    private ArrayList<String> listExperience;
    private ArrayList<String> listUid;

    private AdapterStudentSearch adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerQualification = (Spinner) findViewById(R.id.spinnerQualification);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);


        databaseReferenceTutors= FirebaseDatabase.getInstance().getReference().child("Tutors");

//        final Query fetch = databaseReferenceTutors.orderByKey().equalTo("uid");

        databaseReferenceTutors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                listContactNo = new ArrayList<String>();
                listName = new ArrayList<String>();
                listAddress = new ArrayList<String>();
                listQualification = new ArrayList<String>();
                listExperience = new ArrayList<String>();
                listUid = new ArrayList<String>();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    String getKey = dataSnapshot1.getKey().toString();

                    if (dataSnapshot.child(getKey).hasChild("Approved")){

                    String getApprovedValue = dataSnapshot.child(getKey).child("Approved").getValue().toString();

                    if (dataSnapshot.child(getKey).hasChild("Approved") && getApprovedValue.equals("Yes")) {

                        listUid.add(getKey);

                        databaseReferenceTutors.child(getKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.hasChild("name") && dataSnapshot.hasChild("info")){


                                String name = dataSnapshot.child("name").getValue().toString();
                                String contactNo = dataSnapshot.child("info").child("tutPhoneNo").getValue().toString();
                                String address = dataSnapshot.child("info").child("tutAddress").getValue().toString();
                                String experience = dataSnapshot.child("info").child("tutExperience").getValue().toString();
                                String qualification = dataSnapshot.child("info").child("utQualification").getValue().toString();


                                listContactNo.add(contactNo);
                                listName.add(name);
                                listAddress.add(address);
                                listQualification.add(qualification);
                                listExperience.add(experience);

                            }

                                adapter = new AdapterStudentSearch(StudentSearch.this, listContactNo, listName, listAddress, listQualification, listExperience, listUid);
                                adapter.notifyDataSetChanged();
                                recyclerView.setAdapter(adapter);


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }




                }

            }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_logOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StudentSearch.this, MainActivity.class));
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
