package edu.byui.cit.kindness;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Report {

	private double latitude;
	private double longitude;
	private Category category;

	public Report() {
		this.latitude = 0;
		this.longitude = 0;
	}

	public Report(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void addReport() {
		//test FB connection
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference myRef = database.getReference("report");
		myRef.push().setValue(this);
	}


}
