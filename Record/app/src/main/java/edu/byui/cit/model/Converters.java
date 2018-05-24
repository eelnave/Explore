package edu.byui.cit.model;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;


public class Converters {
	@TypeConverter
	public static Goal.Type typeFromInt(Integer value) {
		return value == null ? null : Goal.Type.values()[value];
	}

	@TypeConverter
	public static Integer intFromType(Goal.Type type) {
		return type == null ? null : type.ordinal();
	}

	@TypeConverter
	public static Goal.Frequency freqFromInt(Integer value) {
		return value == null ? null : Goal.Frequency.values()[value];
	}

	@TypeConverter
	public static Integer intFromFreq(Goal.Frequency freq) {
		return freq == null ? null : freq.ordinal();
	}

	@TypeConverter
	public static Date fromTimestamp(Long value) {
		return value == null ? null : new Date(value);
	}

	@TypeConverter
	public static Long fromDate(Date date) {
		return date == null ? null : date.getTime();
	}
}
