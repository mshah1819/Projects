package com.sjsu.yelpSearch;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

public class F10Activity extends DashboardActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView (R.layout.activity_f10);
		setTitleFromActivityLabel (R.id.title_text);
	}

	@Override
	public Dialog onCreateDialog(int id) {
		if (id == DIALOG_PROGRESS) {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setMessage("Loading...");
			return dialog;
		} else {
			return null;
		}
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
