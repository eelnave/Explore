package edu.byui.cit.units;

public final class Force extends Property {
	public static final int
		newt = 7201,
		lbf  = 7202;

	private static Force singleton;

	public static synchronized Force getInstance() {
		if (singleton == null) {
			singleton = new Force();
		}
		return singleton;
	}

	private Force() {
		super(World.force, "force", new Unit[]{
			new Unit(newt, "N", "Newton", 4.44822),
			new Unit(lbf, "lbf", "poundForce", 1)
		});
	}
}
