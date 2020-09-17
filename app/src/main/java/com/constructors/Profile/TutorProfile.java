package com.constructors.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.constructors.RegisterLogin.MainActivity;
import com.constructors.RegisterLogin.choose;
import com.constructors.findtutor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TutorProfile extends AppCompatActivity {

    TextView profileEmail , userName;
    private TextView contactNo, address, qualification, experience;
    Button saveProfile;
    private ListView listView;

    private DatabaseReference databaseReferenceTutors;
    private FirebaseAuth auth;
    private FirebaseUser currentuser;
    private String currentuserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();
        currentuser=auth.getCurrentUser();
        currentuserid=currentuser.getUid();
        databaseReferenceTutors= FirebaseDatabase.getInstance().getReference().child("Tutors");


        profileEmail = (TextView) findViewById(R.id.email_profile);
        userName = (TextView) findViewById(R.id.UserName);

        contactNo = (TextView) findViewById(R.id.contactTV);
        address = (TextView) findViewById(R.id.addressTV);
        qualification = (TextView) findViewById(R.id.qualificationTV);
        experience = (TextView) findViewById(R.id.experienceTV);

        listView = (ListView) findViewById(R.id.listviewTutorProfile);





        saveProfile = (Button) findViewById(R.id.btn_save_profile);

        profileEmail.setText(currentuser.getEmail());

        databaseReferenceTutors.child(currentuserid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String getUserName = dataSnapshot.child("name").getValue().toString();
                    userName.setText(getUserName);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReferenceTutors.child(currentuserid).child("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    String getContact = dataSnapshot.child("tutPhoneNo").getValue().toString();
                    String getAddress = dataSnapshot.child("tutAddress").getValue().toString();
                    String getQualification = dataSnapshot.child("utQualification").getValue().toString();
                    String getExperience = dataSnapshot.child("tutExperience").getValue().toString();

                    contactNo.setText(getContact);
                    address.setText(getAddress);
                    qualification.setText(getQualification);
                    experience.setText(getExperience);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        databaseReferenceTutors.child(currentuserid).child("Classes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                final List<String> propertyAddressList = new ArrayList<String>();

                if (dataSnapshot.exists()) {

                        if (dataSnapshot.hasChild("primary")){
                            propertyAddressList.add(dataSnapshot.child("primary").getValue().toString());
                        }

                        if (dataSnapshot.hasChild("matric")){
                            propertyAddressList.add(dataSnapshot.child("matric").getValue().toString());
                        }

                        if (dataSnapshot.hasChild("middle")){
                            propertyAddressList.add(dataSnapshot.child("middle").getValue().toString());
                        }

                        if (dataSnapshot.hasChild("inter")){
                            propertyAddressList.add(dataSnapshot.child("inter").getValue().toString());
                        }

                    if (dataSnapshot.hasChild("FSC")){
                        propertyAddressList.add(dataSnapshot.child("FSC").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("ICOM")){
                        propertyAddressList.add(dataSnapshot.child("ICOM").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("bachelors")){
                        propertyAddressList.add(dataSnapshot.child("bachelors").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("bachelorsZolo")){
                        propertyAddressList.add(dataSnapshot.child("bachelorsZolo").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("bachelorsPsy")){
                        propertyAddressList.add(dataSnapshot.child("bachelorsPsy").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("bachelorsEng")){
                        propertyAddressList.add(dataSnapshot.child("bachelorsEng").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("bachelorsMath")){
                        propertyAddressList.add(dataSnapshot.child("bachelorsMath").getValue().toString());
                    }


                }

                final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(TutorProfile.this, R.layout.tutor_profile_list_view, propertyAddressList);

                listView.setAdapter(adapter1);



                }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        final Query fetch = databaseReferenceTutors.orderByChild("info");
//
//        databaseReferenceTutors.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
//                {
//
//                    String reference = dataSnapshot1.getRef().toString();
//                    String[] tokens = reference.split("/");
//                    String parentKey = tokens[tokens.length - 1];
//
//
//                    Toast.makeText(TutorProfile.this, parentKey, Toast.LENGTH_SHORT).show();
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        }) ;



        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String putContactNo = contactNo.getText().toString();
                String putAddress = address.getText().toString();
                String putQualification = qualification.getText().toString();
                String putExperience = experience.getText().toString();


                Intent i = new Intent(TutorProfile.this, TutorUpdateProfile.class);


//Create the bundle
                Bundle bundle = new Bundle();

//Add your data to bundle
                bundle.putString("getContactBundle", putContactNo);
                bundle.putString("getAddressBundle", putAddress);
                bundle.putString("getQualificationBundle", putQualification);
                bundle.putString("getExperienceBundle", putExperience);

//Add the bundle to the intent
                i.putExtras(bundle);

//Fire that second activity
                startActivity(i);


            }
        });

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
                startActivity(new Intent(TutorProfile.this, MainActivity.class));
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

