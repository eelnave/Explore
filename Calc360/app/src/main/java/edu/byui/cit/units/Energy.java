package edu.byui.cit.units;

public final class Energy extends Property {
	public static final int
		milliampHour  = 8901,
		volt = 8902,
		wattHour   = 8903;

	private static Energy singleton;

	public static synchronized Energy getInstance() {
		if (singleton == null) {
			singleton = new Energy();
		}
		return singleton;
	}

	public Energy() {
		super(World.energy, "energy", new Unit[]{
			new Unit(milliampHour, "mAh", "MilliampHour", 1 ),
			new Unit(volt, "V", "Volt", 1),
			new Unit(wattHour, "wH", "WattHour", 1)
		});
	}
}
