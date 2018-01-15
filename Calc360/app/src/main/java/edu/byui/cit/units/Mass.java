package edu.byui.cit.units;

public final class Mass extends Property {
	public static final int
		grain    = 1201,
		ounce    = 1202,
		pound    = 1203,
		stone    = 1204,
		shortTon = 1205,
		longTon  = 1206,
		gram      = 1210,
		kilogram  = 1211,
		metricTon = 1212;

	private static final double kg = 2240 / 2.20462262;
	private static Mass singleton;

	public static synchronized Mass getInstance() {
		if (singleton == null) {
			singleton = new Mass();
		}
		return singleton;
	}

	private Mass() {
		super(World.mass, "mass", new Unit[]{
			new Unit(grain, "gr", "grain", 15680000),
			new Unit(ounce, "oz", "ounce", 35840),
			new Unit(pound, "lb", "pound", 2240),
			new Unit(stone, "st", "stone", 160),
			new Unit(shortTon, "ton", "shortTon", 1.12),
			new Unit(longTon, "ton", "longTon", 1),
			new Unit(gram, "g", "gram", kg * 1000),
			new Unit(kilogram, "kg", "kilogram", kg),
			new Unit(metricTon, "tonne", "metricTon", kg / 1000)
		});
	}
}
