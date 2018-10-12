package edu.byui.cit.kindness;

import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;


public final class Report {


	private Object timestamp = ServerValue.TIMESTAMP;
	private Category category;
	private double latitude;
	private double longitude;


	@SuppressWarnings("unused")  // Used by firebase
	public Report() {
		this.category = Category.None;
		this.latitude = 0;
		this.longitude = 0;
	}

	Report(Category category, Location loc) {
		this.category = category;
		this.latitude = loc.getLatitude();
		this.longitude = loc.getLongitude();
	}


	public void setCategory(int cat) {
		this.category = Category.get(cat);
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@SuppressWarnings("unused")  // Used by firebase
	public Object getTimestamp() {
		return timestamp;
	}

	public int getCategory() {
		return this.category.ordinal();
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}


	@Exclude
	long timestamp() {
		return timestamp instanceof Long ? ((Long)timestamp) : Long.MIN_VALUE;
	}

	@Exclude
	Category category() {
		return category;
	}

	@Exclude
	@Override
	public String toString() {
		return "Report: " + timestamp() +
				" cat " + category.name() +
				" lat " + latitude +
				" long " + longitude;
	}

	@Exclude
	void submit() {
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference reports = database.getReference(
				MainActivity.REPORTS_KEY);
//		reports.push().setValue(this);
		String key = reports.push().getKey();
		HashMap<String, Object> updates = new HashMap<>();
		updates.put("/" + MainActivity.REPORTS_KEY + "/" + key, this);
		updates.put(
				"/" + MainActivity.CATEGORIES_KEY + "/" + getCategory() + "/"
						+ MainActivity.REPORTS_KEY + "/" + key,
				true);
		database.getReference().updateChildren(updates);
		Log.i(MainActivity.TAG, toString());
	}
}
