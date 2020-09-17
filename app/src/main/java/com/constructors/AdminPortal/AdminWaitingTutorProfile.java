package com.constructors.AdminPortal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.constructors.AdminActivity;
import com.constructors.CompleteYourProfile.Tutor;
import com.constructors.Student.AdapterStudentSearch;
import com.constructors.Student.ShowTutorFullProfileToStudent;
import com.constructors.findtutor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class AdminWaitingTutorProfile extends AppCompatActivity {

    private TextView userName,contactNo, address, qualification, experience;
    private ListView listView;
    private ImageView callImg, whatsappmsg;
    private Button approveBtn, disapproveBtn;
    private DatabaseReference databaseReferenceTutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_waiting_tutor_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        databaseReferenceTutors= FirebaseDatabase.getInstance().getReference().child("Waiting Tutors");

        contactNo = (TextView) findViewById(R.id.contactTVS);
        address = (TextView) findViewById(R.id.addressTVS);
        qualification = (TextView) findViewById(R.id.qualificationTVS);
        experience = (TextView) findViewById(R.id.experienceTVS);
        userName = (TextView) findViewById(R.id.nameTVS);

        callImg = (ImageView) findViewById(R.id.call);
        whatsappmsg = (ImageView) findViewById(R.id.whatsapp);

        approveBtn = (Button) findViewById(R.id.btnApprove);
        disapproveBtn = (Button) findViewById(R.id.btnDisapprove);


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


        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(AdminWaitingTutorProfile.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you really want to approve this Tutor?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();


                        DatabaseReference databaseReferenceTutorsApproved = FirebaseDatabase.getInstance().getReference().child("Tutors");
                        databaseReferenceTutorsApproved.child(AdapterAdminDisapproved.getUid).child("Approved").setValue("Yes");

                        databaseReferenceTutors.child(AdapterAdminDisapproved.getUid).child("name").setValue("Approved");



                            startActivity(new Intent(AdminWaitingTutorProfile.this, AdminActivity.class));



                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();


            }
        });




        databaseReferenceTutors.child(AdapterAdminDisapproved.getUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String getUserName = dataSnapshot.child("name").getValue().toString();
                    userName.setText(getUserName);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReferenceTutors.child(AdapterAdminDisapproved.getUid).child("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists() ) {


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



        databaseReferenceTutors.child(AdapterAdminDisapproved.getUid).child("Classes").addValueEventListener(new ValueEventListener() {
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

                    if (dataSnapshot.hasChild("bachelors")){
                        propertyAddressList.add(dataSnapshot.child("bachelors").getValue().toString());
                    }


                }

                final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(AdminWaitingTutorProfile.this, R.layout.tutor_profile_list_view, propertyAddressList);

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
