package edu.byui.cit.units;

public final class Pressure extends Property {
	public static final int
		Pa =  5001,
		psi = 5002,
		atm = 5003,
		bar = 5004;

	private static Pressure singleton;

	public static synchronized Pressure getInstance() {
		if (singleton == null) {
			singleton = new Pressure();
		}
		return singleton;
	}

	private Pressure() {
		super(World.pres, "pressure", new Unit[]{
			new Unit(psi, "psi", "lbfPerSqIn", 14.5038),
			new Unit(atm, "atm", "atmosphere", 0.986923),
			new Unit(Pa, "Pa", "Pascal", 100000),
			new Unit(bar, "bar", "Bar", 1)
		});
	}
}
