package com.sjsu.yelpSearch;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sjsu.yelpSearch.ImageLoader;
import com.sjsu.yelpSearch.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class BusinessActivity extends Activity {

	public static final String EXTRA_BUSINESS = "business";
	//public static final String EXTRA_BUSINESSES = "businesses";

	ImageView img;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business);
		Business business = getIntent().getParcelableExtra(EXTRA_BUSINESS);

		setTitle(business.getName());

		//Fetch Name
		String name = business.getName();

		//Fetch the Address
		String address = business.mLocation.mDisplayAddress.toString();

		//Fetch Distance
		double distance = business.getDistance() * 0.000621371;

		//Fetch Review of place
		String review = business.getSnippetText();
		String phone;
		try{
			phone = business.getPhone();        	
		}
		catch(Exception ex)
		{
			phone = "Not Present";
		}

		int loader = R.drawable.airport_default;
		img = (ImageView)findViewById(R.id.imagenew);
		String imageurl;
		try{
			imageurl = business.getImageUrl();        	
		}
		catch(Exception ex)
		{
			imageurl = "Not Present";
		}
		ImageLoader imgLoader = new ImageLoader(getApplicationContext());

		imgLoader.DisplayImage(imageurl, loader, img);

		((TextView)findViewById(R.id.name1)).setText(name);
		((TextView)findViewById(R.id.address1)).setText(address);
		((TextView)findViewById(R.id.phone1)).setText(phone);
		((TextView)findViewById(R.id.review1)).setText(review);
	}

	protected Bitmap getImage(String urldisplay ) {
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	public static Drawable dispImage(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			System.out.println("Inside stream :"+is);
			Drawable d = Drawable.createFromStream(is, "vaib");
			System.out.println("Inside drawable : "+d);
			return d;
		} 
		catch (Exception e) {
			return null;
		}
	}
}
