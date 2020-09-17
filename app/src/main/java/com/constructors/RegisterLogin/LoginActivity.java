package com.constructors.RegisterLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.constructors.AdminActivity;
import com.constructors.CompleteYourProfile.ApprovedTutor;
import com.constructors.CompleteYourProfile.PendingTutor;
import com.constructors.CompleteYourProfile.Tutor;
import com.constructors.Profile.TutorProfile;
import com.constructors.Student.StudentChoose;
import com.constructors.Student.StudentSearch;
import com.constructors.findtutor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private TextView forgetPassword;
    Animation animh,animu,animp;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentuser;
    private String currentuserid;
    private String getLoginAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        String getUserType = LoginAs.userType;
//        Toast.makeText(this, getUserType, Toast.LENGTH_SHORT).show();

        loginEmail = (EditText)findViewById(R.id.getemail);
        loginPassword = (EditText)findViewById(R.id.getpassword);
        loginButton = (Button) findViewById(R.id.login_btn);
        forgetPassword = (TextView) findViewById(R.id.forgetpass_txtview);

        getLoginAs = LoginAs.userType;

        animu= AnimationUtils.loadAnimation(this,R.anim.right_to_left_1450);
        loginEmail.setAnimation(animu);
        animp=AnimationUtils.loadAnimation(this,R.anim.right_to_left_1850);
        loginPassword.setAnimation(animp);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        currentuser=firebaseAuth.getCurrentUser();
        if (currentuser != null) {
            currentuserid = currentuser.getUid();
        }

        progressDialog = new ProgressDialog(this);

//        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(currentuser != null){

            final String userType = LoginAs.userType;
            final DatabaseReference databaseReferenceUserType = FirebaseDatabase.getInstance().getReference().child(userType);
            databaseReferenceUserType.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists() && userType.equals("Tutors"))
                    {
                        finish();


                        databaseReferenceUserType.child(currentuserid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists() && dataSnapshot.hasChild("Approved")){



                                String getApproved = dataSnapshot.child("Approved").getValue().toString();

                                if (getApproved.equals("Yes")){


                                    startActivity(new Intent(LoginActivity.this, ApprovedTutor.class));

                                }

                                else if (getApproved.equals("No")){

                                    startActivity(new Intent(LoginActivity.this, PendingTutor.class));

                                }

                                else {

                                    startActivity(new Intent(LoginActivity.this, Tutor.class));

                                }

                            }



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                    }

                    else if (dataSnapshot.exists() && userType.equals("Students"))
                    {

                        databaseReferenceUserType.child(currentuserid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){

                                    startActivity(new Intent(LoginActivity.this, StudentChoose.class));

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                    else if (dataSnapshot.exists() && userType.equals("Admin"))
                    {
                       if (dataSnapshot.getValue().equals(currentuserid)){

                           startActivity(new Intent(LoginActivity.this, AdminActivity.class));

                       }
                    }

                    else {

                        Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


//            final DatabaseReference databaseReference1 =      FirebaseDatabase.getInstance().getReference().child("Registered Tutors");
//
//            databaseReference1.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    if (dataSnapshot.exists()){
//
//                        databaseReference1.child(currentuserid).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                if (dataSnapshot.exists()){
//                                    startActivity(new Intent(LoginActivity.this, choose.class));
//
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//
//
//


        }



        //for making 'Sign up' from "Dont have an id? Sign up" form login screen clickable"
        final TextView signuptextview = findViewById(R.id.signup_txtview);

        String textOfSignupOnLoginScreen = "Don't Have an account? Sign up";

        SpannableString ssForSignupOnLoginScreen = new SpannableString(textOfSignupOnLoginScreen); // spannable string allow us to perform functions on substrings

        ForegroundColorSpan fcsForSignup = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));
        ClickableSpan makingSignupClickable = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //open next activity here
                //open sign up page
                if (widget==signuptextview){
                    startActivity(new Intent(getApplicationContext(), chooseRegistrationCategory.class));
                }


            }

            @Override
            //method for customizing the "Sign up"
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.colorAccent)); // to make the "sign up" look blue
                ds.setUnderlineText(false); //to remove the underline from signup
            }
        };

        ssForSignupOnLoginScreen.setSpan(makingSignupClickable, 23,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // exclusive means that action will be performed and inclusive means that action will not be performed. first exclisove means the start and second means the end.
        ssForSignupOnLoginScreen.setSpan(fcsForSignup, 23,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signuptextview.setText(ssForSignupOnLoginScreen);
        signuptextview.setMovementMethod(LinkMovementMethod.getInstance()); //for my click to work-

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = loginEmail.getText().toString().trim();
                final String password = loginPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    loginEmail.setError(getString(R.string.input_error_email));
                    loginEmail.requestFocus();
                    return;
                }

                else if (password.isEmpty()) {
                    loginPassword.setError(getString(R.string.input_error_password));
                    loginPassword.requestFocus();
                    return;
                }
                else {
                    validate(loginEmail.getText().toString(), loginPassword.getText().toString());
                }
            }
        });




