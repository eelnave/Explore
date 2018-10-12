package edu.byui.cit.kindness;

enum Duration {
	None(0),
	Hour(1000L * 60 * 60),
	Day(1000L * 60 * 60 * 24),
	Week(1000L * 60 * 60 * 24 * 7),
	Month(1000L * 60 * 60 * 24 * 30),
	Year(1000L * 60 * 60 * 24 * 365),
	AllTime(Long.MAX_VALUE);

	static Duration get(int ordinal) {
		return values()[ordinal];
	}

	private long durat;

	Duration(long durat) {
		this.durat = durat;
	}

	boolean isWithin(long event, long now) {
		return now - durat <= event && event <= now;
	}
}
