package com.sjsu.yelpSearch;

import android.os.Bundle;

public class F4Activity extends DashboardActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_f4);
		setTitleFromActivityLabel (R.id.title_text);
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