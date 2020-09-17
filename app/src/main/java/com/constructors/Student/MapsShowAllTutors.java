package com.constructors.Student;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.constructors.findtutor.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsShowAllTutors extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    MarkerOptions markerOptions = new MarkerOptions();
    public static String passingMarkerTitle;
    private DatabaseReference databaseReferenceTutors;
    private Spinner spinnerClass, spinnerQualification, spinnerCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_show_all_tutors);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        spinnerCity = (Spinner) findViewById(R.id.spinnerCities);
        spinnerCity.setOnItemSelectedListener(this);

        spinnerClass = (Spinner) findViewById(R.id.spinnerClasses);
        spinnerClass.setOnItemSelectedListener(this);
    }


    public void onClick(View v){

        Button button = (Button) findViewById(R.id.searchLocation);

        switch (v.getId())
        {

            case R.id.searchLocation:


                EditText addressField= (EditText)findViewById(R.id.input_search);
                String address= addressField.getText().toString();

                List<Address> addressList = null;

                // final MarkerOptions userMarkerOptions = new MarkerOptions();

                if (!TextUtils.isEmpty(address))

                {
                    Geocoder geocoder=new Geocoder(this);

                    try
                    {
                        addressList = geocoder.getFromLocationName(address, 6);

                        if (addressList != null)
                        {

                            for (int i=0 ; i < addressList.size(); i++)
                            {
                                Address userAddress = addressList.get(i);
                                LatLng latLng = new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                                markerOptions.position(latLng);
                                //  markerOptions.title(address).title(userAddress.getLatitude()+ "," + userAddress.getLongitude());

                                //  markerOptions.title(address);

                                mMap.addMarker(markerOptions);
                                mMap.setOnMarkerClickListener(this);


                                //Moving the camera
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                                //Animating the camera
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));








                            }


                        }

                        else
                        {
                            Toast.makeText(this,"Loaction not found...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }


                }

                else
                {
                    Toast.makeText(this,"Please wite any Loaction name", Toast.LENGTH_SHORT).show();
                }

                break;

        }



    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        databaseReferenceTutors= FirebaseDatabase.getInstance().getReference().child("Tutors");

        databaseReferenceTutors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    final String getKey = dataSnapshot1.getKey().toString();

                    if (dataSnapshot.child(getKey).hasChild("info")){

                    if (dataSnapshot.child(getKey).child("Approved").getValue().equals("Yes")) {


                        databaseReferenceTutors.child(getKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.hasChild("location")){


                                String lat = dataSnapshot.child("location").child("Lat").getValue().toString();
                                String lng = dataSnapshot.child("location").child("Lng").getValue().toString();

                                double latitude = Double.valueOf(lat);
                                double longitude = Double.valueOf(lng);

                                LatLng location = new LatLng(latitude, longitude);

                                mMap.addMarker(new MarkerOptions().position(location).title(getKey)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.teacher));

                                mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                            }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                  }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }) ;




    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {


        final String spinnerItem = spinnerCity.getSelectedItem().toString();
        final String spinnerClassItem = spinnerClass.getSelectedItem().toString();

        if (!spinnerItem.equals("Select City") && !spinnerClassItem.equals("Select Class")){


            mMap.clear();
            databaseReferenceTutors = FirebaseDatabase.getInstance().getReference().child("Tutors");

        databaseReferenceTutors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    final String getKey = dataSnapshot1.getKey().toString();

                    if (dataSnapshot.child(getKey).hasChild("info")) {

                        String getCity = dataSnapshot.child(getKey).child("info").child("tutCity").getValue().toString();

                        if (dataSnapshot.child(getKey).child("Approved").getValue().equals("Yes") && getCity.equals(spinnerItem) && dataSnapshot.child(getKey).child("Classes").hasChild(spinnerClassItem)) {


                            databaseReferenceTutors.child(getKey).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.hasChild("location")) {


                                        String lat = dataSnapshot.child("location").child("Lat").getValue().toString();
                                        String lng = dataSnapshot.child("location").child("Lng").getValue().toString();

                                        double latitude = Double.valueOf(lat);
                                        double longitude = Double.valueOf(lng);

                                        LatLng location = new LatLng(latitude, longitude);

                                        mMap.addMarker(new MarkerOptions().position(location).title(getKey)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.teacher));

                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }






    @Override
    public boolean onMarkerClick(Marker marker) {


        String markerTitle = marker.getTitle();

        passingMarkerTitle = markerTitle;

        Intent intent = new Intent(MapsShowAllTutors.this , StudentMarkerClick.class);
        startActivity(intent);


        return false;
    }





}
