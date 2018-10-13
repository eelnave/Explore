package edu.byui.cit.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.widget.LinearLayout;

import edu.byui.cit.maintenance.R;


@Entity(
		indices = {@Index(value = {"name"}, unique = true)}
)
public class Vehicle {
	@PrimaryKey(autoGenerate = true)
	private int vehicleID;
	private String name;
	private String vin;
	private int year;
	private String make;
	private String model;
	private String color;

	@ColumnInfo(typeAffinity = ColumnInfo.BLOB)
	private byte[] image;


	public int getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}

	public String getName() {return name;}

	public void setName( String name) {this.name = name;}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setButton() {
		LinearLayout layout = null;;
		layout.findViewById(R.id.ChooseVehicle);

	}

	@Override
	public boolean equals(Object obj2) {
		boolean equ = false;
		if (obj2 instanceof Vehicle) {
			Vehicle other = (Vehicle)obj2;
			equ = vehicleID == other.vehicleID &&
					compareObjects(vin, other.vin) &&
					year == other.year &&
					compareObjects(make, other.make) &&
					compareObjects(model, other.model) &&
					compareObjects(color, other.color);
		}
		return equ;
	}

	private static boolean compareObjects(Object obj1, Object obj2) {
		return obj1 == null ? obj2 == null : obj1.equals(obj2);
	}

	@Override
	public String toString() {
		return "Vehicle: " +
				vehicleID + ", " +
				vin + ", " +
				year + ", " +
				make + ", " +
				model + ", " +
				color;
	}
}
