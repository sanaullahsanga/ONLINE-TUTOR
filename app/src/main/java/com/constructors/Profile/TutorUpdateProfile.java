package com.constructors.Profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.constructors.CompleteYourProfile.Tutor;
import com.constructors.Model.TutorModel;
import com.constructors.findtutor.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class TutorUpdateProfile extends AppCompatActivity {

    DatabaseReference databaseTutors ;
    private FirebaseAuth auth;
    private FirebaseUser currentuser;
    private String currentuserid;

    private EditText enterContact , enterAddress, enterQualification, enterExperience;
    private Button saveInfo;

    CheckBox primaryCB, middleCB, matriculationCB, matricArtsCB, intermediateCB, bachelorsCB, icom;
    RadioGroup matricRG, interRG, bachelorsRG;

    LinearLayout matricLL, interLL, bachelorsLL;

    private Spinner spinnerExperience, spinnerQualification;

    public static String pushIdAddress, pushIdSubjects, pushIdTutorInfo, pushidProfileStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_update_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        enterContact = (EditText) findViewById(R.id.contactNo);
        enterAddress = (EditText) findViewById(R.id.address);

        spinnerExperience = (Spinner) findViewById(R.id.spinner);
        spinnerQualification = (Spinner) findViewById(R.id.spinnerQualification);


        saveInfo = (Button) findViewById(R.id.btnSave);



        Bundle bundle = getIntent().getExtras();

//Extract the data…
        String getContactBundle = bundle.getString("getContactBundle");
        String getAddressBundle = bundle.getString("getAddressBundle");
        String getQualificationBundle = bundle.getString("getQualificationBundle");
        String getExperienceBundle = bundle.getString("getExperienceBundle");


        enterContact.setText(getContactBundle);
        enterAddress.setText(getAddressBundle);







        primaryCB = findViewById(R.id.checkbox_primary);
        middleCB = findViewById(R.id.checkbox_middle);
        matriculationCB = findViewById(R.id.checkbox_matriculationScience);
        intermediateCB = findViewById(R.id.checkbox_FscPreEngineering);
        bachelorsCB = findViewById(R.id.checkbox_Bachelors);



        matricRG= findViewById(R.id.radiogroup_matric);
        interRG= findViewById(R.id.radiogroup_inter);
        bachelorsRG= findViewById(R.id.radiogroup_bachelors);


        matricLL = findViewById(R.id.matricLL);
        interLL = findViewById(R.id.interLL);
        bachelorsLL = findViewById(R.id.bachelorsLL);


        matricLL.setVisibility(View.GONE);
        interLL.setVisibility(View.GONE);
        bachelorsLL.setVisibility(View.GONE);





        matriculationCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    matricLL.setVisibility(View.VISIBLE);
                } else {
                    matricLL.setVisibility(View.GONE);
                }
            }
        });


        intermediateCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    interLL.setVisibility(View.VISIBLE);


                } else {
                    interLL.setVisibility(View.GONE);

                }
            }
        });


        bachelorsCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    bachelorsLL.setVisibility(View.VISIBLE);
                } else {
                    bachelorsLL.setVisibility(View.GONE);
                }
            }
        });







        String getAddress = enterAddress.getText().toString();



        auth= FirebaseAuth.getInstance();
        currentuser=auth.getCurrentUser();
        currentuserid=currentuser.getUid();

        databaseTutors= FirebaseDatabase.getInstance().getReference().child("Tutors").child(currentuserid);


        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getContactNo = enterContact.getText().toString();
