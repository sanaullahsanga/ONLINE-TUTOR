package com.constructors.RegisterLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.constructors.CompleteYourProfile.Tutor;
import com.constructors.Model.User;
import com.constructors.Student.StudentChoose;
import com.constructors.Student.StudentSearch;
import com.constructors.findtutor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private Button stud1, Tutor1;
    private EditText editTextName, editTextLastName, editTextEmail, editTextPassword, editTextConfirmPassword ;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;

    public static String userName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        stud1 = (Button) findViewById(R.id.stud1);
//        Tutor1 = (Button) findViewById(R.id.Tutor1);

        editTextName = findViewById(R.id.getname_signup);
        editTextLastName = findViewById(R.id.getLastname_signup);
        editTextEmail = findViewById(R.id.getemail_signup);
        editTextPassword = findViewById(R.id.getpassword_signup);
        editTextConfirmPassword=findViewById(R.id.getconfirmpassword_signup);

        final String getUserCategory = chooseRegistrationCategory.userCategory;

        progressDialog = new ProgressDialog(this);

        stud1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getUserCategory.equals("Students")){

                    registerStudent();
                }
                else {
                    registerTutor();
                }

            }
        });


    }


    public void registerStudent() {
        final String name = editTextName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        final String concatName = name + lastName;



        if (name.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            editTextLastName.setError("Enter Last Name");
            editTextLastName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.input_error_email));
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() )
        {

            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;

        }





        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_password));
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.input_error_password_length));
            editTextPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError(getString(R.string.input_error_confirmPassword));
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (!confirmPassword.equals(password)){
            editTextConfirmPassword.setError(getString(R.string.input_error_unmatchPassword));
            editTextConfirmPassword.requestFocus();
            return;
        }


        progressDialog.setMessage("Registering");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            progressDialog.dismiss();

                            User user = new User(email, concatName, FirebaseAuth.getInstance().getCurrentUser().getUid());

                            userName = user.name;    // stroing use name in static variable to be used in tutor

                            FirebaseDatabase.getInstance().getReference("Students")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        startActivity(new Intent(getApplicationContext(), StudentChoose.class));
                                        Toast.makeText(Signup.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                    } else {
                                        progressDialog.dismiss();
                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void registerTutor() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();



        if (name.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.input_error_email));
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() )
        {

            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;

        }





        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_password));
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.input_error_password_length));
            editTextPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError(getString(R.string.input_error_confirmPassword));
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (!confirmPassword.equals(password)){
            editTextConfirmPassword.setError(getString(R.string.input_error_unmatchPassword));
            editTextConfirmPassword.requestFocus();
            return;
        }


        progressDialog.setMessage("Registering");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if (task.isSuccessful()) {

                            progressDialog.dismiss();



                            User user = new User(email, name, FirebaseAuth.getInstance().getCurrentUser().getUid() );



                            FirebaseDatabase.getInstance().getReference("Tutors")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        startActivity(new Intent(getApplicationContext(), Tutor.class));
                                        Toast.makeText(Signup.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                    } else {
                                        progressDialog.dismiss();
                                        //display a failure message
                                    }
                                }
                            });


                            FirebaseDatabase.getInstance().getReference("Tutors") .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("Approved").setValue("Fresh");



//                            FirebaseDatabase.getInstance().getReference("Tutors")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if (task.isSuccessful()) {
//
//                                        startActivity(new Intent(getApplicationContext(), choose.class));
//                                        Toast.makeText(Signup.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
//                                    } else {
//                                        progressDialog.dismiss();
//                                        //display a failure message
//                                    }
//                                }
//                            });

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