//        forgetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            }
//        });



    }

    private void validate(final String userName, final String userPassword) {
        progressDialog.setMessage("Logging in");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    final String getUserType= LoginAs.userType;

                    final String currentUserid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                  final DatabaseReference databaseReference =    FirebaseDatabase.getInstance().getReference(getUserType);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                          if (dataSnapshot.exists() && getUserType.equals("Tutors"))
                          {
                              progressDialog.dismiss();
                              Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();



                              databaseReference.child(currentUserid).addValueEventListener(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(DataSnapshot dataSnapshot) {

                                      if (dataSnapshot.exists() && dataSnapshot.hasChild("Approved")){



                                          String getApproved = dataSnapshot.child("Approved").getValue().toString();

                                          if (getApproved.equals("Yes")){


                                              startActivity(new Intent(LoginActivity.this, ApprovedTutor.class));

                                          }

                                          else if (getApproved.equals("No")){

                                              startActivity(new Intent(LoginActivity.this, PendingTutor.class));

                                          }

                                          else {

                                              startActivity(new Intent(LoginActivity.this, Tutor.class));

                                          }

                                      }

                                      else {
                                          progressDialog.dismiss();
                                          Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                                          FirebaseAuth.getInstance().signOut();
                                      }


                                  }

                                  @Override
                                  public void onCancelled(DatabaseError databaseError) {

                                  }
                              });




                          }

//                          else if (!dataSnapshot.exists() && getUserType.equals("Tutors")){
//
//
//                              final DatabaseReference databaseReference1 =      FirebaseDatabase.getInstance().getReference().child("Registered Tutors");
//
//                              databaseReference1.addValueEventListener(new ValueEventListener() {
//                                  @Override
//                                  public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                      if (dataSnapshot.exists()){
//
//                                          databaseReference1.child(currentUserid).addValueEventListener(new ValueEventListener() {
//                                              @Override
//                                              public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                                  if (dataSnapshot.exists()){
//                                                      startActivity(new Intent(LoginActivity.this, choose.class));
//
//                                                  }
//
//                                              }
//
//                                              @Override
//                                              public void onCancelled(DatabaseError databaseError) {
//
//                                              }
//                                          });
//
//
//                                      }
//
//                                  }
//
//                                  @Override
//                                  public void onCancelled(DatabaseError databaseError) {
//
//                                  }
//                              });
//
//
//                          }

                          else if (dataSnapshot.exists() && getUserType.equals("Students"))
                          {
                              if (dataSnapshot.hasChild(currentUserid)){
                              startActivity(new Intent(LoginActivity.this, StudentChoose.class));
                              }
                              else {
                                  progressDialog.dismiss();
                                  Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                                  FirebaseAuth.getInstance().signOut();
                              }


                          }

                          else if (dataSnapshot.exists() && getUserType.equals("Admin"))
                          {
                              if (dataSnapshot.getValue().equals(currentUserid)){
                              startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                              }

                              else {
                                  progressDialog.dismiss();
                                  Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                                  FirebaseAuth.getInstance().signOut();
                              }

                          }



                          else {
                              progressDialog.dismiss();
                              Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
                              FirebaseAuth.getInstance().signOut();
                          }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });





//                    final DatabaseReference databaseReference1 =      FirebaseDatabase.getInstance().getReference().child("Registered Tutors");
//
//                    databaseReference1.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                            if (dataSnapshot.exists() && getUserType.equals("Tutors")){
//
//                                databaseReference1.child(currentUserid).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                        if (dataSnapshot.exists()){
//                                            startActivity(new Intent(LoginActivity.this, choose.class));
//
//                                        }
//
//                                        else {
//                                            progressDialog.dismiss();
//                                            Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
//                                            FirebaseAuth.getInstance().signOut();
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });
//
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//



                    checkEmailVerification();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                }
            }
        });



    }

    private void checkEmailVerification() {

        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();


        //  startActivity(new Intent(LoginActivity.this, Signup.class));

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
