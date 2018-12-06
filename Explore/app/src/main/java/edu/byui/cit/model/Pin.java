package edu.byui.cit.model;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;


// This is the Pin class.
// We declare that this is a database object with the @Entity annotation
// This will also create a table in the database called 'Pin'
@Entity
// We create the class
public class Pin {
	// In here, we create the 'Columns' of the table 'Pin'
	//We use @ColumnInfo to declare each variable as a column
	@ColumnInfo
	//because this first variable is the ID, we want it the primary key and
	// also auto generated
	//we use the @PrimaryKey annotation to make it the primary key, and we use
	// autoGenerate=true to automatically create IDs for each row
	@PrimaryKey(autoGenerate=true)
	//the ID could be an int or a long, so we chose long because we could.
	private long pinId;

	//in the rest of these variables we use the @ColumnInfo like before, but
	// we also include (name = "blah")
	//because we wanted to explicitly say the column names because we were
	// also taking CIT 325 and we
	//were brainwashed into having proper SQL names in the columns.
	//Latitudes and Longitudes passed from the GoogleMap object are doubles,
	// so we also made them doubles in our database,
	//the rest is self-explanatory.

	@ColumnInfo(name = "icon_name")
	private String iconName;

	@ColumnInfo(name = "latitude")
	private double latitude;

	@ColumnInfo(name = "longitude")
	private double longitude;

	@ColumnInfo(name = "timestamp")
	private Date date;

	@ColumnInfo(name = "notes")
	private String notes;

	//we have a constructor in here, because why not?
	public Pin(String iconName, double latitude,
			double longitude, Date date, String notes) {
		this.pinId = pinId;
		this.iconName = iconName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
		this.notes = notes;
	}

	// we have all the getters and setters here because this object wouldn't actually work without them
	public long getPinId() {
		return pinId;
	}

	public void setPinId(long pinId) {
		this.pinId = pinId;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getDate() { return date; }

	public void setDate(Date date) { this.date = date; }
}
