package edu.byui.cit.units;

public final class FuelEcon extends Property {
	public final static int
		mpg = 6201,
		kpl = 6202,
		litper100km = 6203;

	private static FuelEcon singleton;

	public static synchronized FuelEcon getInstance() {
		if (singleton == null) {
			singleton = new FuelEcon();
		}
		return singleton;
	}

	private FuelEcon() {
		super(World.fuelEcon, "fuelEcon", new Unit[]{
			new Unit(mpg, "mpg", "milePerGallon", 2.352144725),
			new Unit(kpl, "kpl", "kilometerPerLiter", 1),
			new Unit(litper100km, "lit/100 km", "litersPer100Kms", 1)
		});
	}
}
