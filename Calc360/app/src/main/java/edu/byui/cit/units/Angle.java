package edu.byui.cit.units;


public final class Angle extends Property {
	public static final int
		rad  = 3101,
		grad = 3102,
		deg  = 3103,
		min  = 3104;  // Minute of arc

	private static Angle singleton;

	public static synchronized Angle getInstance() {
		if (singleton == null) {
			singleton = new Angle();
		}
		return singleton;
	}

	private Angle() {
		super(World.angle, "angle", new Unit[]{
			new Unit(rad, "rad", "radian", Math.PI),
			new Unit(grad, "grad", "gradian", 200),
			new Unit(deg, "deg", "degree", 180),
			new Unit(min, "min", "minute", 10800)
		});
	}
}
