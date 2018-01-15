package edu.byui.cit.units;

public final class Time extends Property {
	public static final int
		nano  = 1301,
		micro = 1302,
		milli = 1303,
		sec   = 1304,
		min   = 1305,
		hour  = 1306,
		day   = 1307,
		week  = 1308,
		month = 1320,
		year  = 1330,
		dec = 1331,
		cent = 1332,
		millienia = 1333;

	private static Time singleton;

	public static synchronized Time getInstance() {
		if (singleton == null) {
			singleton = new Time();
		}
		return singleton;
	}

	private Time() {
		super(World.time, "time", new Unit[]{
			new Unit(nano, "ns", "nanosecond", 7.0 * 24 * 60 * 60 * 1000 * 1000 * 1000),
			new Unit(micro, "\u03bcs", "microsecond", 7.0 * 24 * 60 * 60 * 1000 * 1000),
			new Unit(milli, "ms", "millisecond", 7 * 24 * 60 * 60 * 1000),
			new Unit(sec, "s", "second", 7 * 24 * 60 * 60),
			new Unit(min, "min", "minute", 7 * 24 * 60),
			new Unit(hour, "hr", "hour", 7 * 24),
			new Unit(day, "day", "day", 7),
			new Unit(week, "wk", "week", 1),
			new Unit(month, "mon", "month", 7/30.0),
			new Unit(year, "y", "year", 7/365.25),
			new Unit(dec, "dec", "decade", 7/(365.25 * 10)),
			new Unit(cent, "cent", "century", 7/(365.25 * 100)),
			new Unit(millienia, "mil", "millienia", 7/(365.25 * 1000)),
		});
	}
}
