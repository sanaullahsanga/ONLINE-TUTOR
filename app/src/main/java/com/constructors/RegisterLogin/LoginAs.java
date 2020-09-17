package com.constructors.RegisterLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.constructors.findtutor.R;

public class LoginAs extends AppCompatActivity {

    private ImageView admin, tutor, student;
    Animation animh,animu,animp;
    public static String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        admin = (ImageView) findViewById(R.id.adminImg);
        tutor = (ImageView) findViewById(R.id.tutorImg);
        student = (ImageView) findViewById(R.id.studentimg);

        animu= AnimationUtils.loadAnimation(this,R.anim.bottom_to_top_1450);
        tutor.setAnimation(animu);
        animp=AnimationUtils.loadAnimation(this,R.anim.bottom_to_top_1450);
        student.setAnimation(animp);

        admin.setOnClickListener(onClickListener);
        tutor.setOnClickListener(onClickListener);
        student.setOnClickListener(onClickListener);


    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.adminImg:

                    userType = "Admin";
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    break;

                case R.id.tutorImg:

                    userType = "Tutors";
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    break;

                case R.id.studentimg:

                    userType = "Students";
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    break;
            }

        }
    };

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
