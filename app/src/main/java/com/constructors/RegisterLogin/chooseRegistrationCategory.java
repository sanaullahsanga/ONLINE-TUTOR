package com.constructors.RegisterLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.constructors.findtutor.R;

public class chooseRegistrationCategory extends AppCompatActivity implements View.OnClickListener {

    private Button stud1, Tutor1;
    public static String userCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_registration_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stud1 = (Button) findViewById(R.id.btnStudent);
        Tutor1 = (Button) findViewById(R.id.btnTutor);

        findViewById(R.id.btnStudent).setOnClickListener(this);
        findViewById(R.id.btnTutor).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStudent:
                userCategory = "Students";
                startActivity(new Intent(chooseRegistrationCategory.this, Signup.class));
                break;

            case R.id.btnTutor:
                userCategory = "Tutors";
                startActivity(new Intent(chooseRegistrationCategory.this, Signup.class));

                break;

        }
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
