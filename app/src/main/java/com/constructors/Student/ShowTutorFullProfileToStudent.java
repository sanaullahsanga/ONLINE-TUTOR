package com.constructors.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.constructors.Profile.TutorProfile;
import com.constructors.findtutor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowTutorFullProfileToStudent extends AppCompatActivity {

    private TextView userName,contactNo, address, qualification, experience;
    private ListView listView;
    private ImageView callImg, whatsappmsg;
    private DatabaseReference databaseReferenceTutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tutor_full_profile_to_student);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReferenceTutors= FirebaseDatabase.getInstance().getReference().child("Tutors");

        contactNo = (TextView) findViewById(R.id.contactTVS);
        address = (TextView) findViewById(R.id.addressTVS);
        qualification = (TextView) findViewById(R.id.qualificationTVS);
        experience = (TextView) findViewById(R.id.experienceTVS);
        userName = (TextView) findViewById(R.id.nameTVS);

        callImg = (ImageView) findViewById(R.id.call);
        whatsappmsg = (ImageView) findViewById(R.id.whatsapp);

        listView = (ListView) findViewById(R.id.listviewTutorProfileS);

        callImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getNumber = contactNo.getText().toString();

//                String phone = "+34666777888";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", getNumber, null));
                startActivity(intent);

            }
        });

        whatsappmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getNumber = contactNo.getText().toString();
                openWhatsappContact(getNumber);

            }
        });




        databaseReferenceTutors.child(AdapterStudentSearch.getUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() && dataSnapshot.hasChild("name")) {

                    String getUserName = dataSnapshot.child("name").getValue().toString();
                    userName.setText(getUserName);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReferenceTutors.child(AdapterStudentSearch.getUid).child("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    String getContact = dataSnapshot.child("tutPhoneNo").getValue().toString();
                    String getAddress = dataSnapshot.child("tutAddress").getValue().toString();
                    String getQualification = dataSnapshot.child("utQualification").getValue().toString();
                    String getExperience = dataSnapshot.child("tutExperience").getValue().toString();

                    contactNo.setText(getContact);
                    address.setText(getAddress);
                    qualification.setText(getQualification);
                    experience.setText(getExperience);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        databaseReferenceTutors.child(AdapterStudentSearch.getUid).child("Classes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                final List<String> propertyAddressList = new ArrayList<String>();

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild("primary")){
                        propertyAddressList.add(dataSnapshot.child("primary").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("matric")){
                        propertyAddressList.add(dataSnapshot.child("matric").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("middle")){
                        propertyAddressList.add(dataSnapshot.child("middle").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("inter")){
                        propertyAddressList.add(dataSnapshot.child("inter").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("FSC")){
                        propertyAddressList.add(dataSnapshot.child("FSC").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("ICOM")){
                        propertyAddressList.add(dataSnapshot.child("ICOM").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("bachelors")){
                        propertyAddressList.add(dataSnapshot.child("bachelors").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("bachelorsZolo")){
                        propertyAddressList.add(dataSnapshot.child("bachelorsZolo").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("bachelorsPsy")){
                        propertyAddressList.add(dataSnapshot.child("bachelorsPsy").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("bachelorsEng")){
                        propertyAddressList.add(dataSnapshot.child("bachelorsEng").getValue().toString());
                    }

                    if (dataSnapshot.hasChild("bachelorsMath")){
                        propertyAddressList.add(dataSnapshot.child("bachelorsMath").getValue().toString());
                    }


                }

                final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(ShowTutorFullProfileToStudent.this, R.layout.tutor_profile_list_view, propertyAddressList);

                listView.setAdapter(adapter1);



            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void openWhatsApp(String number) {
        try {
            number = number.replace(" ", "").replace("+", "");

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
            getApplicationContext().startActivity(sendIntent);

        } catch(Exception e) {

        }
    }

    public void openWhatsappContact(String number) {
        String a = number.substring(1,11);
        Uri uri = Uri.parse("smsto:" + "+92"+ a);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));
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
