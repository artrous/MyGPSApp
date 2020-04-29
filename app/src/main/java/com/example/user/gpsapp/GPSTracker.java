package com.example.user.gpsapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

public class GPSTracker extends Service implements LocationListener {

 private static final Location TODO = null;
 private final Context myContext;

 boolean isGPSEnabled = false;
 boolean isNetworkEnabled = false;
 boolean canGetLocation = false;

 Location location; // location
 double latitude; // latitude
 double longitude; // longitude
 double altitude; // altitude

 // Creating a variable where we declare the minimum change of our position in measures in which the receiver will be renewed
 private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

 // Creating a variable where we declare every time (in time) the receiver will be renewed
 private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 λεπτό

 // Create a manager type locationManager where we will manage our data
 protected LocationManager locationManager;

 public GPSTracker(Context context) { // Our tracker that we will then call from the main activity
  this.myContext = context;
  getLocation();
 } // Call the getLocation function

 public Location getLocation() {
  try {
   locationManager = (LocationManager) myContext.getSystemService(LOCATION_SERVICE);
   isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
   isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

   if (!isGPSEnabled && !isNetworkEnabled) {
    //do nothing
   } else {
    this.canGetLocation = true; // We will check it in the Main activity
    if (isNetworkEnabled) { // As long as we have internet
     locationManager.requestLocationUpdates( // We renew the position in Location manager
             LocationManager.NETWORK_PROVIDER,
             MIN_TIME_BW_UPDATES,
             MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
     if (locationManager != null) {
      location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
      if (location != null) {
       latitude = location.getLatitude();
       longitude = location.getLongitude();

      }
     }
    }
    // We follow the same procedure if GPS is enabled
    if (isGPSEnabled) {
     if (location == null) {
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
       // TODO: Consider calling
       //    ActivityCompat#requestPermissions
       // here to request the missing permissions, and then overriding
       //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
       //                                          int[] grantResults)
       // to handle the case where the user grants the permission. See the documentation
       // for ActivityCompat#requestPermissions for more details.
       return TODO;
      }
      locationManager.requestLocationUpdates(
              LocationManager.GPS_PROVIDER,
              MIN_TIME_BW_UPDATES,
              MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
      if( locationManager != null)
      {
       location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       if( location != null )
       {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

       }
      }
     }
    }
   }
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }

  return location;
 }

 public void stopUsingGPS(){ // Initialization of GPS
  if(locationManager != null){
   locationManager.removeUpdates(GPSTracker.this);
  }
 }
 public double getLatitude(){
  if(location != null){
   latitude = location.getLatitude();
  }

  // Latitude application
  return latitude;
 }
 public double getLongitude(){
  if(location != null){
   longitude = location.getLongitude();
  }

  // Longitude application
  return longitude;
 }
 public double getAltitude(){
  if(location != null){
   altitude = location.getAltitude();
  }

  // Altitude application
  return altitude;
 }
 public boolean canGetLocation() {
  return this.canGetLocation;
 }
 public void showSettingsAlert(){
  AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

  // Warning message title
  alertDialog.setTitle("GPS is InActive!");

  // Content of a warning message
  alertDialog.setMessage("GPS is not enabled. Press settings and enable it");

  // Create a click listener that redirects us to the device settings
  alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
   public void onClick(DialogInterface dialog,int which) {
    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    myContext.startActivity(intent);
   }
  });


  // Create a click listener that closes the dialog box
  alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
   public void onClick(DialogInterface dialog, int which) {
    dialog.cancel();
   }
  });

  // Display an informative message
  alertDialog.show();
 }


 public void onLocationChanged(Location location) {
 }


 public void onProviderDisabled(String provider) {
 }


 public void onProviderEnabled(String provider) {
 }


 public void onStatusChanged(String provider, int status, Bundle extras) {
 }

 @Override
 public IBinder onBind(Intent arg0) {
  return null;
 }

}