package com.constructors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.constructors.AdminPortal.AdminDisapproved;
import com.constructors.AdminPortal.AdminWaitingTutorProfile;
import com.constructors.RegisterLogin.MainActivity;
import com.constructors.RegisterLogin.choose;
import com.constructors.Student.AdapterStudentSearch;
import com.constructors.Student.StudentSearch;
import com.constructors.findtutor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_admin);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorites:

                                startActivity(new Intent(AdminActivity.this, StudentSearch.class));


                                break;
                            case R.id.action_schedules:

                                startActivity(new Intent(AdminActivity.this, AdminDisapproved.class));



                                break;

                        }
                        return false;
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
                startActivity(new Intent(AdminActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
