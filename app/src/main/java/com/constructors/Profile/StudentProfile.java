package com.constructors.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.constructors.Model.User;
import com.constructors.Student.StudentChoose;
import com.constructors.findtutor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {

    EditText profileName ;
    TextView profileEmail , userName;
    Button saveProfile;

    DatabaseReference databaseReferenceStudents ;
    private FirebaseAuth auth;
    private FirebaseUser currentuser;
    private String currentuserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();
        currentuser=auth.getCurrentUser();
        currentuserid=currentuser.getUid();
        databaseReferenceStudents= FirebaseDatabase.getInstance().getReference().child("Students");


        profileEmail = (TextView) findViewById(R.id.email_profile);
        userName = (TextView) findViewById(R.id.UserName);
        profileName = (EditText) findViewById(R.id.name_profile);
        saveProfile = (Button) findViewById(R.id.btn_save_profile);

        profileEmail.setText(currentuser.getEmail());

        databaseReferenceStudents.child(currentuserid).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String getUserName = dataSnapshot.getValue().toString();
                    userName.setText(getUserName);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getEditName = profileName.getText().toString();
                String getEditEmail = profileEmail.getText().toString();

                if (!getEditName.isEmpty()) {

                    User user = new User(getEditEmail, getEditName, currentuserid);
                    databaseReferenceStudents.child(currentuserid).child("name").setValue(getEditName);

                    startActivity(new Intent(StudentProfile.this , StudentChoose.class));

                }
                else {
                    Toast.makeText(StudentProfile.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