//                int finalContact = Integer.parseInt(getContactNo);
                String getAddress = enterAddress.getText().toString();

                String getExperience = spinnerExperience.getSelectedItem().toString();
                String getQualification = spinnerQualification.getSelectedItem().toString();

                try{


                    if (!getContactNo.isEmpty() && !getAddress.isEmpty()) {


                    }

                    if (getContactNo.isEmpty()) {
                        enterContact.setError("Contact Number is required");
                        enterContact.requestFocus();

//                    LatLng p = getLocationFromAddress(getApplicationContext(), getAddress);
////                    double f = p.latitude;
//                    String a = p.toString();
//
//
//
//                        Toast.makeText(Tutor.this, a, Toast.LENGTH_SHORT).show();





                    } else if (getAddress.isEmpty()) {
                        enterAddress.setError("Address is required");
                        enterAddress.requestFocus();
                    }

                    else if(getQualification.equals("Select Qualification")){
                        Toast.makeText(TutorUpdateProfile.this, "Select qualification", Toast.LENGTH_SHORT).show();
                    }

                    else if(getExperience.equals("Select Experience")){
                        Toast.makeText(TutorUpdateProfile.this, "Select experience", Toast.LENGTH_SHORT).show();
                    }

                    else if (!primaryCB.isChecked() && !middleCB.isChecked() && !matriculationCB.isChecked() &&  !intermediateCB.isChecked() &&  !bachelorsCB.isChecked() ){

                        Toast.makeText(TutorUpdateProfile.this, "Select any Class", Toast.LENGTH_SHORT).show();

                    }

                    else if (matriculationCB.isChecked() && matricRG.getCheckedRadioButtonId() == -1){
                        Toast.makeText(TutorUpdateProfile.this, "You have to select one category for Matriculation", Toast.LENGTH_SHORT).show();

                    }
                    else if (intermediateCB.isChecked() && interRG.getCheckedRadioButtonId() == -1 ){
                        Toast.makeText(TutorUpdateProfile.this, "You have to select one category for Intermediate", Toast.LENGTH_SHORT).show();

                    }

                    else if (bachelorsCB.isChecked() && bachelorsRG.getCheckedRadioButtonId() == -1 ){
                        Toast.makeText(TutorUpdateProfile.this, "You have to select one category for bachelors", Toast.LENGTH_SHORT).show();

                    }


                    else {



                        if (primaryCB.isChecked()){
                            String getData = primaryCB.getText().toString();

//                        pushIdSubjects = databaseTutors.push().getKey();

                            databaseTutors.child("Classes").child("primary").setValue(getData);
                        }
                        if (middleCB.isChecked()){
                            String getData = middleCB.getText().toString();
                            databaseTutors.child("Classes").child("middle").setValue(getData);
                        }
                        if (matriculationCB.isChecked()){

                            RadioButton selectedRadioButton = findViewById(matricRG.getCheckedRadioButtonId());
                            String getData = selectedRadioButton.getText().toString().trim();
                            databaseTutors.child("Classes").child("matric").setValue(getData);
                        }
                        if (intermediateCB.isChecked()){
                            RadioButton selectedRadioButton = findViewById(interRG.getCheckedRadioButtonId());
                            String getData = selectedRadioButton.getText().toString().trim();
                            databaseTutors.child("Classes").child("inter").setValue(getData);
                        }

                        if (bachelorsCB.isChecked()){

                            RadioButton selectedRadioButton = findViewById(bachelorsRG.getCheckedRadioButtonId());
                            String getData = selectedRadioButton.getText().toString().trim();
                            databaseTutors.child("Classes").child("bachelors").setValue(getData);
                        }


                        TutorModel tutorModel = new TutorModel(getContactNo, getAddress, getQualification, getExperience, "Fsd");

//                    pushIdTutorInfo = databaseTutors.push().getKey();

                        databaseTutors.child("info").setValue(tutorModel);

                        LatLng p = getLocationFromAddress(getApplicationContext(), getAddress);




                    }
                }
                catch(Exception e){
                    Log.e("Register Exception", e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(TutorUpdateProfile.this);
                    builder.setTitle("Error");
                    builder.setMessage(e.getMessage());
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }





            }
        });

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }



            Address location = address.get(0);

            if (address.size()> 0) {

                p1 = new LatLng(location.getLatitude(), location.getLongitude());



//                    pushIdAddress = databaseTutors.push().getKey();

//            DatabaseReference pushLatLng = databaseTutors.push();
                databaseTutors.child("location").child("Lat").setValue(location.getLatitude());
                databaseTutors.child("location").child("Lng").setValue(location.getLongitude());




            }

            else {
                Toast.makeText(context, "Oops!!", Toast.LENGTH_SHORT).show();
            }



        } catch (IOException ex) {

//            ex.printStackTrace();

            AlertDialog.Builder builder = new AlertDialog.Builder(TutorUpdateProfile.this);
            builder.setTitle("Error");
            builder.setMessage("Enter proper address with City name");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }

        return p1;
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

