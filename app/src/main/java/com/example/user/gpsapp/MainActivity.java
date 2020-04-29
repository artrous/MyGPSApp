package com.example.user.gpsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button btnShowLocation;
    GPSTracker gps; // Creating the gps class of the GPStracking type where we made it earlier
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        // Create a Listener for our button
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                gps = new GPSTracker(MainActivity.this); // Initialization of our class
                if(gps.canGetLocation()){ // Check if we have our receivers active // Request the features we are interested in
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    double altitude = gps.getAltitude();
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude + "\nAlt: " + altitude, Toast.LENGTH_LONG).show(); // Display of relevant data to the user
                }else{
                    gps.showSettingsAlert(); // If the receivers are not active we inform the user
                } } }); }}
