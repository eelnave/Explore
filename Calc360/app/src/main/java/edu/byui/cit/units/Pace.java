package edu.byui.cit.units;


public final class Pace extends Property{

	public static final int
		timepermile = 9001,
		timeperkm =	9002;

	private static Pace singleton;

	public static synchronized Pace getInstance() {
		if (singleton == null) {
			singleton = new Pace();
		}
		return singleton;
	}

	private Pace() {
		super(World.pace, "pace", new Unit[]{
				new Unit(timepermile, "tpm", "timePerMile", 1.60934),
				new Unit(timeperkm, "tpkm", "timePerKm", 1)
		});
	}
}
