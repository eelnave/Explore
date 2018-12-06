package edu.byui.cit.model;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;


// this is the converters class.  It converts timestamps to dates and vice
// versa.
// we declare the class here
public class Converters {
	//we annotate that this method is a converter here
	@TypeConverter
	//we create the method here and pass in a Long value, which is the timestamp
	public static Date fromTimestamp(Long value) {
		//this passes a date from the timestamp as long as it is not null
		return value == null ? null : new Date(value);
	}

	//this does the same thing as above, just reverse.
	@TypeConverter
	public static Long dateToTimestamp(Date date) {
		return date == null ? null : date.getTime();
	}
}
