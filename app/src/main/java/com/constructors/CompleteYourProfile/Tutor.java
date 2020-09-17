package com.constructors.CompleteYourProfile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.constructors.Model.TutorModel;
import com.constructors.Profile.TutorUpdateProfile;
import com.constructors.RegisterLogin.MainActivity;
import com.constructors.RegisterLogin.Signup;
import com.constructors.RegisterLogin.choose;
import com.constructors.findtutor.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class Tutor extends AppCompatActivity {

    DatabaseReference databaseWaitingTutors, databaseTutors ;
    private FirebaseAuth auth;
    private FirebaseUser currentuser;
    private String currentuserid;

    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;


    private EditText enterContact , enterAddress, enterQualification, enterExperience, enterAvailabilityTime, enterEndTime;

    private Spinner spinnerExperience, spinnerQualification, spinnerCity;

    private Button saveInfo;

    CheckBox primaryCB, middleCB, matriculationCB, matricArtsCB, intermediateCB, fscCB, icomCB, bachelorsCB, bachelorsZoloCB, bachelorsPsyCB, bachelorsEngCB, bachelorsMathCB, icom;
    RadioGroup matricRG, interRG, fscRG, icomRG, bachelorsRG, bachelorsZoloRG, bachelorsPsyRG, bachelorsEngRG, bachelorsMathRG;

    LinearLayout matricLL, interLL, fscLL, icomLL, bachelorsLL, bachelorsZoloLL, bachelorsPsyLL, bachelorsEngLL, bachelorsMathLL;

    public static String pushIdAddress, pushIdSubjects, pushIdTutorInfo, pushidProfileStatus;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enterContact = (EditText) findViewById(R.id.contactNo);
        enterAddress = (EditText) findViewById(R.id.address);
        enterAvailabilityTime = (EditText) findViewById(R.id.time);
        enterEndTime = (EditText) findViewById(R.id.endtime);


//        enterQualification = (EditText) findViewById(R.id.qualification);
//        enterExperience = (EditText) findViewById(R.id.experience);

        spinnerExperience = (Spinner) findViewById(R.id.spinner);
        spinnerQualification = (Spinner) findViewById(R.id.spinnerQualification);
        spinnerCity = (Spinner) findViewById(R.id.spinnerCities);


        saveInfo = (Button) findViewById(R.id.btnSave);



        primaryCB = findViewById(R.id.checkbox_primary);
        middleCB = findViewById(R.id.checkbox_middle);
        matriculationCB = findViewById(R.id.checkbox_matriculationScience);
        intermediateCB = findViewById(R.id.checkbox_FscPreEngineering);
        fscCB = findViewById(R.id.checkbox_Fsc);
        icomCB = findViewById(R.id.checkbox_Icom);
        bachelorsCB = findViewById(R.id.checkbox_Bachelors);
        bachelorsZoloCB = findViewById(R.id.checkbox_BachelorsZolo);
        bachelorsPsyCB = findViewById(R.id.checkbox_BachelorsPsy);
        bachelorsEngCB = findViewById(R.id.checkbox_BachelorsEng);
        bachelorsMathCB = findViewById(R.id.checkbox_BachelorsMath);



        matricRG= findViewById(R.id.radiogroup_matric);
        interRG= findViewById(R.id.radiogroup_inter);
        fscRG= findViewById(R.id.radiogroup_Fsc);
        icomRG= findViewById(R.id.radiogroup_Icom);
        bachelorsRG= findViewById(R.id.radiogroup_bachelors);
        bachelorsZoloRG= findViewById(R.id.radiogroup_bachelorsZolo);
        bachelorsPsyRG= findViewById(R.id.radiogroup_bachelorsPsy);
        bachelorsEngRG= findViewById(R.id.radiogroup_bachelorsEng);
        bachelorsMathRG= findViewById(R.id.radiogroup_bachelorsMath);

          matricLL = findViewById(R.id.matricLL);
          interLL = findViewById(R.id.interLL);
        fscLL = findViewById(R.id.FscLL);
        icomLL = findViewById(R.id.IcomLL);
          bachelorsLL = findViewById(R.id.bachelorsLL);
        bachelorsZoloLL = findViewById(R.id.bachelorsZoloLL);
        bachelorsPsyLL = findViewById(R.id.bachelorsPsyLL);
        bachelorsEngLL = findViewById(R.id.bachelorsEngLL);
        bachelorsMathLL = findViewById(R.id.bachelorsMathLL);


        enterAvailabilityTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Tutor.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        enterAvailabilityTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        enterEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(Tutor.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        enterEndTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });



        matricLL.setVisibility(View.GONE);
          interLL.setVisibility(View.GONE);
        fscLL.setVisibility(View.GONE);
        icomLL.setVisibility(View.GONE);
          bachelorsLL.setVisibility(View.GONE);
        bachelorsZoloLL.setVisibility(View.GONE);
        bachelorsPsyLL.setVisibility(View.GONE);
        bachelorsEngLL.setVisibility(View.GONE);
        bachelorsMathLL.setVisibility(View.GONE);





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
//                    interLL1.setVisibility(View.VISIBLE);

                } else {
                    interLL.setVisibility(View.GONE);
//                    interLL1.setVisibility(View.GONE);
                }
            }
        });

        fscCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    fscLL.setVisibility(View.VISIBLE);
