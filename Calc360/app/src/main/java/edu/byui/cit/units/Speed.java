package edu.byui.cit.units;

public final class Speed extends Property {
	public static final int
		fps  = 7001,
		mph  = 7002,
		knot = 7010,
		mps  = 7020,
		kmph = 7021;

	private static Speed singleton;

	public static synchronized Speed getInstance() {
		if (singleton == null) {
			singleton = new Speed();
		}
		return singleton;
	}

	private Speed() {
		super(World.speed, "speed", new Unit[]{
			new Unit(fps, "fps", "footPerSecond", 3.28084),
			new Unit(mph, "mph", "milePerHour", 2.2369364),
			new Unit(knot, "knot", "knot", 1.94384),
			new Unit(mps, "mps", "meterPerSecond", 1),
			new Unit(kmph, "kph", "kilometerPerHour", 3.6)
		});
	}
}
