package edu.byui.cit.units;

public final class Frequency extends Property {
	public static final int
		Hz  = 3001,
		kHz = 3002,
		mHz = 3003,
		gHz = 3004,
		tHz = 3005;

	private static Frequency singleton;

	public static synchronized Frequency getInstance() {
		if (singleton == null) {
			singleton = new Frequency();
		}
		return singleton;
	}

	private Frequency() {
		super(World.freq, "frequency", new Unit[]{
			new Unit(Hz, "Hz", "hertz", 1e12),
			new Unit(kHz, "kHz", "kilohertz", 1e9),
			new Unit(mHz, "mHz", "megahertz", 1e6),
			new Unit(gHz, "gHz", "gigahertz", 1e3),
			new Unit(tHz, "tHz", "terahertz", 1)
		});
	}
}
