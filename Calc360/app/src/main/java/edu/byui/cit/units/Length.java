package edu.byui.cit.units;

public final class Length extends Property {
	public static final int
		inch = 1101,
		foot = 1102,
		yard = 1103,
		rod  = 1104,
		mile = 1105,
		mm = 1110,
		cm = 1111,
		m  = 1112,
		km = 1113;

	private static final double metric = 1609344;
	private static Length singleton;

	public static synchronized Length getInstance() {
		if (singleton == null) {
			singleton = new Length();
		}
		return singleton;
	}

	private Length() {
		super(World.len, "length", new Unit[]{
			new Unit(inch, "in", "inch", 63360),
			new Unit(foot, "ft", "foot", 5280),
			new Unit(yard, "yd", "yard", 1760),
			new Unit(rod, "rod", "rod", 320),
			new Unit(mile, "mi", "mile", 1),
			new Unit(mm, "mm", "millimeter", metric),
			new Unit(cm, "cm", "centimeter", metric / 10),
			new Unit(m, "m", "meter", metric / 1000),
			new Unit(km, "km", "kilometer", metric / 1000000)
		});
	}
}


