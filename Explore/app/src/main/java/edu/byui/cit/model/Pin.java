package edu.byui.cit.model;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;
//begin database
@Entity
public class Pin {

    @ColumnInfo
    @PrimaryKey(autoGenerate=true)
    private long pinId;

    @ColumnInfo(name = "icon_name")
    private String iconName;

    @ColumnInfo(name = "latitude")
    private Double latitude;

    @ColumnInfo(name = "longitude")
    private Double longitude;

	@ColumnInfo(name = "timestamp")
    private Date date;

    @ColumnInfo(name = "notes")
    private String notes;

	public Pin(long pinId, String iconName, Double latitude,
			Double longitude, Date date, String notes) {
		this.pinId = pinId;
		this.iconName = iconName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
		this.notes = notes;
	}

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
