package edu.byui.cit.kindness;

import java.text.DateFormat;
import java.util.Date;


enum Duration {
	AllTime(Long.MAX_VALUE),
	Year(1000L * 60 * 60 * 24 * 365),
	Month(1000L * 60 * 60 * 24 * 30),
	Week(1000L * 60 * 60 * 24 * 7),
	Day(1000L * 60 * 60 * 24),
	Hour(1000L * 60 * 60);

	private final long durat;

	Duration(long durat) {
		this.durat = durat;
	}

	long beginning(long now) {
		return now - durat;
	}

	boolean isWithin(long event, long now) {
		return now - durat <= event && event <= now;
	}

	private static final DateFormat fmtr =
			DateFormat.getDateInstance(DateFormat.MEDIUM);

	@Override
	public String toString() {
		Date date = new Date(System.currentTimeMillis() - durat);
		return name() + " " + fmtr.format(date);
	}

	static Duration get(int ordinal) {
		return values()[ordinal];
	}
}
