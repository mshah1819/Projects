package com.sjsu.yelpSearch;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class F2Activity extends DashboardActivity 
{
	private LocationManager locationManager;
	String longitude, latitude;
	private Location loc;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_f2);
		setTitleFromActivityLabel (R.id.title_text);
		getLocation();
	}

	public void getLocation() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,	0, this);
		System.out.println("Inside Search: locationmanager" + locationManager);
		loc = locationManager.getLastKnownLocation("gps");
		String message = String.format(
				"Current Location inside activity \n Longitude: %1$s \n Latitude: %2$s",
				loc.getLongitude(), loc.getLatitude());
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		longitude = String.valueOf(loc.getLongitude());
		System.out.println("Inside f2 activity longitude is " + longitude);
		latitude = String.valueOf(loc.getLatitude());
		System.out.println("Inside f2 activity latitude is " + latitude);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

}