//                    interLL1.setVisibility(View.VISIBLE);

                } else {
                    fscLL.setVisibility(View.GONE);
//                    interLL1.setVisibility(View.GONE);
                }
            }
        });

        icomCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    icomLL.setVisibility(View.VISIBLE);
//                    interLL1.setVisibility(View.VISIBLE);

                } else {
                    icomLL.setVisibility(View.GONE);
//                    interLL1.setVisibility(View.GONE);
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

        bachelorsZoloCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    bachelorsZoloLL.setVisibility(View.VISIBLE);
                } else {
                    bachelorsZoloLL.setVisibility(View.GONE);
                }
            }
        });

        bachelorsPsyCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    bachelorsPsyLL.setVisibility(View.VISIBLE);
                } else {
                    bachelorsPsyLL.setVisibility(View.GONE);
                }
            }
        });

        bachelorsEngCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    bachelorsEngLL.setVisibility(View.VISIBLE);
                } else {
                    bachelorsEngLL.setVisibility(View.GONE);
                }
            }
        });

        bachelorsMathCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    bachelorsMathLL.setVisibility(View.VISIBLE);
                } else {
                    bachelorsMathLL.setVisibility(View.GONE);
                }
            }
        });







        String getAddress = enterAddress.getText().toString();



        auth=FirebaseAuth.getInstance();
        currentuser=auth.getCurrentUser();
        currentuserid=currentuser.getUid();

        databaseWaitingTutors= FirebaseDatabase.getInstance().getReference().child("Waiting Tutors").child(currentuserid);
        databaseTutors= FirebaseDatabase.getInstance().getReference().child("Tutors").child(currentuserid);


        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getContactNo = enterContact.getText().toString();
                String getAddress = enterAddress.getText().toString();
                String getTime = enterAvailabilityTime.getText().toString();

                 String getExperience = spinnerExperience.getSelectedItem().toString();
                 String getQualification = spinnerQualification.getSelectedItem().toString();
                 String getCityName = spinnerCity.getSelectedItem().toString();


                try{


                if (!getContactNo.isEmpty() && !getAddress.isEmpty() ) {


                }

                if (getContactNo.isEmpty()) {
                    enterContact.setError("Contact Number is required");
                    enterContact.requestFocus();



                } else if (getAddress.isEmpty()) {
                    enterAddress.setError("Address is required");
                    enterAddress.requestFocus();
                }

                else if (getTime.isEmpty()) {
                    enterAddress.setError("Set Availability Time");
                    enterAddress.requestFocus();
                }
//
                else if(getQualification.equals("Select Qualification")){
                    Toast.makeText(Tutor.this, "Select qualification", Toast.LENGTH_SHORT).show();
                }

                else if(getExperience.equals("Select Experience")){
                    Toast.makeText(Tutor.this, "Select experience", Toast.LENGTH_SHORT).show();
                }

                else if(getCityName.equals("Select City")){
                    Toast.makeText(Tutor.this, "Select City", Toast.LENGTH_SHORT).show();
                }



                else if (!primaryCB.isChecked() && !middleCB.isChecked() && !matriculationCB.isChecked() &&  !intermediateCB.isChecked() &&  !bachelorsCB.isChecked() &&  !bachelorsZoloCB.isChecked() &&  !bachelorsPsyCB.isChecked() &&  !bachelorsEngCB.isChecked() &&  !bachelorsMathCB.isChecked() ){

                    Toast.makeText(Tutor.this, "Select any Class", Toast.LENGTH_SHORT).show();

                }

//                else if (matriculationCB.isChecked() && matricRG.getCheckedRadioButtonId() == -1){
//                    Toast.makeText(Tutor.this, "You have to select one category for Matriculation", Toast.LENGTH_SHORT).show();
//
//                }
//                else if (intermediateCB.isChecked() && interRG.getCheckedRadioButtonId() == -1 ){
//                    Toast.makeText(Tutor.this, "You have to select one category for Intermediate", Toast.LENGTH_SHORT).show();
//
//                }
//
//                else if (fscCB.isChecked() && fscRG.getCheckedRadioButtonId() == -1 ){
//                    Toast.makeText(Tutor.this, "You have to select one category for FSC", Toast.LENGTH_SHORT).show();
//
//                }
//
//                else if (icomCB.isChecked() && icomRG.getCheckedRadioButtonId() == -1 ){
//                    Toast.makeText(Tutor.this, "You have to select one category for ICOM", Toast.LENGTH_SHORT).show();
//
//                }


//                else if (bachelorsCB.isChecked() && bachelorsRG.getCheckedRadioButtonId() == -1 ){
//                    Toast.makeText(Tutor.this, "You have to select one category for BSCS", Toast.LENGTH_SHORT).show();
//
//                }
//
//                else if (bachelorsZoloCB.isChecked() && bachelorsZoloRG.getCheckedRadioButtonId() == -1 ){
//                    Toast.makeText(Tutor.this, "You have to select one category for BS Zology", Toast.LENGTH_SHORT).show();
//
//                }
//
//                else if (bachelorsPsyCB.isChecked() && bachelorsPsyRG.getCheckedRadioButtonId() == -1 ){
//                    Toast.makeText(Tutor.this, "You have to select one category for BS Psycology", Toast.LENGTH_SHORT).show();
//
//                }
//
//                else if (bachelorsEngCB.isChecked() && bachelorsEngRG.getCheckedRadioButtonId() == -1 ){
//                    Toast.makeText(Tutor.this, "You have to select one category for BS English", Toast.LENGTH_SHORT).show();
//
//                }
//
//                else if (bachelorsMathCB.isChecked() && bachelorsMathRG.getCheckedRadioButtonId() == -1 ){
//                    Toast.makeText(Tutor.this, "You have to select one category for BS Mathematics", Toast.LENGTH_SHORT).show();
//
//                }


                else {



                    if (primaryCB.isChecked()){
                        String getData = primaryCB.getText().toString();

                        databaseWaitingTutors.child("Classes").child("primary").setValue(getData);
                        databaseTutors.child("Classes").child("primary").setValue(getData);
                    }
                    if (middleCB.isChecked()){
                        String getData = middleCB.getText().toString();
                        databaseWaitingTutors.child("Classes").child("middle").setValue(getData);
                        databaseTutors.child("Classes").child("middle").setValue(getData);
                    }
                    if (matriculationCB.isChecked()){

//                        RadioButton selectedRadioButton = findViewById(matricRG.getCheckedRadioButtonId());
//                        String getData = selectedRadioButton.getText().toString().trim();
                        databaseWaitingTutors.child("Classes").child("matric").setValue("Physics IX-X");
                        databaseTutors.child("Classes").child("matric").setValue("Physics IX-X");
                    }
                    if (intermediateCB.isChecked()){
//                        if (!(interRG.getCheckedRadioButtonId() == -1) && inter1RG.getCheckedRadioButtonId() == -1) {
//                            RadioButton selectedRadioButton = findViewById(interRG.getCheckedRadioButtonId());
//                            String getData = selectedRadioButton.getText().toString().trim();
                        databaseWaitingTutors.child("Classes").child("inter").setValue("Computer, Maths XI-XII");
                        databaseTutors.child("Classes").child("inter").setValue("Computer, Maths XI-XII");

                    }

                    if (fscCB.isChecked()){
//                        if (!(interRG.getCheckedRadioButtonId() == -1) && inter1RG.getCheckedRadioButtonId() == -1) {
//                        RadioButton selectedRadioButton = findViewById(fscRG.getCheckedRadioButtonId());
//                        String getData = selectedRadioButton.getText().toString().trim();
                        databaseWaitingTutors.child("Classes").child("FSC").setValue("Biology XI-XII");
                        databaseTutors.child("Classes").child("FSC").setValue("Biology XI-XII");

                    }

                    if (icomCB.isChecked()){
//                        if (!(interRG.getCheckedRadioButtonId() == -1) && inter1RG.getCheckedRadioButtonId() == -1) {
//                        RadioButton selectedRadioButton = findViewById(icomRG.getCheckedRadioButtonId());
//                        String getData = selectedRadioButton.getText().toString().trim();
                        databaseWaitingTutors.child("Classes").child("ICOM").setValue("Accounting XI-XII");
                        databaseTutors.child("Classes").child("ICOM").setValue("Accounting XI-XII");

                    }

                    if (bachelorsCB.isChecked()){

//                        RadioButton selectedRadioButton = findViewById(bachelorsRG.getCheckedRadioButtonId());
//                        String getData = selectedRadioButton.getText().toString().trim();
                        databaseWaitingTutors.child("Classes").child("bachelors").setValue("Object Oriented Programming");
                        databaseTutors.child("Classes").child("bachelors").setValue("Object Oriented Programming");
                    }

                    if (bachelorsZoloCB.isChecked()){

//                        RadioButton selectedRadioButton = findViewById(bachelorsZoloRG.getCheckedRadioButtonId());
//                        String getData = selectedRadioButton.getText().toString().trim();
                        databaseWaitingTutors.child("Classes").child("bachelorsZolo").setValue("Organic Chemistry for Bachelors");
                        databaseTutors.child("Classes").child("bachelorsZolo").setValue("Organic Chemistry for Bachelors");
                    }

                    if (bachelorsPsyCB.isChecked()){

//                        RadioButton selectedRadioButton = findViewById(bachelorsPsyRG.getCheckedRadioButtonId());
//                        String getData = selectedRadioButton.getText().toString().trim();
                        databaseWaitingTutors.child("Classes").child("bachelorsPsy").setValue("Cultural Psycology for Bachelors");
                        databaseTutors.child("Classes").child("bachelorsPsy").setValue("Cultural Psycology for Bachelors");
                    }

                    if (bachelorsEngCB.isChecked()){

//                        RadioButton selectedRadioButton = findViewById(bachelorsEngRG.getCheckedRadioButtonId());
//                        String getData = selectedRadioButton.getText().toString().trim();
                        databaseWaitingTutors.child("Classes").child("bachelorsEng").setValue("English Literature for Bachelors");
                        databaseTutors.child("Classes").child("bachelorsEng").setValue("English Literature for Bachelors");
                    }

                    if (bachelorsMathCB.isChecked()){

//                        RadioButton selectedRadioButton = findViewById(bachelorsMathRG.getCheckedRadioButtonId());
//                        String getData = selectedRadioButton.getText().toString().trim();
                        databaseWaitingTutors.child("Classes").child("bachelorsMath").setValue("Calculus for Bachelors");
                        databaseTutors.child("Classes").child("bachelorsMath").setValue("Calculus for Bachelors");
                    }


                    TutorModel tutorModel = new TutorModel(getContactNo, getAddress, getQualification, getExperience, getCityName);


                    databaseWaitingTutors.child("info").setValue(tutorModel);
                    databaseTutors.child("info").setValue(tutorModel);
                    databaseTutors.child("Approved").setValue("No");



                    DatabaseReference databaseReferenceRegisteredTutors = FirebaseDatabase.getInstance().getReference().child("Tutors").child(currentuserid);
                    databaseReferenceRegisteredTutors.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String getUserName = dataSnapshot.child("name").getValue().toString();
                            databaseWaitingTutors.child("name").setValue(getUserName);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    String concatAddress = getAddress + getCityName;


                    LatLng p = getLocationFromAddress(getApplicationContext(), concatAddress);



                    startActivity(new Intent(Tutor.this, PendingTutor.class));
                }
            }
                catch(Exception e){
                    Log.e("Register Exception", e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tutor.this);
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
        List<Address> address = null;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
                return null;
            }


                if (address.size() > 0) {

                    Address location = address.get(0);

                p1 = new LatLng(location.getLatitude(), location.getLongitude());



                    databaseWaitingTutors.child("location").child("Lat").setValue(location.getLatitude());
                    databaseWaitingTutors.child("location").child("Lng").setValue(location.getLongitude());

                    databaseTutors.child("location").child("Lat").setValue(location.getLatitude());
                    databaseTutors.child("location").child("Lng").setValue(location.getLongitude());




                }

                else {
                    Toast.makeText(context, "Oops!!", Toast.LENGTH_SHORT).show();
                }



        } catch (IOException ex) {

//            ex.printStackTrace();

            AlertDialog.Builder builder = new AlertDialog.Builder(Tutor.this);
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
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
                startActivity(new Intent(Tutor.this, MainActivity.class));
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

