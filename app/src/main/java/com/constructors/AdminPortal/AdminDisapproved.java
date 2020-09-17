package com.constructors.AdminPortal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.constructors.RegisterLogin.MainActivity;
import com.constructors.Student.StudentSearch;
import com.constructors.findtutor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminDisapproved extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReferenceWaitingTutors;

    private ArrayList<String> listContactNo;
    private ArrayList<String> listName;
    private ArrayList<String> listAddress;
    private ArrayList<String> listQualification;
    private ArrayList<String> listExperience;
    private ArrayList<String> listUid;

    private AdapterAdminDisapproved adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_disapproved);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewAdminDisapproved);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorites:

                                startActivity(new Intent(AdminDisapproved.this, StudentSearch.class));


                                break;
                            case R.id.action_schedules:



                                break;

                        }
                        return false;
                    }
                });

        databaseReferenceWaitingTutors= FirebaseDatabase.getInstance().getReference().child("Waiting Tutors");


        databaseReferenceWaitingTutors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    listContactNo = new ArrayList<String>();
                    listName = new ArrayList<String>();
                    listAddress = new ArrayList<String>();
                    listQualification = new ArrayList<String>();
                    listExperience = new ArrayList<String>();
                    listUid = new ArrayList<String>();


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        String getKey = dataSnapshot1.getKey().toString();

                        if (dataSnapshot.child(getKey).hasChild("name") && dataSnapshot.child(getKey).hasChild("info")){


                            String checkApprove = dataSnapshot.child(getKey).child("name").getValue().toString();

                        if (!checkApprove.equals("Approved")) {


                            listUid.add(getKey);

                            databaseReferenceWaitingTutors.child(getKey).addValueEventListener(new ValueEventListener() {
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


                                    adapter = new AdapterAdminDisapproved(AdminDisapproved.this, listContactNo, listName, listAddress, listQualification, listExperience, listUid);
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
                startActivity(new Intent(AdminDisapproved.this, MainActivity.class));
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
