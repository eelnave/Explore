package edu.byui.cit.units;

public final class FuelEffic extends Property {
	public final static int
		mpg = 6201,
		kpl = 6202;

	private static FuelEffic singleton;

	public static synchronized FuelEffic getInstance() {
		if (singleton == null) {
			singleton = new FuelEffic();
		}
		return singleton;
	}

	private FuelEffic() {
		super(World.fuelEffic, "fuelEffic", new Unit[]{
			new Unit(mpg, "mpg", "milePerGallon", 2.352144725),
			new Unit(kpl, "kpl", "kilometerPerLiter", 1)
		});
	}
}
