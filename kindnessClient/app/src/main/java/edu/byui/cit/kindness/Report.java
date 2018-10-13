package edu.byui.cit.kindness;

import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;


final class Report {
	private Object timestamp;
	private Category category;
	private double latitude;
	private double longitude;


	@SuppressWarnings("unused")  // Used by firebase
	public Report() {
		this.timestamp = ServerValue.TIMESTAMP;
		this.category = Category.None;
		this.latitude = 0;
		this.longitude = 0;
	}

	// Used for searching a list of reports.
	Report(long timestamp) {
		this.timestamp = timestamp;
		this.category = Category.None;
		this.latitude = 0;
		this.longitude = 0;
	}

	// Used for creating a report that will be sent to firebase.
	Report(Category category, Location loc) {
		this.timestamp = ServerValue.TIMESTAMP;
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
	private long timestamp() {
		return timestamp instanceof Long ? ((Long)timestamp) : Long.MIN_VALUE;
	}

	@Exclude
	Category category() {
		return category;
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

	@Exclude
	private static final DateFormat fmtr =
			DateFormat.getDateInstance(DateFormat.MEDIUM);

	@Exclude
	@Override
	public String toString() {
		Date date = new Date(timestamp());
		return "Report: " + fmtr.format(date) +
				" " + category.name() +
				" " + latitude +
				" " + longitude;
	}


	@Exclude
	static final Comparator<Report> compareTimestamps =
			new Comparator<Report>() {
				@Override
				public int compare(Report r1, Report r2) {
					return Long.signum(r1.timestamp() - r2.timestamp());
				}
			};
}
