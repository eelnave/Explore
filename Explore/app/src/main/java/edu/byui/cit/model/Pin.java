package edu.byui.cit.model;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Pin {

    @ColumnInfo
    @PrimaryKey(autoGenerate=true)
    long pinId;

    @ColumnInfo
    String iconName;

    @ColumnInfo
    Double latitude;

    @ColumnInfo
    Double longitude;

    @ColumnInfo
    Date timestamp;

    @ColumnInfo
    String notes;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
