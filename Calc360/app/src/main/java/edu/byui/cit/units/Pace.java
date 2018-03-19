package edu.byui.cit.units;


public final class Pace extends Property{

	public static final int
		permile = 9001,
		perkm =	9002;

	private static Pace singleton;

	public static synchronized Pace getInstance() {
		if (singleton == null) {
			singleton = new Pace();
		}
		return singleton;
	}

	private Pace() {
		super(World.pace, "pace", new Unit[]{
				new Unit(permile, "tpm", "perMile", 1.60934),
				new Unit(perkm, "tpkm", "perKm", 1)
		});
	}
}
