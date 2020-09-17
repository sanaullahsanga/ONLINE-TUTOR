package com.constructors.RegisterLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.constructors.CompleteYourProfile.Tutor;
import com.constructors.findtutor.R;
import com.google.firebase.auth.FirebaseAuth;

public class choose extends AppCompatActivity {


    Button stud1, Tutor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        stud1 = (Button) findViewById(R.id.stud1);
        Tutor1 = (Button) findViewById(R.id.btnCompleteYourProfile);
//        stud1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(choose.this, Register_as_student.class);
////                startActivity(intent);
//            }
//        });
        Tutor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(choose.this, Tutor.class);
                startActivity(intent);
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
                startActivity(new Intent(choose.this, MainActivity.class));
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
