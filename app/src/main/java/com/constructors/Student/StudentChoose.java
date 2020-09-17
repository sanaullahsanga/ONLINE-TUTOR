package com.constructors.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.constructors.Profile.StudentProfile;
import com.constructors.RegisterLogin.LoginAs;
import com.constructors.RegisterLogin.MainActivity;
import com.constructors.RegisterLogin.chooseRegistrationCategory;
import com.constructors.findtutor.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentChoose extends AppCompatActivity {

    private Button viewTutorsList, viewTutorsOnMap;
    private DrawerLayout drawerLayout;
    private TextView navTextView , navUserName;
    DatabaseReference databaseReferenceStudents;
    String currentuserid;
    ImageView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_choose);



        viewTutorsList = (Button) findViewById(R.id.viewList);
        viewTutorsOnMap = (Button) findViewById(R.id.viewMap);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View view= navigationView.inflateHeaderView(R.layout.nav_header);
        navTextView = (TextView)view.findViewById(R.id.emailnavbar);
        navUserName = (TextView) view.findViewById(R.id.usernamenavbar);
        nav = (ImageView) findViewById(R.id.nav);

        currentuserid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        navTextView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


//        // Sets the Toolbar to act as the ActionBar for this Activity window.
//        // Make sure the toolbar exists in the activity and is not null


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        databaseReferenceStudents= FirebaseDatabase.getInstance().getReference().child("Students");

        databaseReferenceStudents.child(currentuserid).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String getUserName = dataSnapshot.getValue().toString();
                    navUserName.setText(getUserName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.openDrawer(GravityCompat.START);

            }
        });



        viewTutorsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentChoose.this, StudentSearch.class);
                startActivity(intent);
            }
        });
        viewTutorsOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentChoose.this, MapsShowAllTutors.class);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {


                        int id = menuItem.getItemId();

                        if (id == R.id.nav_camera) {
                            // Handle the camera action
                            startActivity(new Intent(getApplicationContext(), StudentProfile.class));

                        } else if (id == R.id.nav_gallery)
                        {

                            startActivity(new Intent(StudentChoose.this,StudentSearch.class));
                        }

                        else if (id == R.id.nav_slideshow) {

                            startActivity(new Intent(getApplicationContext(), MapsShowAllTutors.class));

                        } else if (id == R.id.nav_manage) {

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            return true;


                        } else if (id == R.id.nav_share)

                        {

                            String getNumber = "03067059167";

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", getNumber, null));
                            startActivity(intent);

                        }

                   DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;


                    }
                });




        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {

//
                        // Respond when the drawer is opened

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}