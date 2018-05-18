package edu.byui.cit.kindness;

import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;


public class Report {
	private Object timestamp = ServerValue.TIMESTAMP;
	private Category category;
	private double latitude;
	private double longitude;

	public Report() {
		this.category = Category.None;
		this.latitude = 0;
		this.longitude = 0;
	}

	public Report(Category category, Location loc) {
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
	public long timestamp() {
		return timestamp instanceof Long ? ((Long)timestamp) : Long.MIN_VALUE;
	}

	@Exclude
	public Category category() {
		return category;
	}

	@Exclude
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(80);
		sb.append("Report: ").append(timestamp())
				.append(" cat ").append(category.name())
				.append(" lat ").append(latitude)
				.append(" long ").append(longitude);
		return sb.toString();
	}

	@Exclude
	public void submit() {
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference reports = database.getReference(KindnessActivity.REPORTS_KEY);
//		reports.push().setValue(this);
		String key = reports.push().getKey();
		HashMap<String, Object> updates = new HashMap<>();
		updates.put("/" + KindnessActivity.REPORTS_KEY + "/" + key, this);
		updates.put("/" + KindnessActivity.CATEGORIES_KEY + "/" + getCategory() + "/" + KindnessActivity.REPORTS_KEY + "/" + key, true);
		database.getReference().updateChildren(updates);
		Log.i(KindnessActivity.TAG, toString());
	}
}
